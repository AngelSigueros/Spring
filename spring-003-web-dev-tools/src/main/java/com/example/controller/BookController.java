package com.example.controller;

import com.example.model.Author;
import com.example.model.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class BookController {

    //@Autowired
    //private BookService bookService;

    List<Book> books;

    public BookController() {
        books = new ArrayList<>();
        books.add(new Book(1L, "Titulo 1", 38.75, true, new Author(1L,"Manuel")));
        books.add(new Book(2L, "Titulo 2", 45.99, false, new Author(1L, "Manuel")));
        books.add(new Book(3L, "Titulo 3", 65.25, true, new Author(3L, "Luisa")));
        books.add(new Book(4L, "Titulo 4", 41.15, false, new Author(4L, "Maria")));

    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> findAll() {
        //return this.bookService.findAll();
        return ResponseEntity.ok(this.books);

    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> findById(@PathVariable Long id) {
        // return this.bookService.findById();

        /* Buscando directamente en la lista
        for (Book book : this.books) {
            if (book.id().equals(id)) {
                return ResponseEntity.ok(book);
            }
        }
        return ResponseEntity.notFound().build();
        */

        // Usando streams
        //return ResponseEntity.ok(this.books.stream().filter((book -> book.id().equals(id))).findFirst());
        // Usando streams
        return this.books.stream()
                .filter(book -> book.id().equals(id))
                .findFirst().map(book -> ResponseEntity.ok(book))
                .orElse(ResponseEntity.notFound().build());

        //return ResponseEntity.ok(this.books.getFirst());

    }

    @GetMapping("/books/by-author-id/{id}")
    public ResponseEntity<List<Book>> findByAuthorId(@PathVariable Long id) {
        // return this.bookService.findById();

        // Buscando directamente en la lista

        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : this.books) {
            if (book.author().id().equals(id)) {
                filteredBooks.add(book);
            }
        }

        if (filteredBooks.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(filteredBooks);


        //return ResponseEntity.ok(this.books.getFirst());

    }

    @PostMapping("books")
    public ResponseEntity<Book> create(@RequestBody Book book) {

        if (book.id() == null)
            return ResponseEntity.notFound().build();

        this.books.add(book);

        return ResponseEntity.ok(book);
    }

}
