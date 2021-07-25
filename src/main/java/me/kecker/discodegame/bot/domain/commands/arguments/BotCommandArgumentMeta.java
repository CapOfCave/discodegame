package me.kecker.discodegame.bot.domain.commands.arguments;

import lombok.NonNull;
import me.kecker.discodegame.bot.domain.commands.arguments.types.ArgumentType;

import java.util.Collection;
import java.util.Collections;

public class BotCommandArgumentMeta<T> {

    @NonNull
    private String name;

    @NonNull
    private Collection<String> aliases;

    @NonNull
    private ArgumentType<T> type;

    @NonNull
    private ArgumentNecessity necessity;

    private BotCommandArgumentMeta(@NonNull String name, @NonNull ArgumentType<T> type, @NonNull Collection<String> aliases, ArgumentNecessity necessity) {
        this.name = name;
        this.type = type;
        this.aliases = aliases;
        this.necessity = necessity;
    }

    public static <T> BotCommandArgumentMeta<T> of(@NonNull String name, @NonNull ArgumentType<T> type) {
        return of(name, type, Collections.emptyList(), ArgumentNecessity.REQUIRED);
    }

    public static <T> BotCommandArgumentMeta<T> of(@NonNull String name, @NonNull ArgumentType<T> type, @NonNull ArgumentNecessity necessity) {
        return of(name, type, Collections.emptyList(), necessity);
    }

    public static <T> BotCommandArgumentMeta<T> of(@NonNull String name, @NonNull ArgumentType<T> type, @NonNull Collection<String> aliases, @NonNull ArgumentNecessity necessity) {
        return new BotCommandArgumentMeta<>(name, type, aliases, necessity);
    }

}
