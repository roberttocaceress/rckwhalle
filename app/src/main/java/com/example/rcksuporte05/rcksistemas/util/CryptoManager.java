package com.example.rcksuporte05.rcksistemas.util;

import android.content.ClipData;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import androidx.annotation.RequiresApi;

import com.itextpdf.text.pdf.qrcode.ByteArray;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.sql.SQLException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

interface CryptoGrafManagerImpl {
    Cipher getInitializedCipherForEncryption(String keyName);

    Cipher getInitializedCipherForDecryption(String keyName, ByteArray initializationVector);

    //EncryptedData encryptData(String plaintext, Cipher cipher);

    /**
     * The Cipher created with [getInitializedCipherForDecryption] is used here
     */
    String decryptData(byte[] ciphertext, Cipher cipher);

}

//EncryptedData item =  EncryptedData(ByteArray ciphertext, ByteArray initializationVector) ;
//data class EncryptedData(val ciphertext: ByteArray, val initializationVector: ByteArray)

public class CryptoManager implements  CryptoGrafManagerImpl {
    private final int KEY_SIZE =  256;
    private final String  ANDROID_KEYSTORE = "AndroidKeyStore";
    private final String ENCRYPTION_BLOCK_MODE = KeyProperties.BLOCK_MODE_GCM;
    private final String ENCRYPTION_PADDING = KeyProperties.ENCRYPTION_PADDING_NONE;
    private final String ENCRYPTION_ALGORITHM = KeyProperties.KEY_ALGORITHM_AES;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public Cipher getInitializedCipherForEncryption(String keyName) {
        Cipher cipher = getCipher();
        SecretKey secretKey = getOrCreateSecretKey(keyName);
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return cipher;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public Cipher getInitializedCipherForDecryption(String keyName, ByteArray initializationVector) {
        Cipher cipher = getCipher();
        SecretKey secretKey = getOrCreateSecretKey(keyName);
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);

            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
           // cipher.init(Cipher.PRIVATE_KEY, secretKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch ( Exception e) {
            e.printStackTrace();
        }
        return cipher;
    }

    //@Override
    //public EncryptedData encryptData(String plaintext, Cipher cipher) {
        //cipher.doFinal(plaintext.getBytes(plaintext))
     //   return null;
    //}

    @Override
    public String decryptData(byte[] ciphertext, Cipher cipher) {
        try {
            byte[] plainText = cipher.doFinal(ciphertext);
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }



    public Cipher getCipher() {
        final String transformation = KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7;//"$ENCRYPTION_ALGORITHM/$ENCRYPTION_BLOCK_MODE/$ENCRYPTION_PADDING";
        try {
            return Cipher.getInstance(transformation);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private SecretKey getOrCreateSecretKey(String keyName) {
        // If Secretkey was previously created for that keyName, then grab and return it.
        KeyStore keyStore;
        try {
            keyStore = KeyStore.getInstance(ANDROID_KEYSTORE);
            keyStore.load(null);
            keyStore.getKey(keyName, null);
            //keyStore.getKey(keyName, null)?.let { return it as SecretKey }
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        }


        // if you reach here, then a new SecretKey must be generated for that keyName


        KeyGenParameterSpec.Builder paramsBuilder = new KeyGenParameterSpec.Builder(keyName, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT);
        paramsBuilder.setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                .setKeySize(KEY_SIZE)
                .setUserAuthenticationRequired(true);

        KeyGenParameterSpec keyGenParams = paramsBuilder.build();
        KeyGenerator keyGen;
        try {
            keyGen = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE);
            keyGen.init(keyGenParams);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException | InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String encrypt(String algorithm, String input, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] cipherText = cipher.doFinal(input.getBytes());
        return Base64.getEncoder()
                .encodeToString(cipherText);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String decrypt(String algorithm, String cipherText, SecretKey key,
                                 IvParameterSpec iv) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] plainText = cipher.doFinal(Base64.getDecoder()
                .decode(cipherText));
        return new String(plainText);
    }

}
