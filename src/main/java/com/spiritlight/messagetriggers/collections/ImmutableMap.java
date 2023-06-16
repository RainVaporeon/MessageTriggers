package com.spiritlight.messagetriggers.collections;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class ImmutableMap<K, V> extends AbstractMap<K, V> {

    private final MapPair<K, V>[] pairs;

    @SafeVarargs
    public ImmutableMap(MapPair<K, V>... pairs) {
        Objects.requireNonNull(pairs);
        this.pairs = pairs;
    }

    @Override
    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return Arrays.stream(pairs).map(MapPair::immutableEntry).collect(Collectors.toSet());
    }
}
