package me.kecker.discodegame.slashbot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;
import java.util.Collection;

@Component
public class JdaManager {
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(JdaManager.class);
    private final JDA jda;


    @Autowired
    public JdaManager(
            @Value("${dcg.bot.token}") String token,
            Collection<EventListener> listeners
    ) throws LoginException {
        this.jda = JDABuilder
                .createDefault(token, GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS))
                .addEventListeners(listeners.toArray())
                .build();
        log.info("Found and registered {} EventListeners: {}", listeners.size(), listeners);

    }
}

