package com.sas.repository;

import com.sas.model.Book;
import com.sas.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository // Opcional
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b where b.releaseDate < ?1")
    List<Book> findByReleaseDateBefore(LocalDate releaseDate);

    List<Book> findByNumPagesLessThan(Integer numPages);


    List<Book> findByNumPagesBetween(Integer numPagesStart, Integer numPagesEnd);

    List<Book> findByTitleContains(String title);

    List<Book> findByAvailableTrue();

    List<Book> findByStatus(Status status);

    List<Book> findByReleaseDateBetween(LocalDate releaseDateStart, LocalDate releaseDateEnd);

    List<Book> findByNumPagesLessThanAndPriceGreaterThanAndAvailableTrue(Integer numPages, Double price);


}