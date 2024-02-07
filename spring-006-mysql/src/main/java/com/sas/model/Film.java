package com.sas.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Films") // anotacion opcional
public class Film {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String title;
    private Integer duration;
    private LocalDate releaseDate;

    @ManyToMany
    @ToString.Exclude
    private List<Category> categories = new ArrayList<>();
}
