package me.kecker.discodegame.application.api;

import lombok.NonNull;
import me.kecker.discodegame.domain.Player;

public interface CodeGame {

    void createLobby(@NonNull String id);

    void joinLobby(@NonNull String lobbyId, @NonNull Player player);
}
