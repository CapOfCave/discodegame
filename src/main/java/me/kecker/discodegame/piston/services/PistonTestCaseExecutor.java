package me.kecker.discodegame.piston.services;

import lombok.NonNull;
import me.kecker.discodegame.application.api.TestCaseExecutor;
import me.kecker.discodegame.domain.exceptions.UnsupportedLanguageException;
import me.kecker.discodegame.domain.executor.TestCaseExecutionRequest;
import me.kecker.discodegame.domain.executor.TestCaseExecutionResult;
import me.kecker.discodegame.piston.dtos.PistonExecutionRequestDTO;
import me.kecker.discodegame.piston.dtos.PistonExecutionResponseDTO;
import me.kecker.discodegame.piston.dtos.PistonRuntimeResponseDTO;
import me.kecker.discodegame.piston.http.PistonConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PistonTestCaseExecutor implements TestCaseExecutor {

    private final PistonRuntimeManager pistonRuntimeManager;
    private final PistonConnector pistonConnector;

    @Autowired
    public PistonTestCaseExecutor(PistonRuntimeManager pistonRuntimeManager, PistonConnector pistonConnector) {
        this.pistonRuntimeManager = pistonRuntimeManager;
        this.pistonConnector = pistonConnector;
    }

    @Override
    @NonNull
    public TestCaseExecutionResult execute(@NonNull TestCaseExecutionRequest request) throws UnsupportedLanguageException {

        PistonRuntimeResponseDTO runtime = this.pistonRuntimeManager
                .getRuntime(request.language())
                .orElseThrow(() -> new UnsupportedLanguageException(request.language()));

        PistonExecutionRequestDTO dto = PistonExecutionRequestDTO.builder(runtime.language(), runtime.version())
                .file(request.sourceCode())
                .stdin(request.stdin())
                .build();

        PistonExecutionResponseDTO executionResponse = this.pistonConnector.execute(dto);

        return new TestCaseExecutionResult(executionResponse.run().stdout(), executionResponse.run().code());
    }
}
