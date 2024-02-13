package com.sas.repository;

import com.sas.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Opcional
public interface BookRepository extends JpaRepository<Book, Long> {

}