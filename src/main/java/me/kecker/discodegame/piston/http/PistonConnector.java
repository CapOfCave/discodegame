package me.kecker.discodegame.piston.http;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import me.kecker.discodegame.piston.exceptions.PistonRuntimeException;
import me.kecker.discodegame.piston.dtos.PistonExecutionResponseDTO;
import me.kecker.discodegame.piston.dtos.PistonExecutionRequestDTO;
import me.kecker.discodegame.piston.dtos.PistonRuntimeResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static me.kecker.discodegame.piston.http.PistonHttpBeanConfiguration.PISTON_HTTP_CLIENT;

@Component
public class PistonConnector {

    private final String baseUrl;
    private final HttpClient httpClient;
    private final Gson gson;

    @Autowired
    public PistonConnector(
            @Value("${dcg.piston.baseUrl}") String baseUrl,
            @Qualifier(PISTON_HTTP_CLIENT) HttpClient httpClient,
            Gson gson) {
        this.httpClient = httpClient;
        this.baseUrl = baseUrl;
        this.gson = gson;
    }

    @SneakyThrows
    public PistonRuntimeResponseDTO[] getRuntimes() {
        HttpRequest request = HttpRequest.newBuilder()
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .uri(URI.create(this.baseUrl + "/runtimes"))
                .build();

        HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return this.gson.fromJson(response.body(), PistonRuntimeResponseDTO[].class);
    }


    public PistonExecutionResponseDTO execute(PistonExecutionRequestDTO request) {
        return execute(this.gson.toJson(request));
    }

    public PistonExecutionResponseDTO execute(String body) {
        HttpRequest request = HttpRequest.newBuilder()
                .method("POST", HttpRequest.BodyPublishers.ofString(body))
                .uri(URI.create(this.baseUrl + "/execute"))
                .build();

        try {
            HttpResponse<String> response = this.httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return this.gson.fromJson(response.body(), PistonExecutionResponseDTO.class);
        } catch (IOException | InterruptedException e) {
            throw new PistonRuntimeException(e);
        }

    }
}
