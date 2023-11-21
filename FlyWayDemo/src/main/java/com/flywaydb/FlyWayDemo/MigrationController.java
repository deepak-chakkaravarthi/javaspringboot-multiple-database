package com.flywaydb.FlyWayDemo;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.Map;

@RestController
@RequestMapping("/migrate")
public class MigrationController {

    @Autowired
    private Map<String, DataSource> dataSources; // Autowire all configured DataSources

    @GetMapping("/sample")
    public String migrateSampleDatabase() {
        for (Map.Entry<String, DataSource> dataSourceEntry : dataSources.entrySet()) {
            String dataSourceName = dataSourceEntry.getKey();
            DataSource dataSource = dataSourceEntry.getValue();

            // Check if the data source URL starts with the desired pattern
            if (dataSourceName.startsWith("jdbc:mysql://localhost:9998/")) {
                // Get the database name from the URL (assuming it follows the pattern)
                String[] urlParts = dataSourceName.split("/");
                if (urlParts.length >= 4) {
                    String dbName = urlParts[3];

                    // Configure Flyway to use the current data source and the migration location
                    Flyway flyway = Flyway.configure()
                            .dataSource(dataSource)
                            .locations("classpath:db/migration/sample_migration")
                            .schemas(dbName) // Set the schema to the current database
                            .load();

                    // Trigger Flyway migration for the current data source
                    flyway.migrate();

                    System.out.println("Migration completed for database: " + dbName);
                }
            }
        }

        return "Migration completed for all databases matching the pattern";
    }
}
