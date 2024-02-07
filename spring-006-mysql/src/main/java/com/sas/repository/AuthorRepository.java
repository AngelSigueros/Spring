package com.sas.repository;

import com.sas.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository // Es opcional
public interface AuthorRepository extends JpaRepository<Author, Long> {


    List<Author> findByName(String name);

    List<Author> findByEmail(String email);

    List<Author> findBySalary(Double salary);

    List<Author> findByAvailable(Boolean available);

    List<Author> findByFechaNacimiento(LocalDate fechaNacimiento);

    List<Author> findByNameAndEmail(String name, String email);
}