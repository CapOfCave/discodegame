package me.kecker.discodegame.bot.domain.commands.arguments.types;

import java.util.regex.Pattern;

public class IntegerArgument implements ArgumentType<Integer> {

    private static Pattern matchPattern = Pattern.compile("\\d+");

    IntegerArgument() {
        // should only be instantiated internally
    }

    @Override
    public boolean matches(String input) {
        return this.matchPattern.matcher(input).matches();
    }

    @Override
    public Integer getValue(String input) {
        return Integer.parseInt(input);
    }

    @Override
    public String getLabel() {
        return "Integer";
    }
}