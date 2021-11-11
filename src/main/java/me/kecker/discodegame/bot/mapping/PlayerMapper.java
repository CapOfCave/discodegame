package me.kecker.discodegame.bot.mapping;

import me.kecker.discodegame.domain.Player;
import net.dv8tion.jda.api.entities.Member;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper {

    public Player mapToPlayer(Member member) {
        return new Player() {

            @Override
            public String getId() {
                return member.getId();
            }
        };
    }
}
