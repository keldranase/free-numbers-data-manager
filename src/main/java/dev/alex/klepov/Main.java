package dev.alex.klepov;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@EnableJpaRepositories(
        basePackages = { "dev.alex.klepov.persistence" }/*,
        entityManagerFactoryRef = "gpiEntityManagerFactory"*/)
@EntityScan("dev.alex.klepov.persistence.entity")
//@ComponentScan(basePackages = { "my.package.base.*" })
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}