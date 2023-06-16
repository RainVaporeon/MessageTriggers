package com.spiritlight.messagetriggers.exceptions.events;

import com.spiritlight.messagetriggers.objects.instructions.predicates.EventPredicate;
import com.spiritlight.messagetriggers.objects.instructions.predicates.GamePredicate;
import com.spiritlight.messagetriggers.objects.instructions.predicates.GuiOpenPredicate;

/**
 * Indicates that one or more event is currently listening.
 * <p>
 * This does not name like ordinary Exceptions as this indicates
 * a termination, but not due to any errors, and should not
 * be treated as one either.
 */
public class EventListening extends RuntimeException {

    private final EventPredicate predicate;

    public EventListening(EventPredicate predicate) {
        this.predicate = predicate;
    }

    public EventPredicate getPredicate() {
        return predicate;
    }
}
