package com.komlancz.gumiszerviz.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordService {
    private static String salt;

    public PasswordService() throws NoSuchAlgorithmException {
        salt = getSalt();
    }

    private String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }

    public static String passwordHashing(String password) throws NoSuchAlgorithmException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(password+salt);
    }

    public static boolean checkPassword(String password, String dbPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return (passwordEncoder.matches(password+salt, dbPassword));
    }
}
