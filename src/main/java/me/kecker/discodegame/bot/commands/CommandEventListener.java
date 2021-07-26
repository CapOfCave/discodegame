package me.kecker.discodegame.bot.commands;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.kecker.discodegame.bot.domain.annotations.RegisteredEventListener;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@RegisteredEventListener
@Component
public class CommandEventListener extends ListenerAdapter {

    @NonNull
    private final CommandLexer commandLexer;

    @NonNull
    private final CommandManager commandManager;

    @Autowired
    public CommandEventListener(@NonNull CommandLexer commandLexer, @NonNull CommandManager commandManager) {
        this.commandLexer = commandLexer;
        this.commandManager = commandManager;
    }

    @SneakyThrows // TODO exception handling concept
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (!this.commandLexer.isCommand(event.getMessage())) {
            return;
        }
        this.commandManager.handleCommand(event.getMessage().getContentRaw());
    }


}
