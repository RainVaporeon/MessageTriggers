package com.spiritlight.messagetriggers.utils;

import java.util.Iterator;

public class ArrayIterator<T> implements Iterable<T>, Iterator<T> {
    private final T[] array;
    int cursor = 0;

    public ArrayIterator(T[] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        return cursor < array.length;
    }

    @Override
    public T next() {
        return array[cursor++];
    }

    public static <T> ArrayIterator<T> of(T[] elements) {
        return new ArrayIterator<>(elements);
    }

    @Override
    public Iterator<T> iterator() {
        return this;
    }
}
