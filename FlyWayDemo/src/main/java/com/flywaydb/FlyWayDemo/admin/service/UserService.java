package com.flywaydb.FlyWayDemo.admin.service;

import com.flywaydb.FlyWayDemo.admin.entities.User;
import com.flywaydb.FlyWayDemo.admin.repo.UserRepo;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final JdbcTemplate jdbcTemplate;
    private final Flyway flyway;

    private List<String> childDatabases = new ArrayList<>();
//    private boolean isInitialMigrationComplete = false;

    @Autowired
    public UserService(UserRepo userRepo, JdbcTemplate jdbcTemplate, Flyway flyway) {
        this.userRepo = userRepo;
        this.jdbcTemplate = jdbcTemplate;
        this.flyway = flyway;
    }

    public User createUser(User user) {
        // You can perform validation and additional logic here
        return userRepo.save(user);
    }

    public void updateStatus(int userId, short status) {
        User user = userRepo.findById(userId).orElse(null);
        if (user != null) {
            user.setStatus(status);
            userRepo.save(user);

            // Handle logic for creating a child database and migrating tables if status is 3
            if (status == 3) {
                createChildDatabase(user.getDatabase_name());
                migrateTablesToChildDatabase(user.getDatabase_name());
            }
        }
    }

    public void createChildDatabase(String databaseName) {
        // Assuming you have appropriate privileges to create a database
        String createDatabaseQuery = "CREATE DATABASE " + databaseName;
        jdbcTemplate.execute(createDatabaseQuery);
    }

    public void migrateTablesToChildDatabase(String databaseName) {
        // Configure Flyway for the child database
        Flyway childFlyway = Flyway.configure()
                .dataSource(jdbcTemplate.getDataSource())  // Use the same data source as the main database
                .locations("classpath:db/migration/sample_migration") // Specify the location for child database migrations
                .baselineOnMigrate(true)
                .validateOnMigrate(false)
                .encoding("UTF-8")
                .outOfOrder(true)
                .schemas(databaseName) // Set the schema to the child database name
                .load();

        // Migrate the child database
        childFlyway.migrate();
    }

    public User updateUser(User user) {
        return userRepo.save(user);
    }
    public User getUserById(int userId) {
        return userRepo.findById(userId).orElse(null);
    }

    public List<String> getDatabasesWithStatus3() {
        List<User> usersWithStatus3 = userRepo.findByStatus((short) 3);
        return usersWithStatus3.stream()
                .map(User::getDatabase_name)
                .collect(Collectors.toList());
    }



    public void generateMigrationFilesForDatabasesWithStatus3() {
        List<String> databasesWithStatus3 = getDatabasesWithStatus3();

        for (String databaseName : databasesWithStatus3) {
            // Set the schema (database) name for Flyway
            Flyway childFlyway = Flyway.configure()
          .dataSource(jdbcTemplate.getDataSource())  // Use the same data source as the main database
                    .locations("classpath:db/migration/sample_migration") // Specify the location for child database migrations
                    .baselineOnMigrate(true)
                    .validateOnMigrate(false)
                    .encoding("UTF-8")
                    .outOfOrder(true)
                    .schemas(databaseName) // Set the schema to the child database name
                    .load();


            // Start the migration
            childFlyway.migrate();
        }
    }

//    public void generateMigrationFilesForDatabase(String databaseName) {
//        String migrationLocation = "classpath:db/migration/sample_migration_for_" + databaseName;
//
//        // Generate migration files in the specified location
//        // You can use Flyway or any other migration tool to create migration files
//    }

}




