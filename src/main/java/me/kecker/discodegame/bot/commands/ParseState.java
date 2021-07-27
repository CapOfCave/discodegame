package me.kecker.discodegame.bot.commands;

import java.util.function.IntFunction;

/**
 * Internal class that helps any lexer or parser navigate a source by providing a lexing/parsing state.
 */
class ParseState<T> {
    private final IntFunction<T> valueSupplier;
    private final int length;
    private int index;
    private T current;

    public ParseState(IntFunction<T> valueSupplier, int length) {
        this.valueSupplier = valueSupplier;
        this.length = length;
        this.index = 0;
        updateCurrentChar();
    }

    public void advance() {
        this.index++;
        updateCurrentChar();
    }

    public boolean hasNext() {
        return this.index < this.length;
    }

    public T getCurrent() {
        if (!hasNext()) {
            throw new IllegalStateException("Reached end of input String");
        }
        return this.current;
    }

    private void updateCurrentChar() {
        if (!hasNext()) {
            return;
        }
        this.current = this.valueSupplier.apply(this.index);
    }
}
