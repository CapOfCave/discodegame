package me.kecker.discodegame.application.events;

import me.kecker.discodegame.domain.events.CodeGameEvent;
import me.kecker.discodegame.domain.events.EventSubscriber;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class EventManager {

    private Collection<EventSubscriber> subscribers;

    public void omit(CodeGameEvent event){
        subscribers.forEach(eventSubscriber -> eventSubscriber.accept(event));
    }


}
