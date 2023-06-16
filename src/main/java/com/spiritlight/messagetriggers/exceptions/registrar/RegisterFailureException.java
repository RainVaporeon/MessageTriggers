package com.spiritlight.messagetriggers.exceptions.registrar;

public class RegisterFailureException extends Exception {
    private final Type type;

    public RegisterFailureException(Throwable t) {
        super(t);
        type = Type.FAIL;
    }

    public RegisterFailureException(String s, Type type) {
        super(s);
        this.type = type;
    }

    public RegisterFailureException(String s) {
        super(s);
        type = Type.FAIL;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        SUCCESS,
        FAIL
    }
}
