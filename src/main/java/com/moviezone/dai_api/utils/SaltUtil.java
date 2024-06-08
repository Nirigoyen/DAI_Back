package com.moviezone.dai_api.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class SaltUtil {
    private static final SecureRandom random = new SecureRandom();

    public static String generateSalt(int length) {
        byte[] salt = new byte[length];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
}
