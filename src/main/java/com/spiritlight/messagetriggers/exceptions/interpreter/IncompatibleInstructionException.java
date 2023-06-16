package com.spiritlight.messagetriggers.exceptions.interpreter;

import com.spiritlight.messagetriggers.objects.instructions.AbstractInstruction;

public class IncompatibleInstructionException extends InterpretException {

    public IncompatibleInstructionException(AbstractInstruction instruction) {
        this("for instruction " + instruction);
    }

    public IncompatibleInstructionException(String s) {
        super(s);
    }
}
