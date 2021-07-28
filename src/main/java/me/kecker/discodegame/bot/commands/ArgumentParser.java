package me.kecker.discodegame.bot.commands;

import lombok.NonNull;
import me.kecker.discodegame.bot.commands.state.ArgumentParseState;
import me.kecker.discodegame.bot.domain.commands.arguments.RawArgument;
import me.kecker.discodegame.bot.domain.exceptions.ArgumentParseException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ArgumentParser {

    @NonNull
    public Result tokenize(@NonNull List<RawArgument> rawArguments) throws ArgumentParseException {
        var parseState = new ArgumentParseState(rawArguments);

        List<String> orderedArguments = parseOrderedArguments(parseState);
        Map<String, String> namedArguments = parseNamedArguments(parseState);

        if (parseState.hasNext()) {
            throw new ArgumentParseException.UnnamedArgumentAfterNamedArgumentException();
        }
        return new Result(orderedArguments, namedArguments);

    }

    @NotNull
    private List<String> parseOrderedArguments(ArgumentParseState parseState) {
        List<String> orderedArguments = new ArrayList<>();
        while (parseState.hasNext() && parseState.getCurrent().key() == null) {
            orderedArguments.add(parseState.getCurrent().value());
            parseState.advance();
        }
        return orderedArguments;
    }

    @NotNull
    private Map<String, String> parseNamedArguments(ArgumentParseState parseState) {
        Map<String, String> namedArguments = new HashMap<>();
        while (parseState.hasNext() && parseState.getCurrent().key() != null) {
            namedArguments.put(parseState.getCurrent().key(), parseState.getCurrent().value());
            parseState.advance();
        }
        return namedArguments;
    }

    record Result(List<String> orderedArguments, Map<String, String> namedArguments) {
    }
}
