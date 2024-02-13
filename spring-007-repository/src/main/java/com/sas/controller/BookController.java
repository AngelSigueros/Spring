package com.sas.controller;

import com.sas.model.Book;
import com.sas.model.Status;
import com.sas.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private final BookRepository repoBook = null;

    @GetMapping
    public List<Book> findAll () {

        return repoBook.findAll();
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

        return repoBook.findByReleaseDateBefore(LocalDate.of(2000, 12, 31));
    }

    @GetMapping("/fecha-entre")
    public List<Book> findDateEntre () {

        return repoBook.findByReleaseDateBetween(LocalDate.of(1990, 1, 1), LocalDate.of(2000, 12, 31));
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
}
