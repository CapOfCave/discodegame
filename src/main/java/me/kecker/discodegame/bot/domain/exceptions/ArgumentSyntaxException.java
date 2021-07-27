package me.kecker.discodegame.bot.domain.exceptions;

public class ArgumentSyntaxException extends Exception {

    public ArgumentSyntaxException() {
        super();
    }

    public ArgumentSyntaxException(String message) {
        super(message);
    }

    public static class MissingQuoteSyntaxException extends ArgumentSyntaxException {
    }

    public static class IllegalCharacterSyntaxException extends ArgumentSyntaxException {
        public IllegalCharacterSyntaxException(char illegalChar) {
            super(String.format("'%c'", illegalChar));
        }
    }

    public static class IllegallyEmptyString extends ArgumentSyntaxException {
    }
}
