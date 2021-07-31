package me.kecker.discodegame.application.events;

import me.kecker.discodegame.domain.events.CodeGameEvent;
import me.kecker.discodegame.domain.events.EventSubscriber;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

@Component
public class EventManager {

    private Collection<EventSubscriber> subscribers = new HashSet<>();

    public void omit(CodeGameEvent event){
        subscribers.forEach(eventSubscriber -> eventSubscriber.accept(event));
    }


}
