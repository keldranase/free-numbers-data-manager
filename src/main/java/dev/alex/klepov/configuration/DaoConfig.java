package dev.alex.klepov.configuration;

import dev.alex.klepov.persistence.FreeNumberDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DaoConfig {

    @Bean
    public FreeNumberDao freeNumberDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new FreeNumberDao(namedParameterJdbcTemplate);
    }
}
