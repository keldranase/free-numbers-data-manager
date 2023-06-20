package dev.alex.klepov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication()
// this way we can use the RestController annotation, and we can declare it in our own configuration
@ComponentScan(excludeFilters = @ComponentScan.Filter(RestController.class))
public class ApplicationMain {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationMain.class, args);
    }
}