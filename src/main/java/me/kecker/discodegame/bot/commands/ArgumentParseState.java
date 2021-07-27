package me.kecker.discodegame.bot.commands;

import me.kecker.discodegame.bot.domain.commands.arguments.RawArgument;

import java.util.List;

/**
 * Internal class that helps an {@link ArgumentLexer} navigate a source String by providing a parsing state.
 */
class ArgumentParseState extends ParseState<RawArgument> {

    public ArgumentParseState(List<RawArgument> source) {
       super(source::get, source.size());
    }
}
