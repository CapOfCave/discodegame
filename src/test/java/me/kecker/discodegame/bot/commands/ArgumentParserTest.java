package me.kecker.discodegame.bot.commands;

import me.kecker.discodegame.bot.domain.commands.arguments.RawArgument;
import me.kecker.discodegame.bot.domain.exceptions.ArgumentParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArgumentParserTest {

    private ArgumentParser objectUnderTest;
    
    @BeforeEach
    void setUp() {
        this.objectUnderTest = new ArgumentParser();
    }

    @Test
    void tokenizeEmptyInput() throws ArgumentParseException {
        ArgumentParser.Result parseResult = this.objectUnderTest.tokenize(Collections.emptyList());
        assertThat(parseResult.namedArguments()).isEmpty();
        assertThat(parseResult.orderedArguments()).isEmpty();
    }

    @Test
    void tokenizeNamedArgument() throws ArgumentParseException {
        ArgumentParser.Result parseResult = this.objectUnderTest.tokenize(List.of(new RawArgument("someKey", "someValue")));
        assertThat(parseResult.namedArguments())
                .hasSize(1)
                .extractingByKey("someKey")
                .isEqualTo("someValue");
        assertThat(parseResult.orderedArguments()).isEmpty();
    }

    @Test
    void tokenizeOrderedArgument() throws ArgumentParseException {
        ArgumentParser.Result parseResult = this.objectUnderTest.tokenize(List.of(new RawArgument(null, "someValue")));
        assertThat(parseResult.namedArguments()).isEmpty();
        assertThat(parseResult.orderedArguments())
                .hasSize(1)
                .first()
                .isEqualTo("someValue");
    }

    @Test
    void tokenizeOrderedThenNamedArguments() throws ArgumentParseException {
        List<RawArgument> input = List.of(
                new RawArgument(null, "someValue1"),
                new RawArgument(null, "someValue2"),
                new RawArgument("someKey1", "someOtherValue1"),
                new RawArgument("someKey2", "someOtherValue2"));
        ArgumentParser.Result parseResult = this.objectUnderTest.tokenize(input);
        assertThat(parseResult.orderedArguments())
                .containsExactly("someValue1", "someValue2");
        assertThat(parseResult.namedArguments())
                .hasSize(2)
                .containsEntry("someKey1", "someOtherValue1")
                .containsEntry("someKey2", "someOtherValue2");
    }

    @Test
    void tokenizeNamedThenOrderedArguments() {
        List<RawArgument> input = List.of(
                new RawArgument("someKey1", "someOtherValue1"),
                new RawArgument(null, "someValue1"));
        assertThrows(ArgumentParseException.UnnamedArgumentAfterNamedArgumentException.class, () -> this.objectUnderTest.tokenize(input));
    }
}