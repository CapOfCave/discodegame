package me.kecker.discodegame.bot.commands;

import me.kecker.discodegame.bot.domain.commands.arguments.RawArgument;
import me.kecker.discodegame.bot.domain.exceptions.ArgumentSyntaxException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ArgumentLexerTest {

    private ArgumentLexer objectUnderTest;

    @BeforeEach
    void setUp() {
        this.objectUnderTest = new ArgumentLexer();
    }

    @Test
    void tokenizeEmptyString() throws ArgumentSyntaxException {
        String rawString = "";
        List<RawArgument> result = this.objectUnderTest.tokenize(rawString);
        assertThat(result)
                .isNotNull()
                .isEmpty();
    }

    @ParameterizedTest
    @ValueSource(strings = {"someValue", "\"someValue\""})
    void tokenizeOneArgumentWithoutName(String rawString) throws ArgumentSyntaxException {
        List<RawArgument> result = this.objectUnderTest.tokenize(rawString);
        assertThat(result)
                .isNotNull()
                .hasSize(1)
                .first()
                .extracting("key", "value")
                .containsExactly(null, "someValue");
    }

    @ParameterizedTest
    @ValueSource(strings = {"someKey=someValue", "\"someKey\"=\"someValue\"", "\"someKey\"=someValue", "someKey=\"someValue\""})
    void tokenizeOneArgumentWithName(String rawString) throws ArgumentSyntaxException {
        List<RawArgument> result = this.objectUnderTest.tokenize(rawString);
        assertThat(result)
                .isNotNull()
                .hasSize(1)
                .first()
                .extracting("key", "value")
                .containsExactly("someKey", "someValue");
    }

    @Test
    void tokenizeMultipleArgumentsWithoutName() throws ArgumentSyntaxException {
        String rawString = "someValue someOtherValue";
        List<RawArgument> result = this.objectUnderTest.tokenize(rawString);
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .containsExactlyElementsOf(List.of(new RawArgument(null, "someValue"), new RawArgument(null, "someOtherValue")));
    }

    @Test
    void tokenizeMultipleArgumentsWithName() throws ArgumentSyntaxException {
        String rawString = "someKey=someValue someOtherKey=someOtherValue";
        List<RawArgument> result = this.objectUnderTest.tokenize(rawString);
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .containsExactlyElementsOf(List.of(new RawArgument("someKey", "someValue"), new RawArgument("someOtherKey", "someOtherValue")));
    }

    @Test
    void tokenizeArgumentsWithKeyAndWithout() throws ArgumentSyntaxException {
        String rawString = "someValue someOtherKey=someOtherValue";
        List<RawArgument> result = this.objectUnderTest.tokenize(rawString);
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .containsExactlyElementsOf(List.of(new RawArgument(null, "someValue"), new RawArgument("someOtherKey", "someOtherValue")));
    }

    @Test
    void tokenizeArgumentsWithKeyAndWithoutReverseOrder() throws ArgumentSyntaxException {
        String rawString = "someOtherKey=someOtherValue someValue";
        List<RawArgument> result = this.objectUnderTest.tokenize(rawString);
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .containsExactlyElementsOf(List.of(new RawArgument("someOtherKey", "someOtherValue"), new RawArgument(null, "someValue")));
    }

    @Test
    void tokenizeManyArguments() throws ArgumentSyntaxException {
        String rawString = "1 2 3 a=4 b=5 c=6";
        List<RawArgument> result = this.objectUnderTest.tokenize(rawString);
        assertThat(result)
                .isNotNull()
                .hasSize(6)
                .containsExactlyElementsOf(List.of(
                        new RawArgument(null, "1"),
                        new RawArgument(null, "2"),
                        new RawArgument(null, "3"),
                        new RawArgument("a", "4"),
                        new RawArgument("b", "5"),
                        new RawArgument("c", "6")));
    }

    @Test
    void tokenizeIgnoreSpaceInQuotes() throws ArgumentSyntaxException {
        String rawString = "\"key with space\"=\"value with space\"";
        List<RawArgument> result = this.objectUnderTest.tokenize(rawString);
        assertThat(result)
                .isNotNull()
                .hasSize(1)
                .first()
                .extracting("key", "value")
                .containsExactly("key with space", "value with space");
    }

    @Test
    void tokenizeIgnoreEqualsInQuotes() throws ArgumentSyntaxException {
        String rawString = "\"some=key\"=\"some=value\"";
        List<RawArgument> result = this.objectUnderTest.tokenize(rawString);
        assertThat(result)
                .isNotNull()
                .hasSize(1)
                .first()
                .extracting("key", "value")
                .containsExactly("some=key", "some=value");
    }

    @Test
    void tokenizeIgnoreEscapedQuotes() throws ArgumentSyntaxException {
        String rawString = "\"some\\\"key\"=\"some\\\"value\"";
        List<RawArgument> result = this.objectUnderTest.tokenize(rawString);
        assertThat(result)
                .isNotNull()
                .hasSize(1)
                .first()
                .extracting("key", "value")
                .containsExactly("some\"key", "some\"value");
    }

    @Test
    void tokenizeConsiderBackslashWithoutQuotes() throws ArgumentSyntaxException {
        String rawString = "\"some\\key\"=\"some\\value\"";
        List<RawArgument> result = this.objectUnderTest.tokenize(rawString);
        assertThat(result)
                .isNotNull()
                .hasSize(1)
                .first()
                .extracting("key", "value")
                .containsExactly("some\\key", "some\\value");
    }

    @Test
    void tokenizeMultipleWhitespacesBetweenArguments() throws ArgumentSyntaxException {
        String rawString = "1  2 \n3";
        List<RawArgument> result = this.objectUnderTest.tokenize(rawString);
        assertThat(result)
                .isNotNull()
                .containsExactlyElementsOf(List.of(
                        new RawArgument(null, "1"),
                        new RawArgument(null, "2"),
                        new RawArgument(null, "3")));
    }

    @Test
    void tokenizeWhitespacesBeforeEquals() {
        String rawString = "1 = 5\n2 = 10 2";
        assertThrows(ArgumentSyntaxException.IllegallyEmptyString.class, () -> this.objectUnderTest.tokenize(rawString));
    }
}