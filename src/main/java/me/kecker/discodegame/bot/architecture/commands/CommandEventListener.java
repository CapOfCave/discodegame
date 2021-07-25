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
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RegisteredEventListener
@Component
public class CommandEventListener extends ListenerAdapter {

    @NonNull
    private final CommandParser commandParser;

    @NonNull
    private final Map<String, BotCommand> guildCommands = new HashMap<>();

    @Autowired
    public CommandEventListener(@NonNull CommandParser commandParser, Collection<? extends BotCommand> allCommands) {
        this.commandParser = commandParser;
        allCommands
                .stream()
                .filter(botCommand -> botCommand.getClass().isAnnotationPresent(RegisteredGuildCommand.class))
                .forEach(this::addToGuildCommands);
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

    private void addToGuildCommands(BotCommand botCommand) {
        this.guildCommands.putIfAbsent(botCommand.getName(), botCommand);
        botCommand.getAliases().forEach(alias -> this.guildCommands.putIfAbsent(alias, botCommand));
    }

    private void executeCommand(String commandName) {
        this.guildCommands.get(commandName).accept();
    }
}
