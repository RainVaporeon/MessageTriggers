package com.spiritlight.messagetriggers.events;

import com.spiritlight.messagetriggers.collections.ImmutableSet;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class EventManager {
    public static final EventManager instance = new EventManager();

    private final Map<Class<?>, Set<EventSubscriber>> subscribers = new ConcurrentHashMap<>();

    private final Set<EventSubscriber> EMPTY = new ImmutableSet<>();

    private EventManager() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void addSubscriber(Class<?> type, EventSubscriber target) {
        this.putIfAbsent(type);
        this.subscribers.get(type).add(target);
    }

    public void removeSubscriber(Class<?> type, EventSubscriber target) {
        this.putIfAbsent(type);
        this.subscribers.get(type).remove(target);
    }

    private Set<EventSubscriber> getSubscribers(Class<?> type) {
        return subscribers.getOrDefault(type, EMPTY);
    }

    private void putIfAbsent(Class<?> type) {
        subscribers.putIfAbsent(type, new HashSet<>());
    }

    @SubscribeEvent
    public void onEvent(Event event) {
        Class<?> clazz = event.getClass();
        for(EventSubscriber subscriber : this.getSubscribers(clazz)) {
            subscriber.onEvent();
        }
    }
}
