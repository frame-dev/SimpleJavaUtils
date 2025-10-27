package ch.framedev.simplejavautils;



/*
 * =============================================
 * This File was Created by FrameDev
 * Please do not change anything without my consent!
 * =============================================
 * This Class was created at 08.11.2024 16:21
 */

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class EncryptionUtil {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String KEY = "F575u8mktb74gEYl"; // Example key (16-byte key for AES-128)

    // Generate a random IV (16 bytes for AES)
    private static IvParameterSpec generateIv() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }


    public static String encrypt(String value) throws Exception {
        SecretKeySpec key = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec iv = generateIv();
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
        byte[] ivAndEncrypted = concatenate(iv.getIV(), encrypted);
        return Base64.getEncoder().encodeToString(ivAndEncrypted);
    }

    public static String decrypt(String encrypted) throws Exception {
        try {
            // Check if the input is a valid Base64-encoded string
            byte[] decodedValue = Base64.getDecoder().decode(encrypted);

            SecretKeySpec key = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
            IvParameterSpec iv = new IvParameterSpec(decodedValue, 0, 16);
            byte[] encryptedBytes = new byte[decodedValue.length - 16];
            System.arraycopy(decodedValue, 16, encryptedBytes, 0, encryptedBytes.length);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, key, iv);
            byte[] original = cipher.doFinal(encryptedBytes);
            return new String(original, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            System.out.println(encrypted);
            throw new RuntimeException("Failed to decode Base64 string. Please check the input.", e);
        }
    }


    // Helper method to concatenate IV and encrypted data
    private static byte[] concatenate(byte[] iv, byte[] encrypted) {
        byte[] result = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, result, 0, iv.length);
        System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);
        return result;
    }
}
