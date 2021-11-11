package me.kecker.discodegame.core.application.services;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import me.kecker.discodegame.core.application.api.CodeGame;
import me.kecker.discodegame.core.application.api.LobbyCreateRequest;
import me.kecker.discodegame.core.application.api.TestCaseExecutor;
import me.kecker.discodegame.core.domain.Game;
import me.kecker.discodegame.core.domain.Lobby;
import me.kecker.discodegame.core.domain.Player;
import me.kecker.discodegame.core.domain.exceptions.PlayerNotInGameException;
import me.kecker.discodegame.core.domain.exceptions.PlayerNotInLobbyException;
import me.kecker.discodegame.core.domain.exceptions.UnsupportedLanguageException;
import me.kecker.discodegame.core.domain.executor.TestCaseExecutionRequest;
import me.kecker.discodegame.core.domain.executor.TestCaseExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
public class CodeGameDelegate implements CodeGame {

    private final SessionManager sessionManager;
    private final TestCaseExecutor testCaseExecutor;

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

    @Override
    public void startGame(@NonNull String playerId, @NonNull Game game) throws PlayerNotInLobbyException {
        Lobby lobby = this.sessionManager.getLobbyForPlayer(playerId).orElseThrow(() -> new PlayerNotInLobbyException(playerId));
        lobby.startGame(game);
        log.info("Game successfully started in lobby {}", lobby.getId());
    }

    @Override
    public void submitResult(@NonNull String playerId, String language, String sourceCode) throws PlayerNotInGameException, UnsupportedLanguageException {
        Lobby lobby = this.sessionManager.getLobbyForPlayer(playerId).orElseThrow(() -> new PlayerNotInGameException(playerId, "The player is not in a lobby."));
        Game activeGame = lobby.getActiveGame().orElseThrow(() -> new PlayerNotInGameException(playerId, "The game has not started yet."));
        TestCaseExecutionResult result = this.testCaseExecutor.execute(new TestCaseExecutionRequest(activeGame.getStdIn(), language, sourceCode));
        log.info("Successfully executed test case for player {} in {}, the result is {}", playerId, language, result);

    }
}
