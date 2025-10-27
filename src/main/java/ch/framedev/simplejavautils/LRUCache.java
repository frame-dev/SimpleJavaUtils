package ch.framedev.simplejavautils;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> {
    private final int maxSize;
    private final long defaultTtlMillis;
    private final Map<K, CacheEntry<V>> map;

    public LRUCache(int maxSize) {
        this(maxSize, 0);
    }

    public LRUCache(int maxSize, long defaultTtlMillis) {
        this.maxSize = Math.max(1, maxSize);
        this.defaultTtlMillis = defaultTtlMillis;
        this.map = Collections.synchronizedMap(new LinkedHashMap<K, CacheEntry<V>>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, CacheEntry<V>> eldest) {
                return size() > LRUCache.this.maxSize;
            }
        });
    }

    public void put(K key, V value) {
        put(key, value, defaultTtlMillis);
    }

    public void put(K key, V value, long ttlMillis) {
        long expiry = ttlMillis > 0 ? System.currentTimeMillis() + ttlMillis : Long.MAX_VALUE;
        map.put(key, new CacheEntry<>(value, expiry));
    }

    public V get(K key) {
        CacheEntry<V> entry = map.get(key);
        if (entry == null) return null;
        if (entry.isExpired()) {
            map.remove(key);
            return null;
        }
        return entry.getValue();
    }

    public V remove(K key) {
        CacheEntry<V> e = map.remove(key);
        return e == null ? null : e.getValue();
    }

    public int size() {
        // remove expired entries and return current live count
        synchronized (map) {
            Iterator<Map.Entry<K, CacheEntry<V>>> it = map.entrySet().iterator();
            int count = 0;
            while (it.hasNext()) {
                Map.Entry<K, CacheEntry<V>> ent = it.next();
                if (ent.getValue().isExpired()) it.remove();
                else count++;
            }
            return count;
        }
    }

    public void clear() {
        map.clear();
    }
}