package com.sas.repository;

import com.sas.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Es opcional
public interface AuthorRepository extends JpaRepository<Author, Long> {


}