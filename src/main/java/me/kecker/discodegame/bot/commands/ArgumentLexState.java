package me.kecker.discodegame.bot.commands;

/**
 * Internal class that helps an {@link ArgumentLexer} navigate a source String by providing a parsing state.
 */
class ArgumentLexState extends ParseState<Character>{

    public ArgumentLexState(String source) {
        super(source::charAt, source.length());
    }
}
