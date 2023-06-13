package dev.alex.klepov.configuration;

import java.net.http.HttpClient;

import dev.alex.klepov.client.OnlinesimApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Bean
    public OnlinesimApiClient onlinesimApiClient(HttpClient onlinesimHttpClient) {
        return new OnlinesimApiClient(onlinesimHttpClient, "https://onlinesim.ru", 15000);
    }

    @Bean
    public HttpClient onlinesimHttpClient() {
        return HttpClient.newBuilder().build();
    }
}
