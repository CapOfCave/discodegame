package me.kecker.discodegame.bot.commands;

import me.kecker.discodegame.bot.domain.commands.BotCommandMeta;
import me.kecker.discodegame.bot.domain.commands.CommandExecutionContext;
import me.kecker.discodegame.bot.domain.commands.CommandExecutionRequest;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgument;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArguments;
import me.kecker.discodegame.bot.domain.exceptions.CommandExecutionException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CommandEventListenerTest {

    private CommandEventListener objectUnderTest;

    @Mock
    private CommandManager commandManagerMock;

    @Mock
    private CommandExecutorService commandExecutorServiceMock;

    @Mock
    private GuildMessageReceivedEvent guildMessageReceivedEventMock;

    @Mock
    private Message messageMock;

    @Mock
    private Guild guild;

    @Mock
    private Member member;

    @Mock
    private BotCommandMeta commandMeta;

    @Mock
    private Map<String, BotCommandArgument<?>> argumentMap;

    private BotCommandArguments arguments;

    private CommandExecutionRequest commandExecutionRequest;

    private static final String RAW_COMMAND = "rawCommand";

    @BeforeEach
    void setUp() {
        when(this.guildMessageReceivedEventMock.getMessage()).thenReturn(messageMock);
        this.arguments = new BotCommandArguments(this.argumentMap);
        this.objectUnderTest = new CommandEventListener(this.commandManagerMock, this.commandExecutorServiceMock);
        this.commandExecutionRequest = new CommandExecutionRequest(this.commandMeta, this.arguments);
    }

    @Test
    void testOnGuildMessageReceived() throws CommandExecutionException {
        when(this.messageMock.getContentRaw()).thenReturn(RAW_COMMAND);
        when(this.guildMessageReceivedEventMock.getGuild()).thenReturn(this.guild);
        when(this.guildMessageReceivedEventMock.getMember()).thenReturn(this.member);
        when(this.commandManagerMock.isCommand(RAW_COMMAND)).thenReturn(true);
        when(this.commandManagerMock.interpretCommand(RAW_COMMAND)).thenReturn(this.commandExecutionRequest);

        this.objectUnderTest.onGuildMessageReceived(this.guildMessageReceivedEventMock);

        verify(this.commandExecutorServiceMock, times(1))
                .executeCommand(this.commandExecutionRequest, new CommandExecutionContext(this.guild, this.member));
    }

    @Test
    void testOnGuildMessageReceivedNoCommand() {
        when(this.messageMock.getContentRaw()).thenReturn(RAW_COMMAND);
        when(this.commandManagerMock.isCommand(RAW_COMMAND)).thenReturn(false);

        this.objectUnderTest.onGuildMessageReceived(this.guildMessageReceivedEventMock);

        // nothing happens
        verify(this.commandExecutorServiceMock, never()).executeCommand(any(CommandExecutionRequest.class), any(CommandExecutionContext.class));

    }
}