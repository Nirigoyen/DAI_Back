package com.moviezone.dai_api.utils;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TokenEncrypter {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encryptToken(String token, String salt) {
        String saltedToken = token + salt + Dotenv.load().get("PEPPER");
        return encoder.encode(saltedToken);
    }

    public static boolean matches(String rawToken, String encodedToken, String salt) {
        String saltedToken = rawToken + salt + Dotenv.load().get("PEPPER");
        return encoder.matches(saltedToken, encodedToken);
    }
}
