package com.spiritlight.messagetriggers.collections;

import javax.annotation.Nonnull;
import java.util.*;

public class ImmutableSet<E> extends AbstractSet<E> {
    private final Map<E, Object> backingMap;

    private static final Object EMPTY = new Object();

    private final int size;

    public ImmutableSet() {
        backingMap = new ImmutableMap<>();
        size = 0;
    }

    @SafeVarargs @SuppressWarnings("unchecked")
    public ImmutableSet(E... elements) {
        MapPair<E, Object>[] pairs = (MapPair<E, Object>[]) Arrays.stream(elements)
                .map(p -> MapPair.of(p, EMPTY))
                .toArray(Object[]::new);
        backingMap = new ImmutableMap<>(pairs);
        size = elements.length;
    }

    public static <E> Set<E> of() {
        return Collections.emptySet();
    }

    @SafeVarargs
    public static <E> Set<E> of(E... el) {
        return el.length == 1 ? Collections.singleton(el[0]) : new ImmutableSet<>(el);
    }

    @Override
    public Iterator<E> iterator() {
        return backingMap.keySet().iterator();
    }

    @Override
    public int size() {
        return size;
    }
}
