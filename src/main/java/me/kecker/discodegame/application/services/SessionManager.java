package me.kecker.discodegame.application.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.kecker.discodegame.application.events.EventManager;
import me.kecker.discodegame.domain.Lobby;
import me.kecker.discodegame.domain.events.LobbyCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SessionManager {

    private EventManager eventManager;

    @NonNull
    private Collection<Lobby> activeLobbies = new HashSet<>();


    public void createLobby(String id) {
        Lobby lobby = new Lobby(id);
        this.activeLobbies.add(lobby);

        this.eventManager.omit(new LobbyCreatedEvent());
    }
}
