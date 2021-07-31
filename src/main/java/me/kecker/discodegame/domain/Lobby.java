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

    public Lobby(@NonNull String id) {
        this(id, Collections.emptySet());
    }

    public Lobby(@NonNull String id, @NonNull Collection<? extends Player> players) {
        this.id = id;
        this.players = new HashSet<>(players);
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }
}
