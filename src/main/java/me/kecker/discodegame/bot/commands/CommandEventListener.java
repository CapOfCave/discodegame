package me.kecker.discodegame.bot.commands;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import me.kecker.discodegame.bot.domain.annotations.RegisteredEventListener;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@RegisteredEventListener
@Component
public class CommandEventListener extends ListenerAdapter {

    @NonNull
    private final CommandParser commandParser;

    @NonNull
    private final CommandManager commandManager;

    @Autowired
    public CommandEventListener(@NonNull CommandParser commandParser, @NonNull CommandManager commandManager) {
        this.commandParser = commandParser;
        this.commandManager = commandManager;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (!this.commandParser.isCommand(event.getMessage())) {
            return;
        }
        this.commandParser
                .mapToCommandExecutionDTO(event.getMessage())
                .ifPresent(this.commandManager::tryExecute);
    }


}
