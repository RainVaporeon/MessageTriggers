package com.spiritlight.messagetriggers.functions;

import com.spiritlight.messagetriggers.objects.elements.Element;
import com.spiritlight.messagetriggers.objects.instructions.AbstractInstruction;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Instructions {

    public static final Instructions instance = new Instructions();

    private final Set<AbstractInstruction> registeredInstructions = new HashSet<>();

    public void register(AbstractInstruction... instruction) {
        this.registeredInstructions.addAll(Arrays.asList(instruction));
    }

    public void unregister(AbstractInstruction... instruction) {
        Arrays.asList(instruction).forEach(this.registeredInstructions::remove);
    }

    public List<String> getCompatibleNames(Class<? extends Element> type) {
        return this.registeredInstructions.stream()
                .filter(element -> element.getClass().isAssignableFrom(type))
                .map(Element::getName).collect(Collectors.toList());
    }

    public AbstractInstruction get(String name) {
        for(AbstractInstruction instruction : this.registeredInstructions) {
            if(instruction.getName().equals(name)) return instruction;
        }
        return null;
    }
}
