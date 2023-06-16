package com.spiritlight.messagetriggers.test;

import com.spiritlight.messagetriggers.events.EventManager;
import com.spiritlight.messagetriggers.exceptions.functions.ArgumentMismatchException;
import com.spiritlight.messagetriggers.exceptions.functions.AcceptException;
import com.spiritlight.messagetriggers.functions.TriggerFunction;
import com.spiritlight.messagetriggers.objects.elements.data.DataElement;
import com.spiritlight.messagetriggers.objects.elements.Element;
import com.spiritlight.messagetriggers.objects.instructions.predicates.EventPredicate;

import java.util.List;

public class TestPredicate extends EventPredicate implements TriggerFunction {

    public TestPredicate() {
        super("event.test");
        EventManager.instance.addSubscriber(TestEvent.class, this);
    }

    @Override
    public void accept(List<Element> instructions) throws AcceptException {
        this.defaultAccept(instructions);
    }

    @Override
    public boolean test(DataElement... args) throws ArgumentMismatchException {
        return true;
    }
}
