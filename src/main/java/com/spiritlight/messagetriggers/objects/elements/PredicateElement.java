package com.spiritlight.messagetriggers.objects.elements;

import com.spiritlight.messagetriggers.objects.instructions.predicates.GamePredicate;

public class PredicateElement extends StatelessElement {
    private final GamePredicate predicate;

    public PredicateElement(GamePredicate predicate, String name) {
        super(name);
        this.predicate = predicate;
    }

    public GamePredicate getPredicate() {
        return predicate;
    }
}
