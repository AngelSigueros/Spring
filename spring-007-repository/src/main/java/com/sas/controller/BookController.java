package com.sas.controller;

import com.sas.model.Book;
import com.sas.model.Status;
import com.sas.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private final BookRepository repoBook = null;

    @GetMapping
    public List<Book> findAll () {

        return repoBook.findAll();
    }

    @GetMapping("/{id}")
    public Book findById (@PathVariable Long id) {

        return repoBook.findById(id).get();
    }

    @PostMapping
    public Book save (@RequestBody Book book) {

        return repoBook.save(book);
    }

    @PostMapping("/login")
    public void login (@RequestBody Book book) {
        System.out.println("Loging!!!");
        repoBook.save(book);
    }

    @DeleteMapping("/{id}")
    public void deleteById (@PathVariable Long id) {

        repoBook.deleteById(id);
    }


    @GetMapping("/entre")
    public List<Book> findEntre () {

        return repoBook.findByNumPagesBetween(400, 700);
    }

    @GetMapping("/menos-de")
    public List<Book> findMenosDe () {

        return repoBook.findByNumPagesLessThan(400);
    }

    @GetMapping("/antes-de")
    public List<Book> findDateAntesDe () {

        return repoBook.findByPublishDateBefore(LocalDate.of(2000, 12, 31));
    }

    @GetMapping("/fecha-entre")
    public List<Book> findDateEntre () {

        return repoBook.findByPublishDateBetween(LocalDate.of(1990, 1, 1), LocalDate.of(2000, 12, 31));
    }


    @GetMapping("/contiene")
    public List<Book> findContiene () {

        return repoBook.findByTitleContains("3");
    }

    @GetMapping("/disponible")
    public List<Book> findDisponible () {

        return repoBook.findByAvailableTrue();
    }

    @GetMapping("/publicado")
    public List<Book> findStatus () {

        return repoBook.findByStatus(Status.PUBLISHED);
    }

    @GetMapping("/codicion")
    public List<Book> findCondition () {

        return repoBook.findByNumPagesLessThanAndPriceGreaterThanAndAvailableTrue(550, 200.0);
    }

    @GetMapping("/first")
    public Book findFirst () {

        return repoBook.findFirstByTitle("Libro");
    }

    @GetMapping("/first-price")
    public Book findFirstPrice () {

        return repoBook.findFirstByPriceOrderByPriceDesc(323.55);
    }

    @GetMapping("/first-price-no")
    public Book findFirstPriceNo () {

        return repoBook.findFirstByPriceNotNullOrderByPriceDesc();
    }

    @GetMapping("/count-status")
    public Long countStatus () {

        return repoBook.countByStatus(Status.PUBLISHED);
    }

    @GetMapping("/existe-antes")
    public Boolean existBefore () {

        return repoBook.existsByPublishDateBefore(LocalDate.of(1970, 12, 31));
    }
}
