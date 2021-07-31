package me.kecker.discodegame.application.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.kecker.discodegame.application.events.EventManager;
import me.kecker.discodegame.domain.Lobby;
import me.kecker.discodegame.domain.Player;
import me.kecker.discodegame.domain.events.LobbyCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SessionManager {

    @NonNull
    private final EventManager eventManager;

    @NonNull
    private final Map<String, Lobby> activeLobbies = new HashMap<>();


    public void createLobby(String id) {
        Lobby lobby = new Lobby(id);
        this.activeLobbies.put(id, lobby);

        this.eventManager.omit(new LobbyCreatedEvent());
    }

    public void joinLobby(String lobbyId, Player player) {
        if (!this.activeLobbies.containsKey(lobbyId)) {
            throw new IllegalArgumentException(String.format("Unknown lobby: %s.", lobbyId));
        }
        this.activeLobbies.get(lobbyId).addPlayer(player);
    }
}
