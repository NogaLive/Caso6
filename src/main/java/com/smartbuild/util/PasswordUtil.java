package com.smartbuild.util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordUtil {
    private static final int ITERATIONS = 310000;
    private static final int KEY_LENGTH = 256;
    private static final String ALGO = "PBKDF2WithHmacSHA256";

    public static String hash(String password) {
        try {
            byte[] salt = new byte[16];
            new SecureRandom().nextBytes(salt);
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGO);
            byte[] key = skf.generateSecret(spec).getEncoded();
            return ITERATIONS + ":" + Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(key);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public static boolean verify(String password, String stored) {
        try {
            String[] parts = stored.split(":");
            int it = Integer.parseInt(parts[0]);
            byte[] salt = Base64.getDecoder().decode(parts[1]);
            byte[] expected = Base64.getDecoder().decode(parts[2]);
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, it, expected.length * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGO);
            byte[] key = skf.generateSecret(spec).getEncoded();
            if (key.length != expected.length) return false;
            int diff = 0;
            for (int i = 0; i < key.length; i++) diff |= key[i] ^ expected[i];
            return diff == 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isStrong(String p) {
        if (p == null || p.length() < 8) return false;
        boolean up=false, low=false, num=false;
        for (char ch: p.toCharArray()) {
            if (Character.isUpperCase(ch)) up = true;
            else if (Character.isLowerCase(ch)) low = true;
            else if (Character.isDigit(ch)) num = true;
        }
        return up && low && num;
    }
}
