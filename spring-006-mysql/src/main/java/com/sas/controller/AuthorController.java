package com.sas.controller;

import com.sas.model.Author;
import com.sas.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
public class AuthorController {

    private final AuthorRepository authorRepository;

    @GetMapping("/authors")
    public List<Author> findAll() {

        return authorRepository.findAll();
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<Author> findById(@PathVariable Long id) {

        Optional<Author> authoreOpt = authorRepository.findById(id);

        return authoreOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

        /* Otra forma
        Optional<Author> authoreOpt = authorRepository.findById(id);
        if (authoreOpt.isPresent())
            return ResponseEntity.ok(authoreOpt.get());
        return ResponseEntity.notFound().build();
         */

        // Otra forma
        //if (!authorRepository.existsById(id))
        //    return ResponseEntity.notFound().build();
        //return ResponseEntity.ok(authorRepository.findById(id).get());
    }

    @PostMapping("/authors")
    public ResponseEntity<Author> create(@RequestBody Author author) {
        return ResponseEntity.ok(authorRepository.save(author));
    }

    @PutMapping("/authors/{id}")
    public ResponseEntity<Author> update(@PathVariable Long id, @RequestBody Author author) {
        if (authorRepository.existsById(id))
            return ResponseEntity.ok(authorRepository.save(author));
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/authors/{id}")
    public void delete(@PathVariable Long id) {
        if (authorRepository.existsById(id))
            authorRepository.deleteById(id);
    }

    /*
    @GetMapping("/authors/{id}/name")
    public String findNameById(@PathVariable Long id) {
        return authorRepository.findById(id).map(Author::getName).orElse(null);
    }

    @GetMapping("/authors/{name}")
    public List<Author> findByName(@PathVariable String name) {
        return authorRepository.findByName(name);
    }

    @GetMapping("/authors/{email}")
    public List<Author> findByEmail(@PathVariable String email) {
        return authorRepository.findByEmail(email);
    }

    @GetMapping("/authors/{salary}")
    public List<Author> findBySalary(@PathVariable Double salary) {
        return authorRepository.findBySalary(salary);
    }

    @GetMapping("/authors/{available}")
    public List<Author> findByAvailable(@PathVariable Boolean available) {
        return authorRepository.findByAvailable(available);
    }

    @GetMapping("/authors/{fechaNacimiento}")
    public List<Author> findByFechaNacimiento(@PathVariable LocalDate fechaNacimiento) {
        return authorRepository.findByFechaNacimiento(fechaNacimiento);
    }

    @GetMapping("/authors/{name}/{email}")
    public List<Author> findByNameAndEmail(@PathVariable String name, @PathVariable String email) {
        return authorRepository.findByNameAndEmail(name, email);
    }
*/

}
