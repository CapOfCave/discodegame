package me.kecker.discodegame.bot.commands;

import net.dv8tion.jda.api.entities.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandParserTest {

    private static final String PREFIX = "prefix";
    private static final String COMMAND = "command";
    private static final String ARGUMENT = "argument";
    private static final String WRONG_PREFIX = "wrongPrefix";
    private static final String WRONG_CASE_PREFIX = "PrEfIx";

    private static final String COMMAND_WITH_PREFIX = PREFIX + COMMAND;
    private static final String COMMAND_WITH_PREFIX_AND_SPACE = PREFIX + " " + COMMAND;
    private static final String COMMAND_WITH_PREFIX_WITH_ARGUMENTS = COMMAND_WITH_PREFIX + " " + ARGUMENT;
    private static final String COMMAND_WITH_PREFIX_AND_SPACE_WITH_ARGUMENTS = COMMAND_WITH_PREFIX_AND_SPACE + " " + ARGUMENT;

    @Mock
    private Message messageMock;

    private CommandParser objectUnderTest;

    @BeforeEach
    public void setUp() {
        this.objectUnderTest = new CommandParser();
        ReflectionTestUtils.setField(this.objectUnderTest, "prefix", PREFIX);
        ReflectionTestUtils.setField(this.objectUnderTest, "ignoreWhiteSpaceAfterPrefix", false);
        ReflectionTestUtils.setField(this.objectUnderTest, "ignorePrefixCase", false);

    }

    @ParameterizedTest
    @ValueSource(strings = {COMMAND_WITH_PREFIX, COMMAND_WITH_PREFIX_AND_SPACE, COMMAND_WITH_PREFIX_WITH_ARGUMENTS, COMMAND_WITH_PREFIX_AND_SPACE_WITH_ARGUMENTS})
    void testIsCommandMessageWithPrefix(String content) {
        mockMessageContent(content);
        assertThat(this.objectUnderTest.isCommand(this.messageMock)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {WRONG_PREFIX + COMMAND, WRONG_PREFIX + PREFIX + COMMAND})
    void testIsCommandMessageWithWrongPrefix(String content) {
        mockMessageContent(content);
        assertThat(this.objectUnderTest.isCommand(this.messageMock)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {COMMAND_WITH_PREFIX, COMMAND_WITH_PREFIX_AND_SPACE, COMMAND_WITH_PREFIX_WITH_ARGUMENTS, COMMAND_WITH_PREFIX_AND_SPACE_WITH_ARGUMENTS})
    void testIsCommandContentWithPrefix(String content) {
        ReflectionTestUtils.setField(this.objectUnderTest, "ignorePrefixCase", true);
        assertThat(this.objectUnderTest.isCommand(content)).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {WRONG_PREFIX + COMMAND, WRONG_PREFIX + PREFIX + COMMAND, WRONG_CASE_PREFIX + COMMAND})
    void testIsCommandContentWithWrongPrefix(String content) {
        assertThat(this.objectUnderTest.isCommand(content)).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {WRONG_CASE_PREFIX + COMMAND, WRONG_CASE_PREFIX + PREFIX + COMMAND})
    void testIsCommandMessageWithPrefixIgnoreCase(String content) {
        ReflectionTestUtils.setField(this.objectUnderTest, "ignorePrefixCase", true);
        mockMessageContent(content);
        assertThat(this.objectUnderTest.isCommand(this.messageMock)).isTrue();
    }


    @ParameterizedTest
    @ValueSource(strings = {COMMAND_WITH_PREFIX, COMMAND_WITH_PREFIX_WITH_ARGUMENTS})
    void testGetCommandName(String content) {
        mockMessageContent(content);
        assertThat(this.objectUnderTest.getCommandName(this.messageMock)).isEqualTo(COMMAND);
    }

    @ParameterizedTest
    @ValueSource(strings = {COMMAND_WITH_PREFIX, COMMAND_WITH_PREFIX_AND_SPACE, COMMAND_WITH_PREFIX_WITH_ARGUMENTS, COMMAND_WITH_PREFIX_AND_SPACE_WITH_ARGUMENTS})
    void testGetCommandNameIgnoreSpace(String content) {
        ReflectionTestUtils.setField(this.objectUnderTest, "ignoreWhiteSpaceAfterPrefix", true);
        mockMessageContent(content);
        assertThat(this.objectUnderTest.getCommandName(this.messageMock)).isEqualTo(COMMAND);
    }

    @ParameterizedTest
    @ValueSource(strings = {COMMAND_WITH_PREFIX, COMMAND_WITH_PREFIX_WITH_ARGUMENTS})
    void testGetCommandNameIgnoreCase(String content) {
        mockMessageContent(content);
        assertThat(this.objectUnderTest.getCommandName(this.messageMock)).isEqualTo(COMMAND);
    }

    private void mockMessageContent(String content) {
        when(this.messageMock.getContentRaw()).thenReturn(content);
    }
}