package com.spiritlight.messagetriggers.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class ExceptionHandler {

    private static final List<Consumer<Throwable>> handlers = new LinkedList<>();

    public static void handleException(Throwable t) {
        for(Consumer<Throwable> consumer : handlers) {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                // no-op
            }
        }
    }

    public static void addHandler(Consumer<Throwable> consumer) {
        handlers.add(consumer);
    }
}
