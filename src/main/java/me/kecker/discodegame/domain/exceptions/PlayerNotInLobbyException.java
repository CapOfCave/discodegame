package me.kecker.discodegame.domain.exceptions;

public class PlayerNotInLobbyException extends Exception {
    public PlayerNotInLobbyException(String playerId) {
        super(String.format("Player %s is currently not in a lobby", playerId));
    }
}
