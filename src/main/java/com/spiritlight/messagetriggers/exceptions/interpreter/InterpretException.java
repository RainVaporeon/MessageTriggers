package com.spiritlight.messagetriggers.exceptions.interpreter;

public class InterpretException extends Exception {
    public InterpretException(String s) {
        super(s);
    }

    public InterpretException(Throwable t) {
        super(t);
    }

    public InterpretException() {
        super();
    }
}
