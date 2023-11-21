package com.flywaydb.FlyWayDemo.admin.repo;

import com.flywaydb.FlyWayDemo.admin.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepo extends JpaRepository<User,Integer> {
    List<User> findByStatus(Short status);

}
