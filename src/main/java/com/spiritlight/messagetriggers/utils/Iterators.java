package com.spiritlight.messagetriggers.utils;

public class Iterators {

    public static <T> ArrayIterator<T> arrayIterator(T[] array) {
        return new ArrayIterator<>(array);
    }
}
