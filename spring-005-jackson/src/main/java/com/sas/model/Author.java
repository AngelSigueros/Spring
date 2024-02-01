package com.sas.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnoreProperties("author")
    private List<Book> books = new ArrayList<>(); // list of books written by this author
}
