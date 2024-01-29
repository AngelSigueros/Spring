package com.example.service;

import com.example.model.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();


    Book findById();
}
