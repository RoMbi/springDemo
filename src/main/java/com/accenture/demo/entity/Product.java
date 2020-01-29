package com.accenture.demo.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Digits;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Digits(integer = 6, fraction = 2)
    @Column(nullable = false, columnDefinition = "float(10,2)")
    private Float price;

    private String description;
}
