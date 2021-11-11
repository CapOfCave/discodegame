package me.kecker.discodegame.piston.services;

import lombok.NonNull;
import me.kecker.discodegame.piston.dtos.PistonRuntimeResponseDTO;
import me.kecker.discodegame.piston.http.PistonConnector;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class PistonRuntimeManager {

    @NonNull
    private final PistonConnector pistonConnector;

    private PistonRuntimeResponseDTO[] runtimes = null;

    public PistonRuntimeManager(@NonNull PistonConnector pistonConnector) {
        this.pistonConnector = pistonConnector;
    }

    public Optional<PistonRuntimeResponseDTO> getRuntime(@NonNull String language) {
        PistonRuntimeResponseDTO[] runtimes = this.getOrFetchRuntimes();
        // TODO handle multiple versions!
        return Arrays.stream(runtimes)
                .filter(runtime -> runtime.matchesLanguage(language))
                .findFirst();
    }

    private PistonRuntimeResponseDTO[] getOrFetchRuntimes() {
        if (this.runtimes == null) {
            fetchRuntimes();
        }
        return this.runtimes;
    }

    private void fetchRuntimes() {
        this.runtimes = this.pistonConnector.getRuntimes();
    }
}
