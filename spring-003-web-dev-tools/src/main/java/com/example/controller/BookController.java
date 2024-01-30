package com.example.controller;

import com.example.model.Author;
import com.example.model.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


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
        return this.books.stream()
                .filter(book -> book.id().equals(id))
                .findFirst().map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

        //return ResponseEntity.ok(this.books.getFirst());

    }

    @GetMapping("/books/by-author-id/{id}")
    public ResponseEntity<List<Book>> findByAuthorId(@PathVariable Long id) {
        // return this.bookService.findById();

        /* Buscando directamente en la lista

        List<Book> filteredBooks = new ArrayList<>();
        for (Book book : this.books) {
            if (book.author().id().equals(id)) {
                filteredBooks.add(book);
            }
        }
        */

        // Usando streams
        List<Book> filteredBooks = this.books.stream()
                .filter(book -> book.author().id().equals(id))
                .collect(Collectors.toList()); //.toList();

        if (filteredBooks.isEmpty())
            return ResponseEntity.notFound().build();

        return ResponseEntity.ok(filteredBooks);

    }

    @PostMapping("books")
    public ResponseEntity<Book> create(@RequestBody Book book) {

        if (book.id() == null)
            return ResponseEntity.notFound().build();

        this.books.add(book);

        return ResponseEntity.ok(book);
    }

    @PutMapping("books/{id}")
    public ResponseEntity<Book> update(@PathVariable Long id, @RequestBody Book book) {

        if (id==null || book.id() == null)
            return ResponseEntity.badRequest().build();

        int index = -1;

        /* con forEach
        for (Book currentBook : this.books) {
            if (currentBook.id().equals(id))
                index = this.books.indexOf(currentBook);
        }
        */

        // con for
        for (int i = 0; i < this.books.size(); i++) {
            if (this.books.get(i).id().equals(id))
                index = i;
        }

        if (index < 0)
            return ResponseEntity.notFound().build();

        this.books.set(index, book);

        return ResponseEntity.ok(book);

        /* con stream
        Book book1 = this.books.stream().filter(b -> b.id().equals(id)).findFirst().orElse(null);
        if (book1 == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(book);
        */

    }

    @DeleteMapping("books/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        int index = -1;
        for (Book book : this.books) {
            if (book.id().equals(id))
                index = this.books.indexOf(book);
        }

        if (index == -1)
            return ResponseEntity.notFound().build();

        this.books.remove(index);

        return ResponseEntity.noContent().build(); // HttpStatus 204 eliminado correctamente
    }

    @DeleteMapping("books")
    public ResponseEntity<Void> deleteAll() {

        if (!this.books.isEmpty())
            this.books.clear();
        return ResponseEntity.noContent().build();

    }
}
