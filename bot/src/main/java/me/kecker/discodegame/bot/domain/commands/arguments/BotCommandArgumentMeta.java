package me.kecker.discodegame.bot.domain.commands.arguments;

import lombok.NonNull;
import me.kecker.discodegame.bot.domain.commands.arguments.types.ArgumentType;

import java.util.Collection;
import java.util.Collections;

public record BotCommandArgumentMeta<T>(
        @NonNull String name,
        @NonNull Collection<String> aliases,
        @NonNull ArgumentType<T> type,
        @NonNull ArgumentNecessity necessity) {

    public static <T> BotCommandArgumentMeta<T> of(@NonNull String name, @NonNull ArgumentType<T> type) {
        return of(name, type, Collections.emptyList(), ArgumentNecessity.REQUIRED);
    }

    public static <T> BotCommandArgumentMeta<T> of(@NonNull String name, @NonNull ArgumentType<T> type, @NonNull ArgumentNecessity necessity) {
        return of(name, type, Collections.emptyList(), necessity);
    }

    public static <T> BotCommandArgumentMeta<T> of(@NonNull String name, @NonNull ArgumentType<T> type, @NonNull Collection<String> aliases, @NonNull ArgumentNecessity necessity) {
        return new BotCommandArgumentMeta<>(name, aliases, type, necessity);
    }
}
