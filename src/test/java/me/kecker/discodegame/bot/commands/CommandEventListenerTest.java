package me.kecker.discodegame.bot.commands;

import me.kecker.discodegame.bot.domain.commands.CommandExecutionDTO;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CommandEventListenerTest {

    private CommandEventListener objectUnderTest;

    @Mock
    private CommandParser commandParserMock;

    @Mock
    private CommandManager commandManagerMock;

    @Mock
    private GuildMessageReceivedEvent guildMessageReceivedEventMock;

    @Mock
    private Message messageMock;

    private static final CommandExecutionDTO COMMAND_EXECUTION_DTO = new CommandExecutionDTO("commandName");

    @BeforeEach
    void setUp() {
        when(this.guildMessageReceivedEventMock.getMessage()).thenReturn(messageMock);
        this.objectUnderTest = new CommandEventListener(this.commandParserMock, this.commandManagerMock);
    }

    @Test
    void testOnGuildMessageReceived() {
        when(this.commandParserMock.isCommand(this.messageMock)).thenReturn(true);
        when(this.commandParserMock.mapToCommandExecutionDTO(messageMock)).thenReturn(Optional.of(COMMAND_EXECUTION_DTO));

        this.objectUnderTest.onGuildMessageReceived(this.guildMessageReceivedEventMock);

        verify(this.commandManagerMock, times(1)).tryExecute(COMMAND_EXECUTION_DTO);
    }

    @Test
    void testOnGuildMessageReceivedNoCommand() {
        when(this.commandParserMock.isCommand(this.messageMock)).thenReturn(false);

        this.objectUnderTest.onGuildMessageReceived(this.guildMessageReceivedEventMock);

        // nothing happens
        verify(this.commandManagerMock, never()).tryExecute(any(CommandExecutionDTO.class));
        verify(this.commandParserMock, never()).mapToCommandExecutionDTO(any(Message.class));
        verify(this.commandParserMock, never()).mapToCommandExecutionDTO(any(String.class));

    }
}