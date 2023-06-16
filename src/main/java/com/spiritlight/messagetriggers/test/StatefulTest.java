package com.spiritlight.messagetriggers.test;

import com.spiritlight.messagetriggers.collections.MapPair;
import com.spiritlight.messagetriggers.exceptions.functions.ArgumentMismatchException;
import com.spiritlight.messagetriggers.objects.elements.Element;
import com.spiritlight.messagetriggers.objects.instructions.StatefulInstruction;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class StatefulTest extends StatefulInstruction {
    @SafeVarargs
    public StatefulTest(MapPair<String, String>... mapping) {
        super("STFTest", 1, mapping);
    }

    @Override
    public boolean accept(Element... parameters) throws ArgumentMismatchException {
        System.out.println(" - - - Start Dump - - -");
        System.out.println("Found parameters of size " + parameters.length);
        for(Element element : parameters) {
            System.out.println(" - " + element);
        }
        System.out.println("Found variable count of " + getVariableSet().size());
        for(Map.Entry<String, String> entry : this.entrySet()) {
            System.out.println(" - " + entry.getKey() + ":" + entry.getValue());
        }
        System.out.println(" - - - End Dump - - -");
        return true;
    }

    @Override
    public Set<Class<? extends Element>> compatibleWith() {
        return Collections.singleton(Element.class);
    }
}
