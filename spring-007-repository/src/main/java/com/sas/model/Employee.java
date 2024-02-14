package com.sas.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    private String email;
    private Double salary;

    @ManyToOne
    private Company company;

    @ManyToMany
    @ToString.Exclude
    private List<Project> projects = new ArrayList<>();
}
