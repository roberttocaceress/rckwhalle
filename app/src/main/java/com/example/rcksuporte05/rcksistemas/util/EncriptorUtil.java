package com.example.rcksuporte05.rcksistemas.util;


import org.jasypt.commons.CommonUtils;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;

public final class EncriptorUtil {

    private static final String ENCRYPT_KEY = "#.rck@5102!.-=";

    public static final String encript(String toEncrypt) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setStringOutputType(CommonUtils.STRING_OUTPUT_TYPE_HEXADECIMAL);
        standardPBEStringEncryptor.setPassword(ENCRYPT_KEY);
        return standardPBEStringEncryptor.encrypt(toEncrypt);
    }

    public static final String decript(String toDecrypt) throws EncryptionOperationNotPossibleException {
        StandardPBEStringEncryptor standardPBEStringEncryptor = getStandardPBEStringEncryptor();
        return standardPBEStringEncryptor.decrypt(toDecrypt);
    }

    public static final String encriptUsuario(String toEncrypt) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setStringOutputType(CommonUtils.STRING_OUTPUT_TYPE_HEXADECIMAL);
        standardPBEStringEncryptor.setPassword(ENCRYPT_KEY);
        return standardPBEStringEncryptor.encrypt(toEncrypt);
    }

    public static final String decriptUsuario(String toDecrypt) throws EncryptionOperationNotPossibleException{
        StandardPBEStringEncryptor standardPBEStringEncryptor = getStandardPBEStringEncryptor();
        return standardPBEStringEncryptor.decrypt(toDecrypt);
    }




    private static StandardPBEStringEncryptor getStandardPBEStringEncryptor() {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        standardPBEStringEncryptor.setStringOutputType(CommonUtils.STRING_OUTPUT_TYPE_HEXADECIMAL);
        standardPBEStringEncryptor.setPassword(ENCRYPT_KEY);
        return standardPBEStringEncryptor;
    }
}
