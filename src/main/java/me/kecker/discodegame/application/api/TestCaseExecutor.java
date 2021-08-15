package me.kecker.discodegame.application.api;

import lombok.NonNull;
import me.kecker.discodegame.domain.exceptions.UnsupportedLanguageException;
import me.kecker.discodegame.domain.executor.TestCaseExecutionRequest;
import me.kecker.discodegame.domain.executor.TestCaseExecutionResult;

public interface TestCaseExecutor {
    @NonNull TestCaseExecutionResult execute(@NonNull TestCaseExecutionRequest request) throws UnsupportedLanguageException;
}
