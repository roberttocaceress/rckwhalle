package com.example.rcksuporte05.rcksistemas.util;


import com.example.rcksuporte05.rcksistemas.model.Usuario;

import org.jasypt.exceptions.EncryptionOperationNotPossibleException;

/**
 * Gerando o Token
 */

public final class TokenUtils {

    private static final String SEPARADOR = ":";

    public static String descryptografaUsuario(String usuario) {
        try {
            String tokenRestaurado = EncriptorUtil.decript(usuario);
            return tokenRestaurado != null ? tokenRestaurado : null;
        } catch (EncryptionOperationNotPossibleException e) {
            return null;
        }

    }


}