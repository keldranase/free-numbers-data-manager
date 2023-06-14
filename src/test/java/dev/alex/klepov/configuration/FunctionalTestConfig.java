package dev.alex.klepov.configuration;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.PropertySource;

@TestConfiguration
@PropertySource("classpath:functional.test.properties")
public class FunctionalTestConfig {

}