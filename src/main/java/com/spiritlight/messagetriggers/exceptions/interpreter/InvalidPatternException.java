package com.spiritlight.messagetriggers.exceptions.interpreter;

public class InvalidPatternException extends InterpretException {
    public InvalidPatternException(String s) {
        super(s);
    }

    public static InvalidPatternException forInputString(String s) {
        return new InvalidPatternException("for input string: " + s);
    }
}
