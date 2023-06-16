package com.spiritlight.messagetriggers.objects.instructions.action;

import com.spiritlight.messagetriggers.collections.Pair;
import com.spiritlight.messagetriggers.events.EventManager;
import com.spiritlight.messagetriggers.events.EventSubscriber;
import com.spiritlight.messagetriggers.exceptions.events.PredicateListening;
import com.spiritlight.messagetriggers.exceptions.functions.AcceptException;
import com.spiritlight.messagetriggers.exceptions.functions.ArgumentMismatchException;
import com.spiritlight.messagetriggers.functions.TriggerFunction;
import com.spiritlight.messagetriggers.objects.elements.Element;
import com.spiritlight.messagetriggers.objects.instructions.StatefulInstruction;
import com.spiritlight.messagetriggers.objects.instructions.predicates.GamePredicate;
import com.spiritlight.messagetriggers.utils.ExceptionHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class WhenInstruction extends StatefulInstruction implements EventSubscriber, TriggerFunction {

    protected final Map<UUID, Pair<TriggerFunction, List<Element>>> queue = new ConcurrentHashMap<>();

    public WhenInstruction() {
        super("when", 1);
        EventManager.instance.addSubscriber(TickEvent.ClientTickEvent.class, this);
    }

    @Override
    public boolean accept(Element... parameters) throws ArgumentMismatchException {
        Element param = parameters[0];
        if(param instanceof GamePredicate) {
            throw new PredicateListening((GamePredicate) param);
        }
        throw new ArgumentMismatchException("Not an GamePredicate");
    }

    @Override
    public Set<Class<? extends Element>> compatibleWith() {
        return Collections.singleton(GamePredicate.class);
    }

    @Override
    public void accept(List<Element> instructions) throws AcceptException {
        this.defaultAccept(instructions);
    }

    @Override
    public String getKeyword() {
        return null;
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
