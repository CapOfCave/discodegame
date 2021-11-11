package me.kecker.discodegame.bot.domain.commands;

import lombok.NonNull;
import me.kecker.discodegame.bot.domain.commands.arguments.ArgumentNecessity;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgument;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgumentMeta;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

public abstract class BotCommandMetaAdapter implements BotCommandMeta {

    private final String name;
    private final Collection<String> aliases;
    private final List<BotCommandArgumentMeta<?>> argumentMetas;
    private final Map<String, BotCommandArgumentMeta<?>> argumentMetasByName;

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
        this.argumentMetasByName =
                this.argumentMetas
                        .stream()
                        .collect(Collectors.toMap(BotCommandArgumentMeta::name, identity()));

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
    public @NonNull BotCommandArgumentMeta<?> getArgumentMeta(@NonNull String name) {
        if (!this.argumentMetasByName.containsKey(name)) {
            throw new IllegalStateException("Unknown argument Meta: " + name + ", known argumentMetas are " + this.argumentMetasByName.keySet());
        }
        return this.argumentMetasByName.get(name);
    }

    @Override
    public @NonNull BotCommandArgumentMeta<?> getArgumentMeta(int i) {
        return this.argumentMetas.get(i);
    }

    @Override
    public int getArgumentCount() {
        return this.argumentMetas.size();
    }

}
