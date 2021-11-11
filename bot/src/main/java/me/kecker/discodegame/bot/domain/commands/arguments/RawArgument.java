package me.kecker.discodegame.bot.domain.commands.arguments;

import lombok.NonNull;

public record RawArgument(String key, @NonNull String value) {
}
