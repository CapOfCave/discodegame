package me.kecker.discodegame.bot.commands;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ArgumentParser {

    public ParseResult parse(String commandNameAndArgument) {
        throw new NotImplementedException();
    }

    public class ParseResult {
        private Map<String, String> namedArguments;
        private List<String> orderedArguments;

        public Map<String, String> getNamedArguments() {
            return  namedArguments;
        }

        public List<String> getOrderedArguments() {
            return orderedArguments;
        }
    }
}
