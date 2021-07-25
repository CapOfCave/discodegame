package me.kecker.discodegame.bot.commands;

import lombok.NonNull;
import net.dv8tion.jda.api.entities.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CommandParser {

    @NonNull
    @Value("${dcg.bot.prefix.value:!}")
    private String prefix;

    @NonNull
    @Value("${dcg.bot.prefix.ignoreSpace:true}")
    private boolean ignoreWhiteSpaceAfterPrefix;

    @NonNull
    @Value("${dcg.bot.prefix.ignoreCase:false}")
    private boolean ignorePrefixCase;

    public boolean isCommand(Message message) {
        return isCommand(message.getContentRaw());
    }

    public String getCommandName (Message message) {
        return getCommandName(message.getContentRaw());
    }

    public boolean isCommand(String messageContent) {
        return messageContent.regionMatches(this.ignorePrefixCase, 0, this.prefix, 0, this.prefix.length());
    }

    public String getCommandName(String messageContent) {
        String contentWithoutPrefix = ignorePrefixCase ?
                StringUtils.removeStartIgnoreCase(messageContent, prefix)
                : StringUtils.removeStart(messageContent, prefix);

        if (ignoreWhiteSpaceAfterPrefix) {
            contentWithoutPrefix = contentWithoutPrefix.stripLeading();
        }

        String[] commandNameAndArguments = contentWithoutPrefix.split("\\s", 2);
        return commandNameAndArguments[0];
    }


}
