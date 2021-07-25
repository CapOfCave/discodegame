package me.kecker.discodegame.bot.lobby.commands;

import me.kecker.discodegame.bot.domain.annotations.RegisteredGuildCommand;
import me.kecker.discodegame.bot.domain.commands.BotCommandAdapter;
import org.springframework.stereotype.Component;

@RegisteredGuildCommand
@Component
public class CreateLobbyCommand extends BotCommandAdapter {

    private static final String NAME = "create";

    public CreateLobbyCommand() {
        super(NAME);
    }

    @Override
    public void accept() {
        //TODO call create Lobby
        System.out.println("Created Lobby.");
    }
}
