package com.spiritlight.messagetriggers.events;

import com.spiritlight.messagetriggers.objects.instructions.predicates.GuiOpenPredicate;

/**
 * A general interface for classes to implement when subscribing to
 * {@link EventManager} for certain events. This interface only
 * has one method, {@link EventSubscriber#onEvent()}.
 */
public interface EventSubscriber {

    /**
     * Acts as a callback. This method is called if and only
     * if the object listening to a certain event has received
     * an event. The method does not intake any method by default
     * as any {@link GuiOpenPredicate} instance
     * only listens to one event at one time.
     */
    void onEvent();
}
