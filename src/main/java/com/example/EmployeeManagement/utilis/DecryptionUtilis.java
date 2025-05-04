package com.example.EmployeeManagement.utilis;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

@Component
public class DecryptionUtilis {

    private static final String passphrase = "EmsTestingTeam@2025";

    private static SecretKeySpec getKey() throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = messageDigest.digest(passphrase.getBytes("UTF-8"));
        return new SecretKeySpec(keyBytes, "AES");
    }

    public String decrypt(String encryptedPassword) throws Exception {
        SecretKeySpec key = getKey();
        IvParameterSpec iv = new IvParameterSpec(new byte[16]);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        byte[] original = cipher.doFinal(Base64.getDecoder().decode(encryptedPassword));
        return new String(original, "UTF-8");
    }
}
