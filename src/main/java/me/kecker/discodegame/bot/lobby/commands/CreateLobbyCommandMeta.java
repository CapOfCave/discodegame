package me.kecker.discodegame.bot.lobby.commands;

import me.kecker.discodegame.bot.domain.annotations.RegisteredGuildCommand;
import me.kecker.discodegame.bot.domain.commands.BotCommandMetaAdapter;
import me.kecker.discodegame.bot.domain.commands.arguments.ArgumentNecessity;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgument;
import me.kecker.discodegame.bot.domain.commands.arguments.BotCommandArgumentMeta;
import me.kecker.discodegame.bot.domain.commands.arguments.types.ArgumentTypes;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@RegisteredGuildCommand
@Component
public class CreateLobbyCommandMeta extends BotCommandMetaAdapter {


    public static final BotCommandArgumentMeta<String> LOBBY_NAME = BotCommandArgumentMeta.of("lobbyName", ArgumentTypes.STRING_ARGUMENT, List.of("name"), ArgumentNecessity.OPTIONAL);
    public static final BotCommandArgumentMeta<Integer> MAX = BotCommandArgumentMeta.of("max", ArgumentTypes.INTEGER_ARGUMENT, ArgumentNecessity.OPTIONAL);

    public CreateLobbyCommandMeta() {
        super("create", List.of(LOBBY_NAME, MAX));
    }

    @Override
    public void accept(Map<String, BotCommandArgument<?>> arguments) {
        System.out.printf("Created Lobby %s. Max players: %d.%n",
                getArg(arguments, LOBBY_NAME).orElse("without name"),
                getArg(arguments, MAX).orElse(12));
    }
}
