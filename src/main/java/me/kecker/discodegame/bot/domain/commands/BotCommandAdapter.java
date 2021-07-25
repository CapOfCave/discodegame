package me.kecker.discodegame.bot.domain.commands;

import lombok.NonNull;

import java.util.Collection;
import java.util.Collections;

public abstract class BotCommandAdapter implements BotCommand {

    private final String name;
    private final Collection<String> aliases;

    public BotCommandAdapter(@NonNull String name) {
        this(name, Collections.emptyList());
    }

    public BotCommandAdapter(@NonNull String name, @NonNull Collection<String> aliases) {
        this.name = name;
        this.aliases = aliases;
    }

    @Override
    public @NonNull String getName() {
        return name;
    }

    @Override
    public @NonNull Collection<String> getAliases() {
        return aliases;
    }

}
