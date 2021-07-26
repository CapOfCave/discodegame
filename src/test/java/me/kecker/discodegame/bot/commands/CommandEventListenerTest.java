package me.kecker.discodegame.bot.commands;

import me.kecker.discodegame.bot.domain.exceptions.ArgumentParseException;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CommandEventListenerTest {

    private CommandEventListener objectUnderTest;

    @Mock
    private CommandLexer commandLexerMock;

    @Mock
    private CommandManager commandManagerMock;

    @Mock
    private GuildMessageReceivedEvent guildMessageReceivedEventMock;

    @Mock
    private Message messageMock;

    private static final String RAW_COMMAND = "rawCommand";

    @BeforeEach
    void setUp() {
        when(this.guildMessageReceivedEventMock.getMessage()).thenReturn(messageMock);
        this.objectUnderTest = new CommandEventListener(this.commandLexerMock, this.commandManagerMock);
    }

    @Test
    void testOnGuildMessageReceived() throws ArgumentParseException {
        when(this.commandLexerMock.isCommand(this.messageMock)).thenReturn(true);
        when(this.messageMock.getContentRaw()).thenReturn(RAW_COMMAND);

        this.objectUnderTest.onGuildMessageReceived(this.guildMessageReceivedEventMock);

        verify(this.commandManagerMock, times(1)).handleCommand(RAW_COMMAND);
    }

    @Test
    void testOnGuildMessageReceivedNoCommand() throws ArgumentParseException {
        when(this.commandLexerMock.isCommand(this.messageMock)).thenReturn(false);

        this.objectUnderTest.onGuildMessageReceived(this.guildMessageReceivedEventMock);

        // nothing happens
        verify(this.commandManagerMock, never()).handleCommand(anyString());
        verify(this.commandLexerMock, never()).tokenizeMessage(any(Message.class));
        verify(this.commandLexerMock, never()).tokenizeMessage(any(String.class));

    }
}