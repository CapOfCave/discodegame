package me.kecker.discodegame.bot.domain.commands;

import lombok.NonNull;

import java.util.Collection;

public interface BotCommand {

    @NonNull
    String getName();

    @NonNull
    Collection<String> getAliases();

    //TODO add arguments
    void accept();
}
