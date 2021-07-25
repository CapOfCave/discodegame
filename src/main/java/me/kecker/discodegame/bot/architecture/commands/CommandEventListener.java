package me.kecker.discodegame.bot.architecture.commands;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import me.kecker.discodegame.bot.domain.commands.BotCommand;
import me.kecker.discodegame.bot.domain.annotations.RegisteredEventListener;
import me.kecker.discodegame.bot.domain.annotations.RegisteredGuildCommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@RegisteredEventListener
@Component
public class CommandEventListener extends ListenerAdapter {

    @NonNull
    @Value("${dcg.bot.prefix.value:!}")
    private String prefix;

    @NonNull
    @Value("${dcg.bot.prefix.allowSpace:false}")
    private boolean allowWhiteSpaceAfterPrefix;

    @NonNull
    @Value("${dcg.bot.prefix.ignoreCase:false}")
    private boolean ignorePrefixCase;

    @NonNull
    private Collection<? extends BotCommand> guildCommands;

    @Autowired
    public CommandEventListener(Collection<? extends BotCommand> allCommands) {
        this.guildCommands = allCommands
                .stream()
                .filter(botCommand -> botCommand.getClass().isAnnotationPresent(RegisteredGuildCommand.class))
                .collect(Collectors.toSet());
        log.info("CommandEventListener found {} commands, of which {} are guild commands.", allCommands, this.guildCommands.size());
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String messageContentRaw = event.getMessage().getContentRaw();
        if (!messageContentRaw.startsWith(prefix)) {
            return;
        }

        executeRelevantCommands(messageContentRaw);

    }

    private void executeRelevantCommands(String messageContentRaw) {
        String contentWithoutPrefix = ignorePrefixCase ?
                StringUtils.removeStartIgnoreCase(messageContentRaw, prefix)
                : StringUtils.removeStart(messageContentRaw, prefix);

        if (allowWhiteSpaceAfterPrefix) {
            contentWithoutPrefix = contentWithoutPrefix.stripLeading();
        }

        String[] commandNameAndArguments = contentWithoutPrefix.split("\\s", 2);
        String command = commandNameAndArguments[0];

        // TODO add arguments
        guildCommands
                .stream()
                .filter(guildCommand -> guildCommand.getName().equals(command)
                        || guildCommand.getAliases().contains(command))
                .forEach(BotCommand::accept);
    }
}
