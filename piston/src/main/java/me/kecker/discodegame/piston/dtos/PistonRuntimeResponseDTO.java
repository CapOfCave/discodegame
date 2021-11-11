package me.kecker.discodegame.piston.dtos;

import java.util.List;
import java.util.Objects;

public final class PistonRuntimeResponseDTO {
    private String language;
    private String version;
    private List<String> aliases;

    public PistonRuntimeResponseDTO(String language, String version, List<String> aliases) {
        this.language = language;
        this.version = version;
        this.aliases = aliases;
    }

    public boolean matchesLanguage(String language) {
        return this.language.equals(language) || this.aliases.contains(language);
    }

    public String language() {
        return language;
    }

    public String version() {
        return version;
    }

    public List<String> aliases() {
        return aliases;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PistonRuntimeResponseDTO) obj;
        return Objects.equals(this.language, that.language) &&
                Objects.equals(this.version, that.version) &&
                Objects.equals(this.aliases, that.aliases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, version, aliases);
    }

    @Override
    public String toString() {
        return "PistonRuntimeResponseDTO[" +
                "language=" + language + ", " +
                "version=" + version + ", " +
                "aliases=" + aliases + ']';
    }

}
