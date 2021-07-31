package me.kecker.discodegame.bot.domain.commands;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;

public record CommandExecutionContext(
        Guild guild,
        Member member) {
}
