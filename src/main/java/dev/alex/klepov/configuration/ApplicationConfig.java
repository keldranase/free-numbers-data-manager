package dev.alex.klepov.configuration;

import dev.alex.klepov.controller.FreeNumbersInfoController;
import dev.alex.klepov.service.FreeSimsService;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@Configuration
public class ApplicationConfig {

    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    public FreeNumbersInfoController freeNumbersInfoController(FreeSimsService freeSimsService) {
        return new FreeNumbersInfoController(freeSimsService);
    }
}
