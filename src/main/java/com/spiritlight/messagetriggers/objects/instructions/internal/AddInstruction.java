package com.spiritlight.messagetriggers.objects.instructions.internal;

import com.spiritlight.messagetriggers.exceptions.functions.ArgumentMismatchException;
import com.spiritlight.messagetriggers.objects.elements.Element;
import com.spiritlight.messagetriggers.objects.elements.data.DataElement;
import com.spiritlight.messagetriggers.objects.instructions.StatefulInstruction;
import com.spiritlight.messagetriggers.objects.instructions.annotations.SpecialInstruction;

import java.util.Collections;
import java.util.Set;

@SpecialInstruction
public class AddInstruction extends StatefulInstruction {

    public AddInstruction() {
        super("add", 1);
    }

    @Override
    public boolean accept(Element... parameters) throws ArgumentMismatchException {
        return true;
    }

    @Override
    public Set<Class<? extends Element>> compatibleWith() {
        return Collections.singleton(DataElement.class);
    }
}
