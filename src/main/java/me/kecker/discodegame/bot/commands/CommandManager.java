package me.kecker.discodegame.bot.commands;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import me.kecker.discodegame.bot.domain.annotations.RegisteredGuildCommand;
import me.kecker.discodegame.bot.domain.commands.BotCommandMeta;
import me.kecker.discodegame.bot.domain.exceptions.ArgumentParseException;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CommandManager {

    @NonNull
    private final CommandLexer commandLexer;

    @NonNull
    private final Map<String, BotCommandMeta> guildCommands = new HashMap<>();

    public CommandManager(@NonNull CommandLexer commandLexer, @NonNull Collection<? extends BotCommandMeta> allCommands) {
        this.commandLexer = commandLexer;
        allCommands
                .stream()
                .filter(botCommand -> botCommand.getClass().isAnnotationPresent(RegisteredGuildCommand.class))
                .forEach(this::addToGuildCommands);
        log.info("{} found {} commands, {} of which are guild commands.", CommandManager.class.getSimpleName(), allCommands.size(), this.guildCommands.size());
    }

    public void handleCommand(String commandRaw) throws ArgumentParseException {
        this.commandLexer.tokenizeMessage(commandRaw);

        //TODO map to CommandExecution
        throw new NotImplementedException();
    }

    private void addToGuildCommands(BotCommandMeta botCommand) {
        this.guildCommands.putIfAbsent(botCommand.getName(), botCommand);
        botCommand.getAliases().forEach(alias -> this.guildCommands.putIfAbsent(alias, botCommand));
    }




}
