package me.kecker.discodegame.bot.domain.commands;

import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgument;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgumentMeta;

import java.util.Map;

public class CommandExecution {
    private String commandName;
    private Map<BotCommandArgumentMeta<?>, BotCommandArgument> argumentsByArgumentMeta;
}
