package me.kecker.discodegame.bot.domain.commands.arguments.types;

public interface ArgumentType<T> {

    /**
     * Check if this argument matches the given type.
     *
     * @param input The argument, as raw text.
     * @return True if and only if this ArgumentType can handle the given input.
     */
    boolean matches(String input);

    /**
     * Get the value of this parameter with the correct type.
     * It is guaranteed that this is only called if {@link #matches(String)} returned true.
     *
     * @param input The argument, as raw text.
     * @return The argument as the correct type.
     */
    T getValue(String input);
}
