package me.kecker.discodegame.bot.domain.commands.arguments;

public record BotCommandArgument<T>(BotCommandArgumentMeta<T> meta, T value) {
}
