package com.flywaydb.FlyWayDemo;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class FlywayGlobalConfig {

    @Bean
    public Flyway flyway(DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration") // Specify the common migration location
                .baselineOnMigrate(true)
                .validateOnMigrate(false)
                .encoding("UTF-8")
                .outOfOrder(true)
                .load();
    }
}

