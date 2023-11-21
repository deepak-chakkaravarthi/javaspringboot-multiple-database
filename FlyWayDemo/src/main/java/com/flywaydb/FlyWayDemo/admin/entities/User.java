package com.flywaydb.FlyWayDemo.admin.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    private String firstname;

    private String lastname;

    private String email;

    private String password;

    private String database_name;

    private Short status;


}
