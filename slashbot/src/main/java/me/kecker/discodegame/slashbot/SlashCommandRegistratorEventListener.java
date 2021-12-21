package me.kecker.discodegame.slashbot;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildAvailableEvent;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
public class SlashCommandRegistratorEventListener extends ListenerAdapter {

    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event) {
        Guild guild = event.getGuild();
        System.out.println(guild.getName());
        System.out.println(guild.isLoaded());
//        guild.updateCommands()
//                .addCommands(
//                        new CommandData("pingme", "test command for discodegame"))
//                .queue();
    }

    @Override
    public void onGuildAvailable(@NotNull GuildAvailableEvent event) {
        System.out.println("available");
    }

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        System.out.println("join");
    }

}
