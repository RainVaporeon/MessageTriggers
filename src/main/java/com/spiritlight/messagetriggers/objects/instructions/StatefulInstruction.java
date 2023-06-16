package com.spiritlight.messagetriggers.objects.instructions;

import com.spiritlight.messagetriggers.collections.MapPair;
import com.spiritlight.messagetriggers.exceptions.functions.ArgumentMismatchException;
import com.spiritlight.messagetriggers.objects.elements.Element;

public abstract class StatefulInstruction extends AbstractInstruction {

    @SafeVarargs
    public StatefulInstruction(String name, int parameters, MapPair<String, String>... mapping) {
        super(name, parameters, mapping);
    }

    /**
     * Accepts a set of arguments
     * @param parameters The parameters to accept
     * @return true if the arguments were accepted, false otherwise
     */
    public abstract boolean accept(Element... parameters) throws ArgumentMismatchException;
}
