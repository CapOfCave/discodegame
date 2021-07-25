package me.kecker.discodegame.bot.domain.commands;

import lombok.NonNull;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgumentMeta;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class BotCommandMetaAdapter implements BotCommandMeta {

    private final String name;
    private final Collection<String> aliases;
    private final List<BotCommandArgumentMeta<?>> argumentMetas;

    public BotCommandMetaAdapter(@NonNull String name) {
        this(name, Collections.emptyList(), Collections.emptyList());
    }

    public BotCommandMetaAdapter(@NonNull String name, List<BotCommandArgumentMeta<?>> argumentMetas) {
        this(name, Collections.emptyList(), argumentMetas);
    }

    public BotCommandMetaAdapter(@NonNull String name, @NonNull Collection<String> aliases, List<BotCommandArgumentMeta<?>> argumentMetas) {
        this.name = name;
        this.aliases = aliases;
        this.argumentMetas = argumentMetas;

    }

    @Override
    public @NonNull String getName() {
        return this.name;
    }

    @Override
    public @NonNull Collection<String> getAliases() {
        return this.aliases;
    }

    @Override
    public @NonNull List<BotCommandArgumentMeta<?>> getArgumentMetas() {
        return this.argumentMetas;
    }
}
