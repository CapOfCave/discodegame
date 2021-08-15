package me.kecker.discodegame.piston.dtos;

import java.util.List;

public record PistonRuntimeResponseDTO(String language, String version, List<String> aliases) {
    public boolean matchesLanguage(String language) {
        return this.language.equals(language) || this.aliases.contains(language);
    }
}
