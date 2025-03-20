package com.example.expense.utils;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class EncryptionUtils {
    // Generate a salt using a secure random number generator
    public String generateSalt() {
        SecureRandom sr = new SecureRandom();
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hash a password with salt using SHA-256
    public String hashPasswordWithSalt(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(salt.getBytes()); // Apply salt to the hashing process
        byte[] hashedPassword = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hashedPassword);
    }

    // Verify if a password matches the stored hash (this is just for educational purposes)
    public boolean verifyPassword(String password, String salt, String hashedPassword) throws NoSuchAlgorithmException {
        String hashToVerify = hashPasswordWithSalt(password, salt);
        return hashToVerify.equals(hashedPassword);
    }
}
