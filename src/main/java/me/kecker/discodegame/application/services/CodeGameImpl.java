package me.kecker.discodegame.application.services;

import lombok.AllArgsConstructor;
import me.kecker.discodegame.application.api.CodeGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor_ = @Autowired)
public class CodeGameImpl implements CodeGame {

    private SessionManager sessionManager;

    @Override
    public void createLobby(String id) {
        this.sessionManager.createLobby(id);
    }

}
