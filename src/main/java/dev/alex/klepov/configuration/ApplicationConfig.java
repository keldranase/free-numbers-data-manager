package dev.alex.klepov.configuration;

import dev.alex.klepov.client.OnlinesimApiClient;
import dev.alex.klepov.persistence.FreeCountryRepository;
import dev.alex.klepov.persistence.FreeNumberRepository;
import dev.alex.klepov.service.FreeSimsService;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@Configuration
@Import({
        ClientConfig.class
})
public class ApplicationConfig {

    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    public FreeSimsService freeSimsService(OnlinesimApiClient onlinesimApiClient,
                                           FreeCountryRepository freeCountryRepository,
                                           FreeNumberRepository freeNumberRepository) {

        return new FreeSimsService(onlinesimApiClient, freeCountryRepository, freeNumberRepository);
    }
}
