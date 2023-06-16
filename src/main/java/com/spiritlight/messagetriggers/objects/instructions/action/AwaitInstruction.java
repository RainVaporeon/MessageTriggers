package com.spiritlight.messagetriggers.objects.instructions.action;

import com.spiritlight.messagetriggers.exceptions.events.EventListening;
import com.spiritlight.messagetriggers.exceptions.functions.ArgumentMismatchException;
import com.spiritlight.messagetriggers.objects.elements.Element;
import com.spiritlight.messagetriggers.objects.elements.PredicateElement;
import com.spiritlight.messagetriggers.objects.instructions.StatefulInstruction;
import com.spiritlight.messagetriggers.objects.instructions.predicates.EventPredicate;
import com.spiritlight.messagetriggers.objects.instructions.predicates.GamePredicate;

import java.util.Collections;
import java.util.Set;

public class AwaitInstruction extends StatefulInstruction {

    public AwaitInstruction() {
        super("await", 1);
    }

    @Override
    public boolean accept(Element... parameters) throws ArgumentMismatchException {
        Element param = parameters[0];
        if(param instanceof PredicateElement) {
            PredicateElement element = (PredicateElement) parameters[0];
            if(element.getPredicate() instanceof EventPredicate) {
                throw new EventListening(((EventPredicate) element.getPredicate()));
            }
        } else if (param instanceof EventPredicate) {
            throw new EventListening((EventPredicate) param);
        }
        throw new ArgumentMismatchException("Not an EventPredicate");
    }

    @Override
    public Set<Class<? extends Element>> compatibleWith() {
        return Collections.singleton(EventPredicate.class);
    }
}
