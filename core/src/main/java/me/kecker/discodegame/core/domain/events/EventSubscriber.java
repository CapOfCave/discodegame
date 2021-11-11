package me.kecker.discodegame.core.domain.events;

import java.util.Set;

public interface EventSubscriber {

    /**
     * The events that this class handles. Will be called multiple times, so it should be relatively performant.
     *
     * @return A Set of all CodeGameEvent classes that this EventSubscriber listens to.
     */
    Set<Class<? extends CodeGameEvent>> getRelevantEvents();

    /**
     * Accept an event.
     * @param event The event that was omitted. It is guaranteed that the event will be one of the events specified in {@link #getRelevantEvents()}.
     */
    void accept(CodeGameEvent event);


}
