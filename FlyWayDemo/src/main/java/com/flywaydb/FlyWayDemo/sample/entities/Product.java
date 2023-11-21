package com.flywaydb.FlyWayDemo.sample.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    private String name;

    private String description;

    private boolean live;

    private double price;
}
