package me.kecker.discodegame.bot;

import lombok.NonNull;
import me.kecker.discodegame.bot.domain.annotations.RegisteredGuildCommand;
import me.kecker.discodegame.bot.domain.commands.BotCommandMetaAdapter;
import me.kecker.discodegame.bot.domain.commands.CommandExecutionContext;
import me.kecker.discodegame.bot.domain.commands.arguments.ArgumentNecessity;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgumentMeta;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArguments;
import me.kecker.discodegame.bot.domain.commands.arguments.types.ArgumentTypes;
import me.kecker.discodegame.core.application.api.CodeGame;
import me.kecker.discodegame.core.domain.exceptions.PlayerNotInGameException;
import me.kecker.discodegame.core.domain.exceptions.UnsupportedLanguageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@RegisteredGuildCommand
@Component
public class SubmitResultsCommand extends BotCommandMetaAdapter {

    static final BotCommandArgumentMeta<String> LANGUAGE =
            BotCommandArgumentMeta.of("language", ArgumentTypes.STRING_ARGUMENT, List.of("lang"), ArgumentNecessity.REQUIRED);
    static final BotCommandArgumentMeta<String> SOURCE_CODE =
            BotCommandArgumentMeta.of("source", ArgumentTypes.STRING_ARGUMENT, List.of("code", "submission"), ArgumentNecessity.REQUIRED);

    @NonNull
    private final CodeGame codeGame;

    @Autowired
    public SubmitResultsCommand(@NonNull CodeGame codeGame) {
        super("submit", List.of(LANGUAGE, SOURCE_CODE));
        this.codeGame = codeGame;
    }

    @Override
    public void accept(CommandExecutionContext context, BotCommandArguments arguments) {

        try {
            this.codeGame.submitResult(context.member().getId(), arguments.getRequired(LANGUAGE), arguments.getRequired(SOURCE_CODE));
        } catch (PlayerNotInGameException | UnsupportedLanguageException e) {
            e.printStackTrace();
        }

    }
}
