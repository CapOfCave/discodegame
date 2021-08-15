package me.kecker.discodegame.piston.dtos;


public record PistonExecutionResponseDTO(
        String language,
        String version,
        ExecutionRun run,
        ExecutionRun compile) {

    public record ExecutionRun(
            String stdout,
            String stderr,
            String output,
            int code,
            String signal) {
    }
}
