package com.spiritlight.messagetriggers.objects.instructions;

import com.spiritlight.messagetriggers.objects.elements.Element;

import java.util.Collections;
import java.util.Set;

public abstract class AbstractStatelessInstruction extends AbstractInstruction {

    protected AbstractStatelessInstruction(String name, int args) {
        super(name, args);
    }

    protected AbstractStatelessInstruction(String name) {
        super(name);
    }

    protected AbstractStatelessInstruction(int args) {
        super("", args);
    }

    protected AbstractStatelessInstruction() {
        super("");
    }

    @Override
    public Set<Class<? extends Element>> compatibleWith() {
        return Collections.singleton(Element.class);
    }
}
