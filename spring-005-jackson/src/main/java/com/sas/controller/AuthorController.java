package com.sas.controller;

import com.sas.model.Author;
import com.sas.model.Book;
import lombok.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authors")
@Builder
public class AuthorController {

    private final Logger log = LoggerFactory.getLogger(AuthorController.class);

    @GetMapping
    public Author findAll() {

        log.info("findAll called");
        log.debug("findAll called");
        log.warn("findAll called");
        log.error("findAll called");

        Book book1 = new Book();
        book1.setId(1L);
        book1.setName("Book 1");
        book1.setAuthor(Author.builder().id(1L).firstName("John").lastName("Doe").email("j@j.com").build());
        return Author.builder().id(1L).firstName("John").lastName("Doe").email("j@j.com").books(List.of(book1)).build();
    }
}
