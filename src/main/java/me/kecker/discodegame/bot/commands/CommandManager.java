package me.kecker.discodegame.bot.commands;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import me.kecker.discodegame.bot.domain.annotations.RegisteredGuildCommand;
import me.kecker.discodegame.bot.domain.commands.BotCommandMeta;
import me.kecker.discodegame.bot.domain.commands.CommandExecution;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgument;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgumentMeta;
import me.kecker.discodegame.bot.domain.commands.arguments.types.ArgumentType;
import me.kecker.discodegame.bot.domain.exceptions.ArgumentParseException;
import me.kecker.discodegame.bot.domain.exceptions.ArgumentSyntaxException;
import me.kecker.discodegame.bot.domain.exceptions.IllegalCommandArgumentException;
import me.kecker.discodegame.utils.DcgMapUtils;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CommandManager {

    @NonNull
    private final CommandLexer commandLexer;

    @NonNull
    private final CommandParser commandParser;

    @NonNull
    private final Map<String, BotCommandMeta> guildCommands = new HashMap<>();

    public CommandManager(
            @NonNull CommandLexer commandLexer,
            @NonNull CommandParser commandParser,
            @NonNull Collection<? extends BotCommandMeta> allCommands) {

        this.commandLexer = commandLexer;
        this.commandParser = commandParser;
        allCommands
                .stream()
                .filter(botCommand -> botCommand.getClass().isAnnotationPresent(RegisteredGuildCommand.class))
                .forEach(this::addToGuildCommands);
        log.info("{} found {} commands, {} of which are guild commands.", CommandManager.class.getSimpleName(), allCommands.size(), this.guildCommands.size());
    }

    public void handleCommand(String commandRaw) throws ArgumentSyntaxException, ArgumentParseException, IllegalCommandArgumentException {
        CommandLexer.Result lexResult = this.commandLexer.tokenizeMessage(commandRaw);
        CommandParser.Result parseResult = this.commandParser.parse(lexResult);
        BotCommandMeta commandMeta = this.guildCommands.get(parseResult.command());
        Map<String, BotCommandArgument<?>> botCommandArgumentsByName = interpretParseResult(commandMeta, parseResult.orderedArguments(), parseResult.namedArguments());
        commandMeta.accept(botCommandArgumentsByName);
    }

    private Map<String, BotCommandArgument<?>> interpretParseResult(BotCommandMeta commandMeta, List<String> orderedArguments, Map<String, String> namedArguments) throws IllegalCommandArgumentException {
        Map<String, BotCommandArgument<?>> argumentsFromOrder = getBotCommandArgumentsFromOrder(commandMeta, orderedArguments);
        Map<String, BotCommandArgument<?>> argumentsFromName = getBotCommandArgumentsFromName(commandMeta, namedArguments);
        try {
            return DcgMapUtils.union(argumentsFromOrder, argumentsFromName);
        } catch (DcgMapUtils.DuplicateKeyException e) {
            throw new IllegalCommandArgumentException.DuplicateArgumentException(
                    e.getDuplicateKeys().stream().map(Object::toString).collect(Collectors.toSet()));
        }
    }

    private Map<String, BotCommandArgument<?>> getBotCommandArgumentsFromOrder(BotCommandMeta commandMeta, List<String> orderedArguments) throws IllegalCommandArgumentException.TooManyArgumentsException, IllegalCommandArgumentException.IllegalTypeException {
        Map<String, BotCommandArgument<?>> result = new HashMap<>();
        for (int i = 0; i < orderedArguments.size(); i++) {
            if (i >= commandMeta.getArgumentCount()) {
                throw new IllegalCommandArgumentException.TooManyArgumentsException();
            }
            BotCommandArgumentMeta<?> argumentMeta = commandMeta.getArgumentMeta(i);
            String stringValue = orderedArguments.get(i);
            result.put(argumentMeta.getName(), mapToBotCommandArgument(argumentMeta, stringValue));
        }
        return result;
    }

    private Map<String, BotCommandArgument<?>> getBotCommandArgumentsFromName(BotCommandMeta commandMeta, Map<String, String> namedArguments) throws IllegalCommandArgumentException.IllegalTypeException {
        Map<String, BotCommandArgument<?>> result = new HashMap<>();
        for (var entry: namedArguments.entrySet()){
            BotCommandArgumentMeta<?> argumentMeta = commandMeta.getArgumentMeta(entry.getKey());
            // note that argumentMeta.getName may not be equal to entry.getKey() in case of aliases
            result.put(argumentMeta.getName(), mapToBotCommandArgument(argumentMeta, entry.getValue()));
        }
        return result;
    }

    private <T> BotCommandArgument<T> mapToBotCommandArgument(BotCommandArgumentMeta<T> argumentMeta, String stringValue) throws IllegalCommandArgumentException.IllegalTypeException {
        ArgumentType<T> type = argumentMeta.getType();
        if (!type.matches(stringValue)){
            throw new IllegalCommandArgumentException.IllegalTypeException(type, stringValue);
        }
        return new BotCommandArgument<>(argumentMeta, type.getValue(stringValue));
    }

    private void addToGuildCommands(BotCommandMeta botCommand) {
        this.guildCommands.putIfAbsent(botCommand.getName(), botCommand);
        botCommand.getAliases().forEach(alias -> this.guildCommands.putIfAbsent(alias, botCommand));
    }
}
