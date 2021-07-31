package me.kecker.discodegame.bot.lobby.commands;

import lombok.NonNull;
import me.kecker.discodegame.application.api.CodeGame;
import me.kecker.discodegame.application.api.LobbyCreateRequest;
import me.kecker.discodegame.bot.domain.annotations.RegisteredGuildCommand;
import me.kecker.discodegame.bot.domain.commands.BotCommandMetaAdapter;
import me.kecker.discodegame.bot.domain.commands.CommandExecutionContext;
import me.kecker.discodegame.bot.domain.commands.arguments.ArgumentNecessity;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgument;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgumentMeta;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArguments;
import me.kecker.discodegame.bot.domain.commands.arguments.types.ArgumentTypes;
import me.kecker.discodegame.bot.mapping.PlayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@RegisteredGuildCommand
@Component
public class CreateLobbyCommandMeta extends BotCommandMetaAdapter {

    static final BotCommandArgumentMeta<String> LOBBY_NAME =
            BotCommandArgumentMeta.of("lobbyName", ArgumentTypes.STRING_ARGUMENT, List.of("name"), ArgumentNecessity.REQUIRED);
    static final BotCommandArgumentMeta<Integer> MAX =
            BotCommandArgumentMeta.of("max", ArgumentTypes.INTEGER_ARGUMENT, ArgumentNecessity.OPTIONAL);

    @NonNull
    private final CodeGame codeGame;

    @NonNull
    private final PlayerMapper playerMapper;

    @Autowired
    public CreateLobbyCommandMeta(@NonNull CodeGame codeGame, @NonNull PlayerMapper playerMapper) {
        super("create", List.of(LOBBY_NAME, MAX));
        this.codeGame = codeGame;
        this.playerMapper = playerMapper;
    }

    @Override
    public void accept(CommandExecutionContext context, BotCommandArguments arguments) {

        // TODO consider max players
        // TODO exception handling
        String lobbyName = arguments.getRequired(LOBBY_NAME);
        this.codeGame.createLobby(new LobbyCreateRequest(lobbyName, arguments.get(MAX).orElse(0)));
//        this.codeGame.joinLobby(lobbyName, this.playerMapper.mapToPlayer(context.member()));

    }
}
