package dev.alex.klepov.configuration;

import java.net.http.HttpClient;

import dev.alex.klepov.client.OnlinesimApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class ClientConfig {

    @Bean
    @Profile("!functionalTest")
    public OnlinesimApiClient onlinesimApiClient(HttpClient onlinesimHttpClient) {
        System.out.println("hello onlinesimApiClient");
        return new OnlinesimApiClient(onlinesimHttpClient, "https://onlinesim.ru", 4000);
    }

    @Bean
    public HttpClient onlinesimHttpClient() {
        return HttpClient.newBuilder().build();
    }
}
