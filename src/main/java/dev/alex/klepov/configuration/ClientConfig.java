package dev.alex.klepov.configuration;

import java.net.http.HttpClient;
import java.time.Duration;

import dev.alex.klepov.client.OnlinesimApiClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Bean
    public OnlinesimApiClient onlinesimApiClient(
            HttpClient onlinesimHttpClient,
            @Value("${onlinesim.data.manager.client.response.timeout}") String responseTimeout) {
        return new OnlinesimApiClient(onlinesimHttpClient, "https://onlinesim.ru", Integer.parseInt(responseTimeout));
    }

    @Bean
    public HttpClient onlinesimHttpClient(
            @Value("${onlinesim.data.manager.client.timeout.connection}") String connectionTimeout) {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(Integer.parseInt(connectionTimeout)))
                .build();
    }
}
