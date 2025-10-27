package ch.framedev.simplejavautils;

/**
 * @param expiryTime epoch millis; Long.MAX_VALUE = never expire
 */
public class CacheEntry<V> {

    private final V value;
    private final long expiryTime;

    public CacheEntry(V value, long expiryTime) {
        this.value = value;
        this.expiryTime = expiryTime;
    }

    public V getValue() {
        return value;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > expiryTime;
    }
}