package me.kecker.discodegame.bot.domain.exceptions;

public abstract class CommandInterpreterException extends CommandExecutionException {
    public CommandInterpreterException() {
        super();
    }

    public CommandInterpreterException(String message) {
        super(message);
    }

    public static class UnknownCommandException extends CommandInterpreterException {
        final String commandName;
        public UnknownCommandException(String commandName){
            super(String.format("Could not find command with name %s.", commandName));
            this.commandName = commandName;
        }
        public String getCommandName() {
            return this.commandName;
        }
    }
}
