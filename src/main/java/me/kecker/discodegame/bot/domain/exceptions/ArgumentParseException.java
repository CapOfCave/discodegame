package me.kecker.discodegame.bot.domain.exceptions;

public class ArgumentParseException extends CommandExecutionException {

    public static class UnnamedArgumentAfterNamedArgumentException extends ArgumentParseException {

    }
}
