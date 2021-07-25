package me.kecker.discodegame.bot;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;

@Slf4j
@Component
public class JdaManager {

    @Value("${jda.discord.token}")
    private String token;

    @Getter
    private JDA jda;

    @PostConstruct
    public void init() throws LoginException {
        this.jda = JDABuilder.createDefault(token).build();
        log.info("Discord Bot started successfully");
    }
}
