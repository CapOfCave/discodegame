package me.kecker.discodegame.bot;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import me.kecker.discodegame.bot.domain.annotations.RegisteredEventListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.EventListener;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;
import java.util.Collection;

@Slf4j
@Component
public class JdaManager {

    private ApplicationContext applicationContext;

    private Collection<? extends EventListener> allEventListeners;

    @Value("${dcg.bot.token}")
    private String token;

    @Getter
    private JDA jda;

    @Autowired
    public JdaManager(ApplicationContext applicationContext, Collection<? extends EventListener> allEventListeners) {
        this.applicationContext = applicationContext;
        this.allEventListeners = allEventListeners;
    }

    @PostConstruct
    public void init() throws LoginException {
        this.jda = createJda();
        log.info("Discord Bot started successfully");
    }

    @NotNull
    private JDA createJda() throws LoginException {
        Object[] relevantEventListeners = getRelevantEventListeners();
        log.info("Found {} EventListener(s) to be registered for the JDA bot", relevantEventListeners.length);
        return JDABuilder
                .createDefault(token)
                .addEventListeners(relevantEventListeners)
                .build();
    }

    @NotNull
    private EventListener[] getRelevantEventListeners() {
        return this.allEventListeners
                .stream()
                .filter(eventListener -> eventListener.getClass().isAnnotationPresent(RegisteredEventListener.class))
                .toArray(EventListener[]::new);
    }
}
