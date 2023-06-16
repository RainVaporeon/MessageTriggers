package com.spiritlight.messagetriggers.objects.instructions;

import com.spiritlight.messagetriggers.exceptions.instructions.ExecutionException;
import com.spiritlight.messagetriggers.objects.elements.Element;

import java.util.Collections;
import java.util.Set;

/**
 * An instruction indicates some sort of action that the program
 * should execute. An instruction is also an element, though
 * without any states.
 * <p>
 * If an instruction at any time requires a state, extend from
 * {@link StatefulInstruction} instead.
 */
public abstract class StatelessInstruction extends AbstractStatelessInstruction {


    public StatelessInstruction(String name, int parameters) {
        super(name, parameters);
    }

    public StatelessInstruction(int parameters) {
        super(parameters);
    }

    public void run() throws ExecutionException {

    }

    @Override
    public Set<Class<? extends Element>> compatibleWith() {
        return Collections.singleton(Element.class);
    }
}
