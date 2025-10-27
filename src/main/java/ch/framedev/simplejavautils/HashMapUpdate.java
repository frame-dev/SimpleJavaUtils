package ch.framedev.simplejavautils;

import java.util.HashMap;

@SuppressWarnings("unused")
public class HashMapUpdate<K, V> extends HashMap<K, V> {

    public HashMapUpdate<K, V> putHash(K key, V value) {
        super.put(key, value);
        return this;
    }
}
