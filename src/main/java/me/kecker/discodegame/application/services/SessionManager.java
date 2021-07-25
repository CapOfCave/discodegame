package me.kecker.discodegame.application.services;

import me.kecker.discodegame.domain.Lobby;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SessionManager {

    public Collection<Lobby> activeLobbies;

    public void createLobby(String id) {
        Lobby lobby = new Lobby(id);
        activeLobbies.add(lobby);
    }
}
