package me.kecker.discodegame.bot.architecture.commands;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import me.kecker.discodegame.bot.domain.annotations.RegisteredEventListener;
import me.kecker.discodegame.bot.domain.annotations.RegisteredGuildCommand;
import me.kecker.discodegame.bot.domain.commands.BotCommand;
import me.kecker.discodegame.utils.StreamUtils;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RegisteredEventListener
@Component
public class CommandEventListener extends ListenerAdapter {

    @NonNull
    private CommandParser commandParser;

    @NonNull
    private Collection<? extends BotCommand> guildCommands;

    @Autowired
    public CommandEventListener(@NonNull CommandParser commandParser, Collection<? extends BotCommand> allCommands) {
        this.commandParser = commandParser;
        this.guildCommands = allCommands
                .stream()
                .filter(botCommand -> botCommand.getClass().isAnnotationPresent(RegisteredGuildCommand.class))
                .collect(Collectors.toSet());
        log.info("CommandEventListener found {} commands, of which {} are guild commands.", allCommands.size(), this.guildCommands.size());
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (!this.commandParser.isCommand(event.getMessage())) {
            return;
        }
        String commandName = this.commandParser.getCommandName(event.getMessage());
        executeCommand(commandName);
    }

    private void executeCommand(String commandName) {
        this.guildCommands
                .stream()
                .filter(guildCommand -> guildCommand.getName().equals(commandName)
                        || guildCommand.getAliases().contains(commandName))
                .collect(StreamUtils.toSingleton())
                .accept();
    }
}
