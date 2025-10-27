package ch.framedev.simplejavautils;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import java.util.logging.Level;

import static ch.framedev.simplejavautils.SimpleJavaUtils.logger;

public class PasswordHasher {

    private static final String ALGO = "PBKDF2WithHmacSHA1";
    private static final byte[] SALT = {
            8, 8, 8, 8, 2,
            8, 7, 7, 7, 2,
            1, 1, 1, 1, 2,
            11
    };
    private static final int ITERATION_COUNT = 1000;
    private static final int KEY_LENGTH = 128;

    private SecretKeyFactory mFactory;

    /**
     * Hashes the given password using the PBKDF2 algorithm.
     *
     * @param password The password to be hashed.
     * @return The hashed password.
     */
    public byte[] hashPassword(String password) {
        SecretKeyFactory factory = getFactory();

        if (factory != null) {
            try {
                KeySpec spec = new PBEKeySpec(password.toCharArray(), SALT, ITERATION_COUNT, KEY_LENGTH);
                return factory.generateSecret(spec).getEncoded();
            } catch (InvalidKeySpecException e) {
                logger.log(Level.SEVERE,"Error", e);
            }
        }
        return null;
    }

    /**
     * Verifies the given password against the expected hash result.
     *
     * @param password           The password to be verified.
     * @param expectedHashResult The expected hash result.
     * @return True if the password is verified, false otherwise.
     */
    public boolean verifyPassword(String password, byte[] expectedHashResult) {
        byte[] hashedPassword = hashPassword(password);
        if (hashedPassword == null) {
            // Log fail result
            return false;
        }
        return Arrays.equals(hashedPassword, expectedHashResult);
    }

    private SecretKeyFactory getFactory() {
        if (mFactory == null) {
            try {
                mFactory = SecretKeyFactory.getInstance(ALGO);
            } catch (NoSuchAlgorithmException e) {
                logger.log(Level.SEVERE,"Error", e);
            }
        }
        return mFactory;
    }
}