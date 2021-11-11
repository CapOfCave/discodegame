package me.kecker.discodegame.bot.domain.commands.arguments.types;

public class StringArgument implements ArgumentType<String> {

    StringArgument() {
        // should only be instantiated internally
    }

    @Override
    public boolean matches(String input) {
        return true;
    }

    @Override
    public String getValue(String input) {
        return input;
    }

    @Override
    public String getLabel() {
        return "String";
    }
}