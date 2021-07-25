package me.kecker.discodegame.bot.domain.commands;

import lombok.NonNull;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgumentMeta;

import java.util.Collection;
import java.util.List;

public interface BotCommandMeta {

    @NonNull
    String getName();

    @NonNull
    Collection<String> getAliases();

    //TODO add arguments
    void accept();

    @NonNull
    List<BotCommandArgumentMeta<?>> getArgumentMetas();
}
