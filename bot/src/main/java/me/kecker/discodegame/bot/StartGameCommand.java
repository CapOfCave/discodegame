package me.kecker.discodegame.bot;

import lombok.NonNull;
import me.kecker.discodegame.core.application.api.CodeGame;
import me.kecker.discodegame.bot.domain.annotations.RegisteredGuildCommand;
import me.kecker.discodegame.bot.domain.commands.BotCommandMetaAdapter;
import me.kecker.discodegame.bot.domain.commands.CommandExecutionContext;
import me.kecker.discodegame.bot.domain.commands.arguments.ArgumentNecessity;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgumentMeta;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArguments;
import me.kecker.discodegame.bot.domain.commands.arguments.types.ArgumentTypes;
import me.kecker.discodegame.core.domain.Game;
import me.kecker.discodegame.core.domain.exceptions.PlayerNotInGameException;
import me.kecker.discodegame.core.domain.exceptions.PlayerNotInLobbyException;
import me.kecker.discodegame.core.domain.exceptions.UnsupportedLanguageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@RegisteredGuildCommand
@Component
public class StartGameCommand extends BotCommandMetaAdapter {

    @NonNull
    private final CodeGame codeGame;

    @Autowired
    public StartGameCommand(@NonNull CodeGame codeGame) {
        super("start");
        this.codeGame = codeGame;
    }

    @Override
    public void accept(CommandExecutionContext context, BotCommandArguments arguments) {
        try {
            this.codeGame.startGame(context.member().getId(), new Game("1234"));
        } catch (PlayerNotInLobbyException e) {
            e.printStackTrace();
        }
    }
}
