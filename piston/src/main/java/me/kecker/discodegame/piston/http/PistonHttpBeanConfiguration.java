package me.kecker.discodegame.piston.http;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;

@Configuration
public class PistonHttpBeanConfiguration {

    public static final String PISTON_HTTP_CLIENT = "pistonHttpClient";
    public static final String PISTON_GSON = "pistonGson";


    @Bean(PISTON_HTTP_CLIENT)
    public HttpClient getHttpClient() {
        return HttpClient.newBuilder().build();

    }

    @Bean(PISTON_GSON)
    public Gson getGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }
}
