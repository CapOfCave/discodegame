package me.kecker.discodegame.domain;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Lobby {

    @NonNull
    private final String id;

    @NonNull
    private final Map<String, Player> playersById;

    private int maxPlayers;

    private Game activeGame = null;

    public Lobby(@NonNull String id, int maxPlayers) {
        this(id, maxPlayers, Collections.emptySet());
    }

    public Lobby(@NonNull String id, int maxPlayers, @NonNull Collection<? extends Player> players) {
        this.id = id;
        this.maxPlayers = maxPlayers;
        this.playersById = players.stream().collect(Collectors.toMap(Player::getId, Function.identity()));
    }

    public void addPlayer(Player player) {
        this.playersById.put(player.getId(), player);
    }

    public boolean isFull() {
        return this.maxPlayers != 0 && getPlayerCount() >= this.maxPlayers;
    }

    public int getPlayerCount() {
        return this.playersById.size();
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public boolean containsPlayer(Player player) {
        return this.containsPlayer(player.getId());
    }

    public boolean containsPlayer(String playerId) {
        return this.playersById.containsKey(playerId);
    }

    public void startGame(Game game) {
        this.activeGame = game;
    }

    public void stopGame() {
        if (this.activeGame == null) {
            throw new IllegalStateException("No game running");
        }
        this.activeGame = null;
    }

    public Optional<Game> getActiveGame() {
        return Optional.ofNullable(this.activeGame);
    }
}
