package com.spiritlight.messagetriggers.exceptions.instructions;

import com.spiritlight.messagetriggers.objects.instructions.AbstractInstruction;

public class InstructionNotFoundException extends InstructionException {
    public InstructionNotFoundException(String s) {
        super(s);
    }

    public InstructionNotFoundException(AbstractInstruction instruction) {
        this("for instruction: " + instruction);
    }

    public static InstructionNotFoundException forInstruction(AbstractInstruction abi) {
        return new InstructionNotFoundException(abi);
    }
}
