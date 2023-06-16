package com.spiritlight.messagetriggers.test;

import com.spiritlight.messagetriggers.exceptions.functions.ArgumentMismatchException;
import com.spiritlight.messagetriggers.objects.elements.Element;
import com.spiritlight.messagetriggers.objects.elements.data.DataElement;
import com.spiritlight.messagetriggers.objects.elements.data.NumericElement;
import com.spiritlight.messagetriggers.objects.elements.data.StringElement;
import com.spiritlight.messagetriggers.objects.instructions.StatefulInstruction;

import java.util.Map;
import java.util.Set;

public class TestInstruction extends StatefulInstruction {
    public TestInstruction() {
        super("print", 3);
    }

    @Override
    public boolean accept(Element... parameters) throws ArgumentMismatchException {
        System.out.println(" - - - Start Dump - - -");
        System.out.println("Found parameters of size " + parameters.length);
        System.out.println("Inserted elements: ");
        int i = 1;
        for(Element element : parameters) {
            if(!(element instanceof DataElement)) throw new ArgumentMismatchException("Expected DataElement!");
            if(element instanceof StringElement) this.addVariable("val" + i++, ((StringElement) element).getValue());
            if(element instanceof NumericElement) this.addVariable("val" + i++, element.getMappedVariable(NumericElement.VALUE));
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
        return null;
    }
}
