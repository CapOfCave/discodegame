package me.kecker.discodegame.bot.domain.exceptions;

import me.kecker.discodegame.bot.domain.commands.arguments.types.ArgumentType;
import me.kecker.discodegame.utils.DcgStringUtils;

import java.util.Collection;
import java.util.stream.Collectors;

public class IllegalCommandArgumentException extends CommandExecutionException {

    public IllegalCommandArgumentException() {
    }

    public IllegalCommandArgumentException(String message) {
        super(message);
    }

    public static class TooManyArgumentsException extends IllegalCommandArgumentException {
    }

    public static class IllegalTypeException extends IllegalCommandArgumentException {
        public IllegalTypeException(ArgumentType<?> type, String stringValue) {
            super(String.format("String '%s' does not match expected type %s.", stringValue, type));
        }
    }

    public static class DuplicateArgumentException extends IllegalCommandArgumentException {
        public DuplicateArgumentException(Collection<String> duplicateKeys) {
            super(getMessage(duplicateKeys));
        }

        private static String getMessage(Collection<String> duplicateKeys) {
            if (duplicateKeys.size() <= 1) {
                return String.format("Duplicate argument: %s.", duplicateKeys.stream().findAny().orElseThrow());
            }
            String args = duplicateKeys.stream().map(DcgStringUtils.addQuotes).collect(Collectors.joining(", "));
            return String.format("Duplicate arguments: %s.", args);

        }
    }
}
