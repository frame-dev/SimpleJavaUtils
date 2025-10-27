package ch.framedev.simplejavautils;

import org.junit.Test;

public class CacheEntryTest {

    @Test
    public void testCacheEntryCreation() {
        long expiryTime = System.currentTimeMillis() + 1000; // 1 second in the future
        CacheEntry<String> entry = new CacheEntry<>("testValue", expiryTime);
        assert entry.getValue().equals("testValue");
        assert !entry.isExpired();
    }

    @Test
    public void testCacheEntryExpiration() throws InterruptedException {
        long expiryTime = System.currentTimeMillis() + 500; // 0.5 seconds in the future
        CacheEntry<String> entry = new CacheEntry<>("testValue", expiryTime);
        assert !entry.isExpired();
        Thread.sleep(600); // Wait for 0.6 seconds
        assert entry.isExpired();
    }

    @Test
    public void testCacheEntryWithDifferentTypes() {
        long expiryTime = System.currentTimeMillis() + 1000; // 1 second in the future
        CacheEntry<Integer> intEntry = new CacheEntry<>(42, expiryTime);
        assert intEntry.getValue() == 42;

        CacheEntry<Double> doubleEntry = new CacheEntry<>(3.14, expiryTime);
        assert doubleEntry.getValue() == 3.14;

        CacheEntry<Object> objectEntry = new CacheEntry<>(new Object(), expiryTime);
        assert objectEntry.getValue() != null;
    }

    @Test
    public void testCacheEntryImmediateExpiration() {
        long expiryTime = System.currentTimeMillis() - 1000; // 1 second in the past
        CacheEntry<String> entry = new CacheEntry<>("expiredValue", expiryTime);
        assert entry.isExpired();
    }
}
