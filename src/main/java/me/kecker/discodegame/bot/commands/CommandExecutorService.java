package me.kecker.discodegame.bot.commands;

import me.kecker.discodegame.bot.domain.commands.CommandExecutionContext;
import me.kecker.discodegame.bot.domain.commands.CommandExecutionRequest;
import org.springframework.stereotype.Component;

@Component
public class CommandExecutorService {

    public void executeCommand(CommandExecutionRequest request, CommandExecutionContext context) {
        request.commandMeta().accept(context, request.arguments());
    }
}
