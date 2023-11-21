package com.flywaydb.FlyWayDemo;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name ="driver")
@EntityScan
@ComponentScan
public class driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "token")
    private String token;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBageid() {
        return bageid;
    }

    public void setBageid(String bageid) {
        this.bageid = bageid;
    }

    public Date getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Date deleted_at) {
        this.deleted_at = deleted_at;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Column(name = "bageid")
    private String bageid;




    @Column(name ="deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleted_at;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;
}
