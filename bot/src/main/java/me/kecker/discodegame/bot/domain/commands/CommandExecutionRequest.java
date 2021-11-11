package me.kecker.discodegame.bot.domain.commands;

import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgument;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArguments;

import java.util.Map;

public record CommandExecutionRequest(BotCommandMeta commandMeta, BotCommandArguments arguments) {
}