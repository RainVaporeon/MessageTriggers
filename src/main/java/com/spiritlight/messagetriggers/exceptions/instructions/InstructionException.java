package com.spiritlight.messagetriggers.exceptions.instructions;

/**
 * Exceptions that are caused by faulty instructions
 */
public class InstructionException extends Exception {

    public InstructionException() {
        super();
    }

    public InstructionException(String s) {
        super(s);
    }

    public InstructionException(Throwable t) {
        super(t);
    }

}
