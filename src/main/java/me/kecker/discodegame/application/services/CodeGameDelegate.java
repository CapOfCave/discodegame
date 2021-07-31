package me.kecker.discodegame.application.services;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import me.kecker.discodegame.application.api.CodeGame;
import me.kecker.discodegame.application.api.LobbyCreateRequest;
import me.kecker.discodegame.domain.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class CodeGameDelegate implements CodeGame {

    private final SessionManager sessionManager;

    @Override
    public void createLobby(@NonNull LobbyCreateRequest lobbyCreateRequest) {
        this.sessionManager.createLobby(lobbyCreateRequest);
        log.info("Successfully created lobby with id '{}'.", lobbyCreateRequest.id());
    }

    @Override
    public void joinLobby(@NonNull String lobbyId, @NonNull Player player) {
        this.sessionManager.joinLobby(lobbyId, player);
        log.info("Player {} successfully joined lobby with id '{}'.", player, lobbyId);
    }
}
