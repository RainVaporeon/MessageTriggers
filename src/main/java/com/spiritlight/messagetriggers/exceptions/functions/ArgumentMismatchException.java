package com.spiritlight.messagetriggers.exceptions.functions;

public class ArgumentMismatchException extends AcceptException {

    public ArgumentMismatchException(String s) {
        super(s);
    }

    public ArgumentMismatchException(int expected, int actual) {
        this("expected " + expected + " arguments, but got " + actual);
    }
}
