package ch.framedev.simplejavautils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

public class EncryptionUtil {

    private static SecretKeySpec deriveKey(String keyOrPassword) throws Exception {
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] keyBytes = sha.digest(keyOrPassword.getBytes(StandardCharsets.UTF_8));
        byte[] key128 = Arrays.copyOf(keyBytes, 16); // use first 16 bytes = 128 bit key
        return new SecretKeySpec(key128, "AES");
    }

    public static String encrypt(String plainText, String keyOrPassword) throws Exception {
        SecretKeySpec key = deriveKey(keyOrPassword);
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        byte[] combined = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encrypted, 0, combined, iv.length, encrypted.length);

        return Base64.getEncoder().encodeToString(combined);
    }

    public static String decrypt(String base64IvAndCipher, String keyOrPassword) throws Exception {
        SecretKeySpec key = deriveKey(keyOrPassword);
        byte[] combined = Base64.getDecoder().decode(base64IvAndCipher);

        byte[] iv = Arrays.copyOfRange(combined, 0, 16);
        byte[] cipherText = Arrays.copyOfRange(combined, 16, combined.length);

        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] decrypted = cipher.doFinal(cipherText);

        return new String(decrypted, StandardCharsets.UTF_8);
    }
}
