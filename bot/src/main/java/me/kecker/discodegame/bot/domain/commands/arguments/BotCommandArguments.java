package me.kecker.discodegame.bot.domain.commands.arguments;

import java.util.Map;
import java.util.Optional;

public record BotCommandArguments(Map<String, BotCommandArgument<?>> arguments) {

    @SuppressWarnings("unchecked") // types are checked because of the way arguments was filled
    public <T> T getRequired(BotCommandArgumentMeta<T> argumentMeta) {
        if (argumentMeta.necessity() != ArgumentNecessity.REQUIRED) {
            throw new IllegalArgumentException("getRequiredArg can only be called with an argumentMeta whose necessity is REQUIRED.");
        }
        return (T) arguments.get(argumentMeta.name()).value();
    }

    @SuppressWarnings("unchecked") // types are checked because of the way arguments was filled
    public <T> Optional<T> get(BotCommandArgumentMeta<T> argumentMeta) {
        return Optional.ofNullable((BotCommandArgument<T>) arguments.get(argumentMeta.name())).map(BotCommandArgument::value);

    }
}
