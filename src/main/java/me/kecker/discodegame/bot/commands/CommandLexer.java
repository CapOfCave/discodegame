package me.kecker.discodegame.bot.commands;

import lombok.NonNull;
import me.kecker.discodegame.bot.domain.commands.CommandExecutionDTO;
import me.kecker.discodegame.bot.domain.commands.arguments.RawArgument;
import me.kecker.discodegame.bot.domain.exceptions.ArgumentParseException;
import net.dv8tion.jda.api.entities.Message;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class CommandLexer {

    @NonNull
    private ArgumentLexer argumentLexer;

    @NonNull
    @Value("${dcg.bot.prefix.value:!}")
    private String prefix;

    @NonNull
    @Value("${dcg.bot.prefix.ignoreSpace:true}")
    private boolean ignoreWhiteSpaceAfterPrefix;

    @NonNull
    @Value("${dcg.bot.prefix.ignoreCase:false}")
    private boolean ignorePrefixCase;

    @Autowired
    public CommandLexer(@NonNull ArgumentLexer argumentLexer) {
        this.argumentLexer = argumentLexer;
    }

    public boolean isCommand(Message message) {
        return isCommand(message.getContentRaw());
    }

    public Result tokenizeMessage(Message message) throws ArgumentParseException {
        return tokenizeMessage(message.getContentRaw());
    }

    @Deprecated
    public String getCommandName(Message message) {
        return getCommandName(message.getContentRaw());
    }

    public boolean isCommand(String messageContent) {
        return messageContent.regionMatches(this.ignorePrefixCase, 0, this.prefix, 0, this.prefix.length());
    }

    @Deprecated
    public String getCommandName(String messageContent) {
        String contentWithoutPrefix = removePrefix(messageContent);
        String[] commandNameAndArguments = contentWithoutPrefix.split("\\s+", 2);
        return commandNameAndArguments[0];
    }

    public Result tokenizeMessage(@NonNull String messageContent) throws ArgumentParseException {
        String contentWithoutPrefix = removePrefix(messageContent);
        String[] commandNameAndArguments = contentWithoutPrefix.split("\\s+", 2);
        String commandName = commandNameAndArguments[0];
        if (commandNameAndArguments.length == 1) {
            return new Result(commandName, Collections.emptyList());
        }

        List<RawArgument> arguments = this.argumentLexer.tokenize(commandNameAndArguments[1]);
        return new Result(commandName, arguments);
    }

    @Nullable
    private String removePrefix(@NonNull String messageContent) {
        String contentWithoutPrefix = this.ignorePrefixCase ?
                StringUtils.removeStartIgnoreCase(messageContent, this.prefix)
                : StringUtils.removeStart(messageContent, this.prefix);

        if (!this.ignoreWhiteSpaceAfterPrefix) {
            return contentWithoutPrefix;
        }
        return contentWithoutPrefix.stripLeading();
    }

    record Result(String command, List<RawArgument> arguments) {
    }

}
