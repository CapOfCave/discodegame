package me.kecker.discodegame.bot.domain.commands;

import lombok.NonNull;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CommandExecutionDTO {

    @NonNull
    private final String commandName;

    @NonNull
    private final String argumentsRaw;

    @NonNull
    private final List<String> orderedArguments;

    @NonNull
    private final Map<String, String> namedArguments;

    public CommandExecutionDTO(@NonNull String commandName) {
        this(commandName, "", Collections.emptyList(), Collections.emptyMap());
    }

    public CommandExecutionDTO(
            @NonNull String commandName,
            @NonNull String argumentsRaw,
            @NonNull List<String> orderedArguments,
            @NonNull Map<String, String> namedArguments) {

        this.commandName = commandName;
        this.argumentsRaw = argumentsRaw;
        this.orderedArguments = orderedArguments;
        this.namedArguments = namedArguments;
    }

    @NonNull
    public String getCommandName() {
        return this.commandName;
    }

    @NonNull
    public String getArgumentsRaw() {
        return this.argumentsRaw;
    }

    @NonNull
    public List<String> getOrderedArguments() {
        return this.orderedArguments;
    }

    @NonNull
    public Map<String, String> getNamedArguments() {
        return this.namedArguments;
    }
}
