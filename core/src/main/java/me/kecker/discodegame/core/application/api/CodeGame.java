package me.kecker.discodegame.core.application.api;

import lombok.NonNull;
import me.kecker.discodegame.core.domain.Game;
import me.kecker.discodegame.core.domain.Player;
import me.kecker.discodegame.core.domain.exceptions.PlayerNotInGameException;
import me.kecker.discodegame.core.domain.exceptions.PlayerNotInLobbyException;
import me.kecker.discodegame.core.domain.exceptions.UnsupportedLanguageException;

public interface CodeGame {

    void createLobby(@NonNull LobbyCreateRequest lobbyCreateRequest);

    void joinLobby(@NonNull String lobbyId, @NonNull Player player);

    void startGame(@NonNull String playerId, @NonNull Game game) throws PlayerNotInLobbyException;

    void submitResult(@NonNull String playerId, String language, String code) throws PlayerNotInGameException, UnsupportedLanguageException;
}
