package me.kecker.discodegame.bot.domain.commands;

import lombok.NonNull;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgument;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgumentMeta;

import java.util.Collection;
import java.util.Map;

public interface BotCommandMeta {

    @NonNull
    String getName();

    @NonNull
    Collection<String> getAliases();

    //TODO add caller id
    void accept(Map<String, BotCommandArgument<?>> argumentsByName);

    @NonNull
    BotCommandArgumentMeta<?> getArgumentMeta(@NonNull String name);

    @NonNull
    BotCommandArgumentMeta<?> getArgumentMeta(int i);

    int getArgumentCount();
}
