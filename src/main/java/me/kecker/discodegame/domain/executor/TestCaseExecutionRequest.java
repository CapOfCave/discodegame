package me.kecker.discodegame.domain.executor;

public record TestCaseExecutionRequest(
        String stdin,
        String language,
        String sourceCode
        ) {
}
