package ch.framedev.simplejavautils;

import org.junit.Test;

public class LRUCacheTest {

    @Test
    public void testLRUCacheCreation() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        cache.put(1, "one");
        cache.put(2, "two");
        assert cache.get(1).equals("one");
        assert cache.get(2).equals("two");
    }

    @Test
    public void testLRUCacheEviction() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(3, "three"); // This should evict key 1
        assert cache.get(1) == null;
        assert cache.get(2).equals("two");
        assert cache.get(3).equals("three");
    }

    @Test
    public void testLRUCacheUpdate() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        cache.put(1, "one");
        cache.put(2, "two");
        cache.put(1, "uno"); // Update key 1
        assert cache.get(1).equals("uno");
        assert cache.get(2).equals("two");
    }

    @Test
    public void testLRUCacheAccessOrder() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        cache.put(1, "one");
        cache.put(2, "two");
        cache.get(1); // Access key 1
        cache.put(3, "three"); // This should evict key 2
        assert cache.get(1).equals("one");
        assert cache.get(2) == null;
        assert cache.get(3).equals("three");
    }

    @Test
    public void testLRUCacheClear() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        cache.put(1, "one");
        cache.put(2, "two");
        cache.clear();
        assert cache.get(1) == null;
        assert cache.get(2) == null;
    }

    @Test
    public void testLRUCacheSize() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        cache.put(1, "one");
        cache.put(2, "two");
        assert cache.size() == 2;
        cache.put(3, "three"); // This should evict key 1
        assert cache.size() == 2;
    }

    @Test
    public void testLRUCacheWithTTL() throws InterruptedException {
        LRUCache<Integer, String> cache = new LRUCache<>(2, 500); // 500 ms TTL
        cache.put(1, "one");
        Thread.sleep(600); // Wait for entry to expire
        assert cache.get(1) == null; // Entry should be expired
    }
}
