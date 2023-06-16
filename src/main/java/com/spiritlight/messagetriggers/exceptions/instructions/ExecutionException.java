package com.spiritlight.messagetriggers.exceptions.instructions;

public class ExecutionException extends InstructionException {
    public ExecutionException(Throwable t) {
        super(t);
    }

    public ExecutionException(String s) {
        super(s);
    }
}
