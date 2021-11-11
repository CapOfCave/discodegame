package me.kecker.discodegame.bot.commands;

import me.kecker.discodegame.bot.domain.commands.arguments.RawArgument;
import me.kecker.discodegame.bot.domain.exceptions.ArgumentParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandParserTest {

    private CommandParser objectUnderTest;

    @Mock
    private ArgumentParser argumentParser;

    public static final String COMMAND = "someCommand";
    private static final List<RawArgument> RAW_ARGUMENTS = List.of(new RawArgument(null, "someValue1"), new RawArgument("someKey", "someValue2"));
    private static final CommandLexer.Result lexResult = new CommandLexer.Result(COMMAND, RAW_ARGUMENTS);

    private static final List<String> ORDERED_ARGUMENTS = List.of("someValue1");
    private static final Map<String, String> NAMED_ARGUMENTS = Map.of("someKey", "someValue2");
    private static final ArgumentParser.Result argumentParseResult = new ArgumentParser.Result(ORDERED_ARGUMENTS, NAMED_ARGUMENTS);

    @BeforeEach
    void setUp() {
        this.objectUnderTest = new CommandParser(argumentParser);
    }

    @Test
    void parse() throws ArgumentParseException {
        when(this.argumentParser.tokenize(RAW_ARGUMENTS)).thenReturn(argumentParseResult);
        CommandParser.Result result = this.objectUnderTest.parse(lexResult);
        assertThat(result.command()).isEqualTo(COMMAND);
        assertThat(result.orderedArguments()).isEqualTo(ORDERED_ARGUMENTS);
        assertThat(result.namedArguments()).isEqualTo(NAMED_ARGUMENTS);
    }
}