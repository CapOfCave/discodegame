package me.kecker.discodegame.bot.commands.state;

import me.kecker.discodegame.bot.commands.ArgumentLexer;

/**
 * Internal class that helps an {@link ArgumentLexer} navigate a source String by providing a parsing state.
 */
public class ArgumentLexState extends ParseState<Character>{

    public ArgumentLexState(String source) {
        super(source::charAt, source.length());
    }
}
