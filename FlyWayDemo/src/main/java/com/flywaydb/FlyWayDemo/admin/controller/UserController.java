package com.flywaydb.FlyWayDemo.admin.controller;

import com.flywaydb.FlyWayDemo.admin.entities.User;
import com.flywaydb.FlyWayDemo.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private DataSource dataSource;
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody Map<String, String> userData) {
        User user = mapToUser(userData);
        User createdUser = userService.createUser(user);

        if (user.getStatus() == 3) {
            // Handle status 3 logic here if needed
        }

        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<String> updateStatus(@RequestBody Map<String, Object> requestData) {
        Integer userId = Integer.parseInt((String) requestData.get("userId"));
        Integer status = Integer.parseInt((String) requestData.get("status"));

        if (userId != null && status != null) {
            User user = userService.getUserById(userId);

            if (user != null) {
                user.setStatus(status.shortValue());
                userService.updateUser(user);

                String databaseName = user.getDatabase_name();

                // Check if the database already exists
                boolean databaseExists = checkIfDatabaseExists(databaseName);

                if (!databaseExists) {
                    // If the database doesn't exist, create it
                    userService.createChildDatabase(databaseName);
                }

                // Trigger migration for the specified database
                userService.migrateTablesToChildDatabase(databaseName);

                return new ResponseEntity<>("Status updated successfully, and database migrated", HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Invalid input data", HttpStatus.BAD_REQUEST);
    }

    public boolean checkIfDatabaseExists(String databaseName) {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getCatalogs();

            while (resultSet.next()) {
                String existingDatabaseName = resultSet.getString(1);
                if (existingDatabaseName.equals(databaseName)) {
                    return true;
                }
            }

            return false;
        } catch (SQLException e) {
            // Handle any exceptions that may occur during the process.
            e.printStackTrace();
            return false;
        }
    }


    private User mapToUser(Map<String, String> userData) {
        User user = new User();
        user.setFirstname(userData.get("firstname"));
        user.setLastname(userData.get("lastname"));
        user.setEmail(userData.get("email"));
        user.setDatabase_name(userData.get("database_name"));
        user.setPassword(userData.get("password"));
//        user.setStatus(Short.valueOf(userData.get("status")));
        user.setStatus((short) 1);
        return user;
    }


    @PostMapping("/generateMigrationFiles")
    public ResponseEntity<String> generateMigrationFilesForDatabasesWithStatus3() {
        userService.generateMigrationFilesForDatabasesWithStatus3();
        return new ResponseEntity<>("Migration files generated for databases with status 3", HttpStatus.OK);
    }


}


