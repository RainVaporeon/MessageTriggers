package com.spiritlight.messagetriggers.objects.instructions.predicates;

import com.spiritlight.messagetriggers.collections.Pair;
import com.spiritlight.messagetriggers.exceptions.functions.ArgumentMismatchException;
import com.spiritlight.messagetriggers.functions.TriggerFunction;
import com.spiritlight.messagetriggers.objects.elements.Element;
import com.spiritlight.messagetriggers.objects.elements.data.DataElement;
import com.spiritlight.messagetriggers.objects.instructions.StatelessInstruction;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A game predicate, intended to be match conditions in a game
 */
public abstract class GamePredicate extends StatelessInstruction {

    /**
     * The queue backing this predicate, a UUID is generated upon registration
     * to distinguish separate actions, and then a Pair where the key
     * is the function, and the element list act as a sequence of instructions
     * for the function to accept.
     */
    protected final Map<UUID, Pair<TriggerFunction, List<Element>>> queue = new ConcurrentHashMap<>();

    public GamePredicate(String name) {
        super(name, 0);
    }

    /**
     * Registers this trigger function to the event.
     * @param function The function to receive the instructions once triggered
     * @param instructions The instructions to execute
     */
    public void register(TriggerFunction function, List<Element> instructions) {
        queue.put(UUID.randomUUID(), Pair.of(function, instructions));
    }

    /**
     * Tests whether this condition is true.
     * @param args The argument to be passed in
     * @return {@code true} if the test passes, {@code false} otherwise
     */
    public abstract boolean test(DataElement... args) throws ArgumentMismatchException;
}
