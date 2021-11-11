package me.kecker.discodegame.core.application.api;

import lombok.NonNull;
import me.kecker.discodegame.core.domain.exceptions.UnsupportedLanguageException;
import me.kecker.discodegame.core.domain.executor.TestCaseExecutionRequest;
import me.kecker.discodegame.core.domain.executor.TestCaseExecutionResult;

public interface TestCaseExecutor {
    @NonNull TestCaseExecutionResult execute(@NonNull TestCaseExecutionRequest request) throws UnsupportedLanguageException;
}
