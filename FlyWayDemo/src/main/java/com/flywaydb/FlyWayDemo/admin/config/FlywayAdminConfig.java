package com.flywaydb.FlyWayDemo.admin.config;

import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.output.MigrateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayAdminConfig {

//    @Autowired
    @Bean(name = "admin_database")
    public MigrateResult flywayAdmin(@Qualifier("adminSecondDatabase") DataSource secondDataSource) {
        return Flyway.configure()
                .dataSource(secondDataSource)
                .locations("classpath:db/migration/admin_migration")  // Specify the location without 'classpath:'
                .baselineOnMigrate(true)
                .validateOnMigrate(false)
                .encoding("UTF-8")
                .outOfOrder(true)
                .load()
                .migrate();
    }
}
