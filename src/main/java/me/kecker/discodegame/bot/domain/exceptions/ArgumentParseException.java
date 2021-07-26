package me.kecker.discodegame.bot.domain.exceptions;

public class ArgumentParseException extends Exception {

    public ArgumentParseException() {
        super();
    }
    public ArgumentParseException(String message) {
        super(message);
    }



    public static class MissingQuoteParseException extends ArgumentParseException {
    }
    
    public static class IllegalCharacterParseException extends ArgumentParseException {

        public IllegalCharacterParseException(char illegalChar) {
            super(String.format("'%c'", illegalChar));
        }
    }


    public static class IllegallyEmptyString extends ArgumentParseException {
    }
}
