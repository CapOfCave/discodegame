package me.kecker.discodegame.application.api;

import lombok.NonNull;
import me.kecker.discodegame.domain.Player;

public interface CodeGame {

    void createLobby(@NonNull LobbyCreateRequest lobbyCreateRequest);

    void joinLobby(@NonNull String lobbyId, @NonNull Player player);
}
