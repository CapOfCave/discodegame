package me.kecker.discodegame.application.api;

import lombok.NonNull;
import me.kecker.discodegame.domain.Game;
import me.kecker.discodegame.domain.Player;
import me.kecker.discodegame.domain.exceptions.PlayerNotInGameException;
import me.kecker.discodegame.domain.exceptions.PlayerNotInLobbyException;
import me.kecker.discodegame.domain.exceptions.UnsupportedLanguageException;

public interface CodeGame {

    void createLobby(@NonNull LobbyCreateRequest lobbyCreateRequest);

    void joinLobby(@NonNull String lobbyId, @NonNull Player player);

    void startGame(@NonNull String playerId, @NonNull Game game) throws PlayerNotInLobbyException;

    void submitResult(@NonNull String playerId, String language, String code) throws PlayerNotInGameException, UnsupportedLanguageException;
}
