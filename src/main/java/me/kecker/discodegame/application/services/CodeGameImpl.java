package me.kecker.discodegame.application.services;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import me.kecker.discodegame.application.api.CodeGame;
import me.kecker.discodegame.domain.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class CodeGameImpl implements CodeGame {

    private final SessionManager sessionManager;

    @Override
    public void createLobby(@NonNull String id) {
        this.sessionManager.createLobby(id);
    }

    @Override
    public void joinLobby(@NonNull String lobbyId, @NonNull Player player) {
        this.sessionManager.joinLobby(lobbyId, player);
    }
}
