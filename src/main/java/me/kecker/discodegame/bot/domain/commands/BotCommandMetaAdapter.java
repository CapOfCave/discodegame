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

    @SuppressWarnings("unchecked") // types are checked because of the way arguments was filled
    protected <T> Optional<T> getArg(Map<String, BotCommandArgument<?>> arguments, BotCommandArgumentMeta<T> argumentMeta) {
        return Optional.ofNullable((BotCommandArgument<T>) arguments.get(argumentMeta.name())).map(BotCommandArgument::value);
    }

    //TODO make required args required
    @SuppressWarnings("unchecked") // types are checked because of the way arguments was filled
    protected <T> T getRequiredArg(Map<String, BotCommandArgument<?>> arguments, BotCommandArgumentMeta<T> argumentMeta) {
        if (argumentMeta.necessity() != ArgumentNecessity.REQUIRED){
            throw new IllegalArgumentException("getRequiredArg can only be called with an argumentMeta whose necessity is REQUIRED.");
        }
        return (T) arguments.get(argumentMeta.name()).value();
    }
}
