package com.flywaydb.FlyWayDemo.sample.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywaySampleConfig {

//    @Autowired
    @Bean(name = "sample_database")
    public MigrateResult flywaySample(@Qualifier("sampleDataSource") DataSource secondDataSource) {
        return Flyway.configure()
                .dataSource(secondDataSource)
                .locations("classpath:db/migration/sample_migration")  // Specify the location without 'classpath:'
                .baselineOnMigrate(true)
                .validateOnMigrate(false)
                .encoding("UTF-8")
                .outOfOrder(true)
                .load()
                .migrate();
    }
}
