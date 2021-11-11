package me.kecker.discodegame.piston.dtos;


import java.util.Objects;

public final class PistonExecutionResponseDTO {
    private String language;
    private String version;
    private ExecutionRun run;
    private ExecutionRun compile;

    public PistonExecutionResponseDTO(
            String language,
            String version,
            ExecutionRun run,
            ExecutionRun compile) {
        this.language = language;
        this.version = version;
        this.run = run;
        this.compile = compile;
    }

    public String language() {
        return language;
    }

    public String version() {
        return version;
    }

    public ExecutionRun run() {
        return run;
    }

    public ExecutionRun compile() {
        return compile;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (PistonExecutionResponseDTO) obj;
        return Objects.equals(this.language, that.language) &&
                Objects.equals(this.version, that.version) &&
                Objects.equals(this.run, that.run) &&
                Objects.equals(this.compile, that.compile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, version, run, compile);
    }

    @Override
    public String toString() {
        return "PistonExecutionResponseDTO[" +
                "language=" + language + ", " +
                "version=" + version + ", " +
                "run=" + run + ", " +
                "compile=" + compile + ']';
    }


    public static final class ExecutionRun {
        private String stdout;
        private String stderr;
        private String output;
        private int code;
        private String signal;

        public ExecutionRun(
                String stdout,
                String stderr,
                String output,
                int code,
                String signal) {
            this.stdout = stdout;
            this.stderr = stderr;
            this.output = output;
            this.code = code;
            this.signal = signal;
        }

        public String stdout() {
            return stdout;
        }

        public String stderr() {
            return stderr;
        }

        public String output() {
            return output;
        }

        public int code() {
            return code;
        }

        public String signal() {
            return signal;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (ExecutionRun) obj;
            return Objects.equals(this.stdout, that.stdout) &&
                    Objects.equals(this.stderr, that.stderr) &&
                    Objects.equals(this.output, that.output) &&
                    this.code == that.code &&
                    Objects.equals(this.signal, that.signal);
        }

        @Override
        public int hashCode() {
            return Objects.hash(stdout, stderr, output, code, signal);
        }

        @Override
        public String toString() {
            return "ExecutionRun[" +
                    "stdout=" + stdout + ", " +
                    "stderr=" + stderr + ", " +
                    "output=" + output + ", " +
                    "code=" + code + ", " +
                    "signal=" + signal + ']';
        }

    }
}
