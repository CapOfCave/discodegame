package me.kecker.discodegame.bot.commands;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import me.kecker.discodegame.bot.domain.annotations.RegisteredEventListener;
import me.kecker.discodegame.bot.domain.annotations.RegisteredGuildCommand;
import me.kecker.discodegame.bot.domain.commands.BotCommandMeta;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RegisteredEventListener
@Component
public class CommandEventListener extends ListenerAdapter {

    @NonNull
    private final CommandParser commandParser;

    @NonNull
    private final Map<String, BotCommandMeta> guildCommands = new HashMap<>();

    @Autowired
    public CommandEventListener(@NonNull CommandParser commandParser, @NonNull Collection<? extends BotCommandMeta> allCommands) {
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
        if (this.guildCommands.containsKey(commandName)) {
            this.guildCommands.get(commandName).accept();
        }
    }

    private void addToGuildCommands(BotCommandMeta botCommand) {
        this.guildCommands.putIfAbsent(botCommand.getName(), botCommand);
        botCommand.getAliases().forEach(alias -> this.guildCommands.putIfAbsent(alias, botCommand));
    }
}
