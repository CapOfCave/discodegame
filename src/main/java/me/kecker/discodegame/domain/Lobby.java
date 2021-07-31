package me.kecker.discodegame.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public class Lobby {

    @NonNull
    private final String id;

    @NonNull
    private final Collection<Player> players;

    private int maxPlayers;

    public Lobby(@NonNull String id) {
        this(id, 0, Collections.emptySet());
    }

    public Lobby(@NonNull String id, int maxPlayers) {
        this(id, maxPlayers, Collections.emptySet());
    }

    public Lobby(@NonNull String id, int maxPlayers, @NonNull Collection<? extends Player> players) {
        this.id = id;
        this.maxPlayers = maxPlayers;
        this.players = new HashSet<>(players);
    }



    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public boolean isFull() {
        return this.maxPlayers != 0 && getPlayerCount() >= this.maxPlayers;
    }

    public int getPlayerCount() {
        return this.players.size();
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public String getId() {
        return id;
    }

    public boolean containsPlayer(Player player) {
        return this.players.contains(player);
    }
}
