package me.kecker.discodegame.bot.lobby.commands;

import me.kecker.discodegame.core.application.api.CodeGame;
import me.kecker.discodegame.core.application.api.LobbyCreateRequest;
import me.kecker.discodegame.bot.domain.commands.CommandExecutionContext;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgument;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArguments;
import me.kecker.discodegame.bot.mapping.PlayerMapper;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static me.kecker.discodegame.bot.lobby.commands.CreateLobbyCommandMeta.LOBBY_NAME;
import static me.kecker.discodegame.bot.lobby.commands.CreateLobbyCommandMeta.MAX;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateLobbyCommandMetaTest {

    private CreateLobbyCommandMeta objectUnderTest;

    @Mock
    private CodeGame codeGameMock;

    @Mock
    private PlayerMapper playerMapperMock;

    @Mock
    private Guild guild;

    @Mock
    private Member member;

    private CommandExecutionContext context;

    public static final String LOBBY_NAME_ARGUMENT_VALUE = "lobbyNameValue";
    private static final BotCommandArgument<String> LOBBY_NAME_ARGUMENT = new BotCommandArgument<>(LOBBY_NAME, LOBBY_NAME_ARGUMENT_VALUE);
    public static final int MAX_ARGUMENT_VALUE = 4;
    private static final BotCommandArgument<Integer> MAX_ARGUMENT = new BotCommandArgument<>(MAX, MAX_ARGUMENT_VALUE);

    private static final BotCommandArguments lobbyArguments = new BotCommandArguments(Map.of(LOBBY_NAME.name(), LOBBY_NAME_ARGUMENT));
    private static final BotCommandArguments allArguments = new BotCommandArguments(Map.of(LOBBY_NAME.name(), LOBBY_NAME_ARGUMENT, MAX.name(), MAX_ARGUMENT));

    @BeforeEach
    public void setUp() {
        this.context = new CommandExecutionContext(this.guild, this.member);
        this.objectUnderTest = new CreateLobbyCommandMeta(this.codeGameMock, this.playerMapperMock);
    }

    @Test
    public void testAcceptNoMax() {
        this.objectUnderTest.accept(this.context, lobbyArguments);

        var expectedRequest = new LobbyCreateRequest(LOBBY_NAME_ARGUMENT_VALUE, 0);
        verify(this.codeGameMock, times(1)).createLobby(expectedRequest);
    }

    @Test
    public void testAccept() {
        this.objectUnderTest.accept(this.context, allArguments);

        var expectedRequest = new LobbyCreateRequest(LOBBY_NAME_ARGUMENT_VALUE, MAX_ARGUMENT_VALUE);
        verify(this.codeGameMock, times(1)).createLobby(expectedRequest);
    }
}