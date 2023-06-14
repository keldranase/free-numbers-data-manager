package dev.alex.klepov.configuration;

import dev.alex.klepov.client.OnlinesimApiClient;
import dev.alex.klepov.persistence.FreeNumberDao;
import dev.alex.klepov.service.FreeSimsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    @Bean
    public FreeSimsService freeSimsService(OnlinesimApiClient onlinesimApiClient, FreeNumberDao freeNumberDao) {
        return new FreeSimsService(onlinesimApiClient, freeNumberDao);
    }
}
