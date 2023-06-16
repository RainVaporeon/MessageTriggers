package com.spiritlight.messagetriggers.utils;

public class Callback<T> {
    private final T t;
    private final Throwable throwable;
    private final Result result;

    public Callback(T type, Result result, Throwable throwable) {
        this.t = type;
        this.result = result;
        this.throwable = throwable;
    }

    public Result getResult() {
        return result;
    }

    public T get() {
        return t;
    }

    public boolean hasElement() {
        return t != null;
    }

    public boolean hasThrowable() {
        return throwable != null;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public static <T> Callback<T> of(T t, Result result, Throwable throwable) {
        return new Callback<>(t, result, throwable);
    }

    public static <T> Callback<T> of(T t) {
        return new Callback<>(t, Result.SUCCESS, null);
    }

    public static <T> Callback<T> of(Throwable t) {
        return new Callback<>(null, Result.FAIL, t);
    }

    @Override
    public String toString() {
        return hasElement() ? t.toString() : throwable.toString();
    }
}
