package com.spiritlight.messagetriggers.exceptions.events;

import com.spiritlight.messagetriggers.objects.instructions.predicates.GamePredicate;

public class PredicateListening extends RuntimeException {
    private final GamePredicate predicate;

    public PredicateListening(GamePredicate predicate) {
        this.predicate = predicate;
    }

    public GamePredicate getPredicate() {
        return predicate;
    }
}
