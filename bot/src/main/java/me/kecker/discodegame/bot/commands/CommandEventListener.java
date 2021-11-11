package me.kecker.discodegame.bot.commands;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.kecker.discodegame.bot.domain.annotations.RegisteredEventListener;
import me.kecker.discodegame.bot.domain.commands.CommandExecutionContext;
import me.kecker.discodegame.bot.domain.commands.CommandExecutionRequest;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@RegisteredEventListener
@Component
public class CommandEventListener extends ListenerAdapter {

    @NonNull
    private final CommandManager commandManager;

    @NonNull
    private final CommandExecutorService executorService;

    @Autowired
    public CommandEventListener(@NonNull CommandManager commandManager, @NonNull CommandExecutorService executorService) {
        this.commandManager = commandManager;
        this.executorService = executorService;
    }

    @SneakyThrows // TODO exception handling
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String contentRaw = event.getMessage().getContentRaw();
        if (!this.commandManager.isCommand(contentRaw)) {
            return;
        }
        var context = new CommandExecutionContext(event.getGuild(), event.getMember());
        CommandExecutionRequest commandExecutionRequest = this.commandManager.interpretCommand(contentRaw);
        this.executorService.executeCommand(commandExecutionRequest, context);
    }
}
