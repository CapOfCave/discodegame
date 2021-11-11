package me.kecker.discodegame.annotationclasses;

import me.kecker.discodegame.bot.domain.annotations.RegisteredGuildCommand;
import me.kecker.discodegame.bot.domain.commands.BotCommandMeta;

public class AnnotationTestClasses {
    @RegisteredGuildCommand
    public static abstract class RegisteredGuildCommandBotCommand implements BotCommandMeta {}

}
