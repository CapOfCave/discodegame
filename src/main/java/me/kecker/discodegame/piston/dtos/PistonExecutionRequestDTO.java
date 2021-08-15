package me.kecker.discodegame.piston.dtos;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

public record PistonExecutionRequestDTO(
        @NonNull String language,
        @NonNull String version,
        @NonNull PistonExecutionFile[] files,
        String stdin,
        String[] args,
        int compileTimeout,
        int runTimeout,
        int compileMemoryLimit,
        int runMemoryLimit
) {

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String language;
        private String version;
        private final List<PistonExecutionFile> files = new ArrayList<>();
        private String stdin;
        private String[] args;
        private int compileTimeout;
        private int runTimeout;
        private int compileMemoryLimit;
        private int runMemoryLimit;

        Builder() {
        }

        public PistonExecutionRequestDTO build() {
            // TODO validation
            return new PistonExecutionRequestDTO(
                    this.language,
                    this.version,
                    this.files.toArray(PistonExecutionFile[]::new),
                    this.stdin,
                    this.args,
                    this.compileTimeout,
                    this.runTimeout,
                    this.compileMemoryLimit,
                    this.runMemoryLimit
            );
        }

        public Builder language(String language) {
            this.language = language;
            return this;
        }

        public Builder version(String version) {
            this.version = version;
            return this;
        }

        public Builder file(String fileName, @NonNull String content) {
            PistonExecutionFile file = new PistonExecutionFile(fileName, content);
            this.files.add(file);
            return this;
        }

        public Builder file(@NonNull String content) {
            return file(null, content);
        }

        public Builder stdin(String stdin) {
            this.stdin = stdin;
            return this;
        }

        public Builder args(String... args) {
            this.args = args;
            return this;
        }

        public Builder compileTimeout(int compileTimeout) {
            this.compileTimeout = compileTimeout;
            return this;
        }

        public Builder runTimeout(int runTimeout) {
            this.runTimeout = runTimeout;
            return this;
        }

        public Builder compileMemoryLimit(int compileMemoryLimit) {
            this.compileMemoryLimit = compileMemoryLimit;
            return this;

        }

        public Builder runMemoryLimit(int runMemoryLimit) {
            this.runMemoryLimit = runMemoryLimit;
            return this;
        }

    }

    public static record PistonExecutionFile(
            String name,
            @NonNull String content
    ) {
    }


}
