package com.spiritlight.messagetriggers.objects.instructions.predicates;

import com.spiritlight.messagetriggers.collections.Pair;
import com.spiritlight.messagetriggers.events.EventSubscriber;
import com.spiritlight.messagetriggers.exceptions.functions.AcceptException;
import com.spiritlight.messagetriggers.functions.TriggerFunction;
import com.spiritlight.messagetriggers.objects.elements.Element;
import com.spiritlight.messagetriggers.utils.ExceptionHandler;

import java.util.LinkedList;
import java.util.List;


/**
 * The base class for all event predicates.
 * This class implements {@link TriggerFunction} by default to provide
 * the class better accessibility to execution of instructions, and implements
 * the {@link EventSubscriber} interface to allow receiving notifications for received
 * events.
 */
public abstract class EventPredicate extends GamePredicate implements EventSubscriber, TriggerFunction {

    public EventPredicate(String name) {
        super(name);
    }

    @Override
    public void accept(List<Element> instructions) throws AcceptException {
        this.defaultAccept(instructions);
    }

    @Override
    public String getKeyword() {
        return this.name;
    }

    @Override
    public void onEvent() {
        this.queue.forEach((uuid, function) -> {
            try {
                function.getKey().accept(function.getValue());
                this.queue.remove(uuid);
            } catch (AcceptException e) {
                ExceptionHandler.handleException(e);
            }
        });
    }
}
