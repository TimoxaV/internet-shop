package com.internet.shop.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class HashUtil {
    private static final String algorithm = "SHA-512";

    public static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        StringBuilder hashedPwd = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(salt);
            byte[] digest = messageDigest.digest(password.getBytes());
            for (byte symbol : digest) {
                hashedPwd.append(symbol);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("There is a problem with hashing password!");
        }
        return hashedPwd.toString();
    }
}
