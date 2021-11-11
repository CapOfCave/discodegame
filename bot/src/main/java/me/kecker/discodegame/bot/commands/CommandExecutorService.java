package me.kecker.discodegame.bot.commands;

import lombok.extern.slf4j.Slf4j;
import me.kecker.discodegame.bot.domain.commands.CommandExecutionContext;
import me.kecker.discodegame.bot.domain.commands.CommandExecutionRequest;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommandExecutorService {

    public void executeCommand(CommandExecutionRequest request, CommandExecutionContext context) {
        try {
            request.commandMeta().accept(context, request.arguments());
        } catch (Exception e){
            log.error("Exception while executing command for request {} with context {}: ", request, context, e);
        }
    }
}
