package me.kecker.discodegame.core.domain.executor;

public record TestCaseExecutionRequest(
        String stdin,
        String language,
        String sourceCode
        ) {
}
