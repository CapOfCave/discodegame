package me.kecker.discodegame.bot.commands;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import me.kecker.discodegame.bot.domain.annotations.RegisteredGuildCommand;
import me.kecker.discodegame.bot.domain.commands.BotCommandMeta;
import me.kecker.discodegame.bot.domain.commands.CommandExecutionDTO;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CommandManager {

    @NonNull
    private final Map<String, BotCommandMeta> guildCommands = new HashMap<>();

    public CommandManager(@NonNull Collection<? extends BotCommandMeta> allCommands) {
        allCommands
                .stream()
                .filter(botCommand -> botCommand.getClass().isAnnotationPresent(RegisteredGuildCommand.class))
                .forEach(this::addToGuildCommands);
        log.info("{} found {} commands, {} of which are guild commands.", CommandManager.class.getSimpleName(), allCommands.size(), this.guildCommands.size());
    }

    public void tryExecute(CommandExecutionDTO commandExecutionDTO) {
        //TODO implement
        throw new NotImplementedException();
    }

    private void addToGuildCommands(BotCommandMeta botCommand) {
        this.guildCommands.putIfAbsent(botCommand.getName(), botCommand);
        botCommand.getAliases().forEach(alias -> this.guildCommands.putIfAbsent(alias, botCommand));
    }




}
