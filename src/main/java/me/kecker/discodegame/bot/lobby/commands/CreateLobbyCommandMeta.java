package me.kecker.discodegame.bot.lobby.commands;

import me.kecker.discodegame.bot.domain.annotations.RegisteredGuildCommand;
import me.kecker.discodegame.bot.domain.commands.BotCommandMetaAdapter;
import me.kecker.discodegame.bot.domain.commands.arguments.ArgumentNecessity;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgumentMeta;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgument;
import me.kecker.discodegame.bot.domain.commands.arguments.types.ArgumentTypes;
import org.springframework.stereotype.Component;

import java.util.List;

@RegisteredGuildCommand
@Component
public class CreateLobbyCommandMeta extends BotCommandMetaAdapter {

    public CreateLobbyCommandMeta() {
        super("create", List.of(
                BotCommandArgumentMeta.of("lobbyName", ArgumentTypes.STRING_ARGUMENT, List.of("name"), ArgumentNecessity.OPTIONAL),
                BotCommandArgumentMeta.of("max", ArgumentTypes.STRING_ARGUMENT, ArgumentNecessity.OPTIONAL)

        ));
    }

    @Override
    public void accept(List<BotCommandArgument<?>> arguments) {
        System.out.printf("Created Lobby %s.%n", arguments.get(0));
    }

}
