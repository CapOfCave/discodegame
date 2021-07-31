package me.kecker.discodegame.application.services;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import me.kecker.discodegame.application.api.LobbyCreateRequest;
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


    public void createLobby(LobbyCreateRequest lobbyCreateRequest) {
        if (this.activeLobbies.containsKey(lobbyCreateRequest.id())) {
            throw new IllegalStateException(String.format("Lobby with id '%s' already exists! Cannot create lobby", lobbyCreateRequest.id()));
        }
        Lobby lobby = new Lobby(lobbyCreateRequest.id(), lobbyCreateRequest.maxPlayers());
        this.activeLobbies.put(lobbyCreateRequest.id(), lobby);

        this.eventManager.omit(new LobbyCreatedEvent());
    }

    public void joinLobby(String lobbyId, Player player) {
        if (!this.activeLobbies.containsKey(lobbyId)) {
            throw new IllegalArgumentException(String.format("Unknown lobby: '%s'. Cannot join lobby", lobbyId));
        }

        Lobby lobby = this.activeLobbies.get(lobbyId);
        if (lobby.isFull()) {
            throw new IllegalStateException(
                    String.format("Lobby with id '%s' has already reached %d/%d players! Cannot join lobby.",
                            lobby.getId(), lobby.getPlayerCount(), lobby.getMaxPlayers()));
        }
        if (lobby.containsPlayer(player)) {
            throw new IllegalStateException(
                    String.format("Player %s is already a member of lobby with id %s. Cannot join lobby.", player, lobby.getId()));
        }
        lobby.addPlayer(player);
    }
}
