package com.sas.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Book {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String title;
    private Integer numPages;
    private Double price;
    private Boolean available;

    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDate releaseDate;


}
