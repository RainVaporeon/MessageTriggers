package com.spiritlight.messagetriggers.collections;

import java.util.Map;

public class MapPair<K, V> implements Pair<K, V> {
    private final K key;
    private V value;

    public MapPair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }

    public Map.Entry<K,V> entry() {
        return new PairEntry(true);
    }

    public Map.Entry<K, V> immutableEntry() {
        return new PairEntry(false);
    }

    public static <K, V> MapPair<K, V> of(K key, V value) {
        return new MapPair<>(key, value);
    }

    public class PairEntry implements Map.Entry<K, V> {
        private final boolean allowModifications;

        public PairEntry(boolean allowModifications) {
            this.allowModifications = allowModifications;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            if(!allowModifications) throw new UnsupportedOperationException();
            return MapPair.this.setValue(value);
        }
    }
}
