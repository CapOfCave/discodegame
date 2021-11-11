package me.kecker.discodegame.bot.commands;

import lombok.NonNull;
import me.kecker.discodegame.bot.domain.exceptions.ArgumentParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class CommandParser {

    @NonNull
    private final ArgumentParser argumentParser;

    @Autowired
    public CommandParser(@NonNull ArgumentParser argumentParser) {
        this.argumentParser = argumentParser;
    }


    public Result parse(CommandLexer.Result lexResult) throws ArgumentParseException {
        ArgumentParser.Result argumentTokens = this.argumentParser.tokenize(lexResult.arguments());
        return new Result(lexResult.command(), argumentTokens.orderedArguments(), argumentTokens.namedArguments());
    }

    record Result(String command, List<String> orderedArguments, Map<String, String> namedArguments){

    }
}
