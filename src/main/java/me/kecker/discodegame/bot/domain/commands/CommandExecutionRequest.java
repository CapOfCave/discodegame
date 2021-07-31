package me.kecker.discodegame.bot.domain.commands;

import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgument;

import java.util.Map;

public record CommandExecutionRequest(BotCommandMeta commandMeta, Map<String, BotCommandArgument<?>> arguments) {
}