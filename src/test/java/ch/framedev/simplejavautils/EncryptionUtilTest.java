package ch.framedev.simplejavautils;

import org.junit.Test;

public class EncryptionUtilTest {

    @Test
    public void testEncryptionWithPasswordDecryption() throws Exception {
        String originalText = "Hello, World!";
        String password = "securePassword";

        String encryptedText = EncryptionUtil.encrypt(originalText, password);
        assert !encryptedText.equals(originalText);

        String decryptedText = EncryptionUtil.decrypt(encryptedText, password);
        assert decryptedText.equals(originalText);
    }

    @Test
    public void testEncryptionWithKeyDecryption() throws Exception {
        String originalText = "Hello, World!";
        String key = "123456789012355"; // 16-byte key for AES-128
        String encryptedText = EncryptionUtil.encrypt(originalText, key);
        assert !encryptedText.equals(originalText);
        String decryptedText = EncryptionUtil.decrypt(encryptedText, key);
        assert decryptedText.equals(originalText);
    }
}
