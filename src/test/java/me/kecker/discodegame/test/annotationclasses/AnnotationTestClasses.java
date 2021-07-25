package me.kecker.discodegame.test.annotationclasses;

import me.kecker.discodegame.bot.domain.annotations.RegisteredGuildCommand;
import me.kecker.discodegame.bot.domain.commands.BotCommand;

public class AnnotationTestClasses {
    @RegisteredGuildCommand
    public static abstract class RegisteredGuildCommandBotCommand implements BotCommand {}

}
