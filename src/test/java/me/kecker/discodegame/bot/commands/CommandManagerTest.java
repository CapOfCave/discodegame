package me.kecker.discodegame.bot.commands;

import me.kecker.discodegame.bot.domain.commands.BotCommandMeta;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgument;
import me.kecker.discodegame.bot.domain.exceptions.ArgumentParseException;
import me.kecker.discodegame.test.annotationclasses.AnnotationTestClasses;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Disabled("Not yet implemented")
class CommandManagerTest {

    private CommandManager objectUnderTest;

    @Mock
    private CommandLexer commandLexerMock;

    @Mock
    private AnnotationTestClasses.RegisteredGuildCommandBotCommand guildCommandMock;

    @Mock
    private BotCommandMeta nonGuildCommandMock;

    @Captor
    private ArgumentCaptor<List<BotCommandArgument<?>>> botCommandArgumentCaptor;

    private static final String COMMAND_NAME = "commandName";
    private static final String UNKNOWN_COMMAND_NAME = "unknownCommandName";
    private static final String COMMAND_ALIAS = "alias";


    @BeforeEach
    void setUp() {
        when(this.guildCommandMock.getName()).thenReturn(COMMAND_NAME);
        when(this.guildCommandMock.getAliases()).thenReturn(List.of(COMMAND_ALIAS));

        this.objectUnderTest = new CommandManager(this.commandLexerMock, List.of(this.guildCommandMock, this.nonGuildCommandMock));
    }

    @Test
    void testTryExecute() throws ArgumentParseException {
        this.objectUnderTest.handleCommand(COMMAND_NAME);

        verify(this.guildCommandMock, times(1)).accept(this.botCommandArgumentCaptor.capture());
        verify(this.nonGuildCommandMock, never()).accept(anyList());

        //TODO add botCommandArgumentCaptor checks
    }

    @Test
    void testTryExecuteUnknownCommand() throws ArgumentParseException {
        this.objectUnderTest.handleCommand(UNKNOWN_COMMAND_NAME);

        // nothing happens
        verify(this.guildCommandMock, never()).accept(anyList());
        verify(this.nonGuildCommandMock, never()).accept(anyList());
    }

    @Test
    void testOnGuildMessageReceivedAlias() throws ArgumentParseException {
        this.objectUnderTest.handleCommand(COMMAND_ALIAS);

        verify(this.guildCommandMock, times(1)).accept(this.botCommandArgumentCaptor.capture());
        verify(this.nonGuildCommandMock, never()).accept(anyList());

        //TODO add botCommandArgumentCaptor checks
    }
}