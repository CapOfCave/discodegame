package me.kecker.discodegame.bot.architecture.commands;

import me.kecker.discodegame.bot.domain.commands.BotCommand;
import me.kecker.discodegame.test.annotationclasses.AnnotationTestClasses;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CommandEventListenerTest {

    private CommandEventListener objectUnderTest;

    @Mock
    private CommandParser commandParserMock;

    @Mock
    private AnnotationTestClasses.RegisteredGuildCommandBotCommand guildCommandMock;

    @Mock
    private BotCommand nonGuildCommandMock;

    @Mock
    private GuildMessageReceivedEvent guildMessageReceivedEventMock;

    @Mock
    private Message messageMock;

    private static final String COMMAND_NAME = "commandName";
    private static final String UNKNOWN_COMMAND_NAME = "unknownCommandName";
    private static final String COMMAND_ALIAS = "alias";

    @BeforeEach
    void setUp() {
        when(this.guildCommandMock.getName()).thenReturn(COMMAND_NAME);
        when(this.guildCommandMock.getAliases()).thenReturn(List.of(COMMAND_ALIAS));
        when(this.guildMessageReceivedEventMock.getMessage()).thenReturn(messageMock);

        this.objectUnderTest = new CommandEventListener(this.commandParserMock, List.of(this.guildCommandMock, this.nonGuildCommandMock));
    }

    @Test
    void testOnGuildMessageReceived() {
        when(this.commandParserMock.isCommand(this.messageMock)).thenReturn(true);
        when(this.commandParserMock.getCommandName(this.messageMock)).thenReturn(COMMAND_NAME);

        this.objectUnderTest.onGuildMessageReceived(this.guildMessageReceivedEventMock);

        verify(this.guildCommandMock, times(1)).accept();
        verify(this.nonGuildCommandMock, never()).accept();
    }

    @Test
    void testOnGuildMessageReceivedUnknownCommand() {
        when(this.commandParserMock.isCommand(this.messageMock)).thenReturn(true);
        when(this.commandParserMock.getCommandName(this.messageMock)).thenReturn(UNKNOWN_COMMAND_NAME);

        this.objectUnderTest.onGuildMessageReceived(this.guildMessageReceivedEventMock);

        // nothing happens
        verify(this.guildCommandMock, never()).accept();
        verify(this.nonGuildCommandMock, never()).accept();
    }

    @Test
    void testOnGuildMessageReceivedNoCommand() {
        when(this.commandParserMock.isCommand(this.messageMock)).thenReturn(false);

        this.objectUnderTest.onGuildMessageReceived(this.guildMessageReceivedEventMock);

        // nothing happens
        verify(this.guildCommandMock, never()).accept();
        verify(this.nonGuildCommandMock, never()).accept();
    }

    @Test
    void testOnGuildMessageReceivedAlias() {
        when(this.commandParserMock.isCommand(this.messageMock)).thenReturn(true);
        when(this.commandParserMock.getCommandName(this.messageMock)).thenReturn(COMMAND_ALIAS);

        this.objectUnderTest.onGuildMessageReceived(this.guildMessageReceivedEventMock);

        // nothing happens
        verify(this.guildCommandMock, times(1)).accept();
        verify(this.nonGuildCommandMock, never()).accept();
    }
}