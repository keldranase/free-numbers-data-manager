package dev.alex.klepov.configuration;

import dev.alex.klepov.controller.FreeNumbersInfoController;
import dev.alex.klepov.service.FreeNumberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {

    @Bean
    public FreeNumbersInfoController freeNumbersInfoController(FreeNumberService freeNumberService) {
        return new FreeNumbersInfoController(freeNumberService);
    }
}
