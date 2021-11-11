package me.kecker.discodegame.core.domain.exceptions;

public class PlayerNotInGameException extends Exception {
    public PlayerNotInGameException(String playerId, String message) {
        super(String.format("Player %s is currently not in a game: %s", playerId, message));
    }
}
