package ch.framedev.simplejavautils;

import org.junit.Test;

public class PasswordHasherTest {

    @Test
    public void testPasswordHashing() {
        PasswordHasher hasher = new PasswordHasher();
        String password = "SecurePassword123!";
        byte[] hashedPassword = hasher.hashPassword(password);

        assert hasher.verifyPassword(password, hashedPassword) : "Password verification failed!";
        assert !hasher.verifyPassword("WrongPassword", hashedPassword) : "Incorrect password verified successfully!";
    }

    @Test
    public void testEmptyPassword() {
        PasswordHasher hasher = new PasswordHasher();
        String password = "";
        byte[] hashedPassword = hasher.hashPassword(password);

        assert hasher.verifyPassword(password, hashedPassword) : "Empty password verification failed!";
    }
}
