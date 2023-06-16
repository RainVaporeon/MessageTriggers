package com.spiritlight.messagetriggers.exceptions.interpreter;

public class ParameterMismatchException extends InterpretException {
    public ParameterMismatchException(String s) {
        super(s);
    }

    public ParameterMismatchException(int expected, int actual) {
        this("Expected " + expected + " arguments, but got " + actual);
    }
}
