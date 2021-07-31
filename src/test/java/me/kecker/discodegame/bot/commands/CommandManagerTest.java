package me.kecker.discodegame.bot.commands;

import me.kecker.discodegame.bot.domain.commands.BotCommandMeta;
import me.kecker.discodegame.bot.domain.commands.CommandExecutionRequest;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgument;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgumentMeta;
import me.kecker.discodegame.bot.domain.commands.arguments.RawArgument;
import me.kecker.discodegame.bot.domain.commands.arguments.types.ArgumentTypes;
import me.kecker.discodegame.bot.domain.exceptions.CommandExecutionException;
import me.kecker.discodegame.bot.domain.exceptions.CommandInterpreterException;
import me.kecker.discodegame.test.annotationclasses.AnnotationTestClasses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandManagerTest {

    private CommandManager objectUnderTest;

    @Mock
    private CommandLexer commandLexerMock;

    @Mock
    private CommandParser commandParserMock;

    @Mock
    private AnnotationTestClasses.RegisteredGuildCommandBotCommand guildCommandMock;

    @Mock
    private BotCommandMeta nonGuildCommandMock;

    @Captor
    private ArgumentCaptor<Map<String, BotCommandArgument<?>>> botCommandArgumentCaptor;

    private static final String COMMAND_NAME = "commandName";
    private static final String UNKNOWN_COMMAND_NAME = "unknownCommandName";
    private static final String COMMAND_ALIAS = "alias";
    private static final String ARGUMENT_1_KEY = "kk";
    private static final String ARGUMENT_1_VALUE = "argument1";
    private static final String ARGUMENT_2_KEY = "k";
    private static final String ARGUMENT_2_VALUE = "argument2";

    private static final RawArgument ARGUMENT1 = new RawArgument(null, ARGUMENT_1_VALUE);
    private static final RawArgument ARGUMENT2 = new RawArgument(ARGUMENT_2_KEY, ARGUMENT_2_VALUE);

    private static final String COMMAND = "command";
    private static final List<RawArgument> RAW_ARGUMENTS = List.of(ARGUMENT1, ARGUMENT2);
    private static final CommandLexer.Result LEX_RESULT = new CommandLexer.Result(COMMAND_NAME, RAW_ARGUMENTS);

    private static final List<String> ORDERED_ARGUMENTS = List.of(ARGUMENT_1_VALUE);
    private static final Map<String, String> NAMED_ARGUMENTS = Map.of(ARGUMENT_2_KEY, ARGUMENT_2_VALUE);
    private static final CommandParser.Result PARSE_RESULT = new CommandParser.Result(COMMAND_NAME, ORDERED_ARGUMENTS, NAMED_ARGUMENTS);
    private static final CommandParser.Result PARSE_RESULT_ALIAS = new CommandParser.Result(COMMAND_ALIAS, ORDERED_ARGUMENTS, NAMED_ARGUMENTS);
    private static final CommandParser.Result PARSE_RESULT_UNKNOWN = new CommandParser.Result(UNKNOWN_COMMAND_NAME, ORDERED_ARGUMENTS, NAMED_ARGUMENTS);

    private static final BotCommandArgumentMeta<String> argumentMeta1 = BotCommandArgumentMeta.of(ARGUMENT_1_KEY, ArgumentTypes.STRING_ARGUMENT);

    private static final BotCommandArgumentMeta<String> argumentMeta2 = BotCommandArgumentMeta.of(ARGUMENT_2_KEY, ArgumentTypes.STRING_ARGUMENT);


    @BeforeEach
    void setUp() {
        when(this.guildCommandMock.getName()).thenReturn(COMMAND_NAME);
        when(this.guildCommandMock.getAliases()).thenReturn(List.of(COMMAND_ALIAS));

        this.objectUnderTest = new CommandManager(this.commandLexerMock, this.commandParserMock, List.of(this.guildCommandMock, this.nonGuildCommandMock));
    }

    @Test
    void testTryExecute() throws CommandExecutionException {
        when(this.commandLexerMock.tokenizeMessage(COMMAND)).thenReturn(LEX_RESULT);
        when(this.commandParserMock.parse(LEX_RESULT)).thenReturn(PARSE_RESULT);
        when(this.guildCommandMock.getArgumentCount()).thenReturn(10);
        doReturn(argumentMeta1).when(this.guildCommandMock).getArgumentMeta(0);
        doReturn(argumentMeta2).when(this.guildCommandMock).getArgumentMeta(ARGUMENT_2_KEY);

        CommandExecutionRequest result = this.objectUnderTest.interpretCommand(COMMAND);

        assertThat(result.commandMeta()).isEqualTo(this.guildCommandMock);

        // TODO validate arguments
    }

    @Test
    void testTryExecuteUnknownCommand() throws CommandExecutionException {
        when(this.commandLexerMock.tokenizeMessage(COMMAND)).thenReturn(LEX_RESULT);
        when(this.commandParserMock.parse(LEX_RESULT)).thenReturn(PARSE_RESULT_UNKNOWN);

        CommandInterpreterException.UnknownCommandException exception =
                assertThrows(CommandInterpreterException.UnknownCommandException.class,
                        () -> this.objectUnderTest.interpretCommand(COMMAND));

        assertThat(exception.getCommandName()).isEqualTo(UNKNOWN_COMMAND_NAME);
    }

    @Test
    void testOnGuildMessageReceivedAlias() throws CommandExecutionException {
        when(this.commandLexerMock.tokenizeMessage(COMMAND)).thenReturn(LEX_RESULT);
        when(this.commandParserMock.parse(LEX_RESULT)).thenReturn(PARSE_RESULT_ALIAS);
        when(this.guildCommandMock.getArgumentCount()).thenReturn(10);
        // not type save
        doReturn(argumentMeta1).when(this.guildCommandMock).getArgumentMeta(0);
        doReturn(argumentMeta2).when(this.guildCommandMock).getArgumentMeta(ARGUMENT_2_KEY);


        CommandExecutionRequest result = this.objectUnderTest.interpretCommand(COMMAND);
        assertThat(result).isNotNull();

        //TODO add botCommandArgumentCaptor checks
    }
}