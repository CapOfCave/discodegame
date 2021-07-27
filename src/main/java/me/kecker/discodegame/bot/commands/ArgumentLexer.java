package me.kecker.discodegame.bot.commands;

import lombok.NonNull;
import me.kecker.discodegame.bot.domain.commands.arguments.RawArgument;
import me.kecker.discodegame.bot.domain.exceptions.ArgumentSyntaxException;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;

@Component
public class ArgumentLexer {
    private static final Set<Character> ESCAPABLE_CHARS_IN_QUOTED_STRING = Set.of('\\', '"');

    @NonNull
    public List<RawArgument> tokenize(@NonNull String argumentsRaw) throws ArgumentSyntaxException {
        var lexerState = new ArgumentLexState(argumentsRaw);
        List<RawArgument> arguments = new ArrayList<>();
        while (lexerState.hasNext()) {
            arguments.add(tokenizeArgument(lexerState));
            passWhiteSpace(lexerState);
        }
        return arguments;
    }

    @NonNull
    private RawArgument tokenizeArgument(ArgumentLexState parseState) throws ArgumentSyntaxException {
        String firstWord = tokenizeWord(parseState, false);
        String argumentName = null;
        String argumentValue;
        if (parseState.hasNext() && isSeparator(parseState.getCurrent())) {
            argumentName = firstWord;
            parseState.advance();
            argumentValue = tokenizeWord(parseState, true);
        } else if (!parseState.hasNext() || Character.isWhitespace(parseState.getCurrent())) {
            argumentValue = firstWord;
        } else {
            throw new ArgumentSyntaxException.IllegalCharacterSyntaxException(parseState.getCurrent());
        }
        return new RawArgument(argumentName, argumentValue);
    }

    @NotNull
    private String tokenizeWord(@NotNull ArgumentLexState parseState, boolean allowEmpty) throws ArgumentSyntaxException {
        if (isQuote(parseState.getCurrent())) {
            return tokenizeQuotedWord(parseState, allowEmpty);
        }
        return tokenizeUnquotedWord(parseState, c -> Character.isWhitespace(c) || isSeparator(c), Collections.emptySet(), allowEmpty);
    }


    @NotNull
    private String tokenizeQuotedWord(@NotNull ArgumentLexState parseState, boolean allowEmpty) throws ArgumentSyntaxException {
        if (!isQuote(parseState.getCurrent())) {
            throw new ArgumentSyntaxException.MissingQuoteSyntaxException();
        }
        parseState.advance();
        String unquotedWord = tokenizeUnquotedWord(parseState, c -> isQuote(c), ESCAPABLE_CHARS_IN_QUOTED_STRING, allowEmpty);
        if (!isQuote(parseState.getCurrent())) {
            throw new ArgumentSyntaxException.MissingQuoteSyntaxException();
        }
        parseState.advance();
        return unquotedWord;
    }

    @NotNull
    private String tokenizeUnquotedWord(@NotNull ArgumentLexState parseState, Predicate<Character> wordEndCondition, Set<Character> escapableChars, boolean allowEmpty) throws ArgumentSyntaxException {
        var identifierBuilder = new StringBuilder();
        while (parseState.hasNext() && !wordEndCondition.test(parseState.getCurrent())) {
            if (parseState.getCurrent() == '\\') {
                parseState.advance();
                handleBackslash(parseState, escapableChars, identifierBuilder);
                continue; // check condition again
            }
            if (isQuote(parseState.getCurrent())) {
                throw new ArgumentSyntaxException.IllegalCharacterSyntaxException(parseState.getCurrent());
            }
            identifierBuilder.append(parseState.getCurrent());
            parseState.advance();
        }
        if (!allowEmpty && identifierBuilder.isEmpty()) {
            throw new ArgumentSyntaxException.IllegallyEmptyString();
        }
        return identifierBuilder.toString();
    }

    private void handleBackslash(@NotNull ArgumentLexState parseState, Set<Character> escapableChars, StringBuilder identifierBuilder) {
        if (escapableChars.contains(parseState.getCurrent())) {
            identifierBuilder.append(parseState.getCurrent());
            parseState.advance();
        } else {
            identifierBuilder.append('\\');
        }
    }

    private boolean isQuote(char test) {
        return test == '"';
    }

    private boolean isSeparator(char test) {
        return test == '=';
    }

    private void passWhiteSpace(@NonNull ArgumentLexState lexerState) {
        while (lexerState.hasNext() && Character.isWhitespace(lexerState.getCurrent())) {
            lexerState.advance();
        }
    }
}
