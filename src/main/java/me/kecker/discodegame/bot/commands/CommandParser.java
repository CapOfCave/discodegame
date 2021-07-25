package me.kecker.discodegame.bot.commands;

import lombok.NonNull;
import me.kecker.discodegame.bot.domain.commands.CommandExecutionDTO;
import net.dv8tion.jda.api.entities.Message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CommandParser {

    @NonNull
    private ArgumentParser argumentParser;

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
    public CommandParser(@NonNull ArgumentParser argumentParser) {
        this.argumentParser = argumentParser;
    }

    public boolean isCommand(Message message) {
        return isCommand(message.getContentRaw());
    }

    public Optional<CommandExecutionDTO> mapToCommandExecutionDTO(Message message) {
        return mapToCommandExecutionDTO(message.getContentRaw());
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
        String contentWithoutPrefix = this.ignorePrefixCase ?
                StringUtils.removeStartIgnoreCase(messageContent, prefix)
                : StringUtils.removeStart(messageContent, prefix);

        if (this.ignoreWhiteSpaceAfterPrefix) {
            contentWithoutPrefix = contentWithoutPrefix.stripLeading();
        }

        String[] commandNameAndArguments = contentWithoutPrefix.split("\\s+", 2);
        return commandNameAndArguments[0];
    }


    public Optional<CommandExecutionDTO> mapToCommandExecutionDTO(String messageContent) {
        String contentWithoutPrefix = this.ignorePrefixCase ?
                StringUtils.removeStartIgnoreCase(messageContent, this.prefix)
                : StringUtils.removeStart(messageContent, this.prefix);

        if (this.ignoreWhiteSpaceAfterPrefix) {
            contentWithoutPrefix = contentWithoutPrefix.stripLeading();
        }

        String[] commandNameAndArguments = contentWithoutPrefix.split("\\s+", 2);

        if (commandNameAndArguments.length == 0) {
            return Optional.empty();
        }

        if (commandNameAndArguments.length == 1) {
            CommandExecutionDTO commandExecutionDTO = new CommandExecutionDTO(commandNameAndArguments[0]);
            return Optional.of(commandExecutionDTO);
        }

        ArgumentParser.ParseResult parseResult = this.argumentParser.parse(commandNameAndArguments[1]);

        CommandExecutionDTO commandExecutionDTO = new CommandExecutionDTO(
                commandNameAndArguments[0], commandNameAndArguments[1], parseResult.getOrderedArguments(), parseResult.getNamedArguments());
        return Optional.of(commandExecutionDTO);
    }

}
