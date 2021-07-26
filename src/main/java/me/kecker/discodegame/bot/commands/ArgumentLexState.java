package me.kecker.discodegame.bot.commands;

/**
 * Internal class that helps an {@link ArgumentLexer} navigate a source String by providing a parsing state.
 */
class ArgumentLexState {
    private final String source;
    private int index;
    private char current;

    public ArgumentLexState(String source) {
        this.source = source;
        this.index = 0;
        updateCurrentChar();
    }

    public void advance() {
        this.index++;
        updateCurrentChar();
    }

    public boolean hasNext() {
        return this.index < this.source.length();
    }

    public char getCurrent() {
        if (!hasNext()){
            throw new IllegalStateException("Reached end of input String");
        }
        return this.current;
    }

    private void updateCurrentChar() {
        if (!hasNext()) {
            return;
        }
        this.current = this.source.charAt(this.index);
    }
}
