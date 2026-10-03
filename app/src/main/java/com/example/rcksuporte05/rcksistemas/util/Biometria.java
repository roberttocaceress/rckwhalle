package com.example.rcksuporte05.rcksistemas.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.hardware.fingerprint.FingerprintManagerCompat;

public class Biometria {
    public  boolean isBiometricPromptEnabled() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P);
    }
    public  boolean isSdkVersionSupported() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }
    public boolean isFingerprintAvailable(Context context) {
        FingerprintManagerCompat fingerprintManager = FingerprintManagerCompat.from(context);
        return fingerprintManager.hasEnrolledFingerprints();
    }
    public boolean isPermissionGranted(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) ==
                PackageManager.PERMISSION_GRANTED;
    }
    @RequiresApi(api = Build.VERSION_CODES.P)
    public class BiometricCallback extends BiometricPrompt.AuthenticationCallback {

        private BiometricCallback biometricCallback;
        public BiometricCallback(BiometricCallback biometricCallback) {
            this.biometricCallback = biometricCallback;
        }


        @Override
        public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
            biometricCallback.onAuthenticationSucceeded(result);
            //biometricCallback.onAuthenticationSuccessful();
        }




        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            biometricCallback.onAuthenticationError(errorCode, errString);
        }


        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
            biometricCallback.onAuthenticationFailed();
        }
    }

}
