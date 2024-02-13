package com.sas.controller;

import com.sas.model.Book;
import com.sas.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Book")
public class BookController {

    @Autowired
    private final BookRepository repoBook = null;
    public ResponseEntity<List<Book>> findAll () {

        return (ResponseEntity<List<Book>>) repoBook.findAll();
    }
}
