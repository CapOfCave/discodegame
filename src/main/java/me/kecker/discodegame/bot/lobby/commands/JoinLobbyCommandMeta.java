package me.kecker.discodegame.bot.lobby.commands;

import lombok.NonNull;
import me.kecker.discodegame.application.api.CodeGame;
import me.kecker.discodegame.bot.domain.annotations.RegisteredGuildCommand;
import me.kecker.discodegame.bot.domain.commands.BotCommandMetaAdapter;
import me.kecker.discodegame.bot.domain.commands.CommandExecutionContext;
import me.kecker.discodegame.bot.domain.commands.arguments.ArgumentNecessity;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgument;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgumentMeta;
import me.kecker.discodegame.bot.domain.commands.arguments.types.ArgumentTypes;
import me.kecker.discodegame.bot.mapping.PlayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@RegisteredGuildCommand
@Component
public class JoinLobbyCommandMeta extends BotCommandMetaAdapter {

    static final BotCommandArgumentMeta<String> LOBBY_ID =
            BotCommandArgumentMeta.of("lobbyId", ArgumentTypes.STRING_ARGUMENT,List.of("id"), ArgumentNecessity.REQUIRED);

    @NonNull
    private final CodeGame codeGame;

    @NonNull
    private final PlayerMapper playerMapper;

    @Autowired
    public JoinLobbyCommandMeta(@NonNull CodeGame codeGame, @NonNull PlayerMapper playerMapper) {
        super("join", List.of(LOBBY_ID));
        this.codeGame = codeGame;
        this.playerMapper = playerMapper;
    }

    @Override
    public void accept(CommandExecutionContext context, Map<String, BotCommandArgument<?>> argumentsByName) {
        this.codeGame.joinLobby(getRequiredArg(argumentsByName, LOBBY_ID), this.playerMapper.mapToPlayer(context.member()));
    }
}
