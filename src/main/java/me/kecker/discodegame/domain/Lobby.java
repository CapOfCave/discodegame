package me.kecker.discodegame.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;

@RequiredArgsConstructor
public class Lobby {

    @NonNull
    private String id;

    private Guild guild;

}
