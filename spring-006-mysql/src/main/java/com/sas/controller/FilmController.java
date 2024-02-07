package com.sas.controller;

import com.sas.model.Film;
import com.sas.repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/film")
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;


    @GetMapping
    public List<Film> findAll() {
        return filmRepository.findAll();
    }

    @GetMapping("/{id}")
    public Film findById(@PathVariable Long id) {
        return filmRepository.findById(id).get();
    }

    @PostMapping
    public Film create(@RequestBody Film film) {
        return filmRepository.save(film);
    }

    @PutMapping("{id}")
    public Film update(@PathVariable Long id, @RequestBody Film film) {
        Optional<Film> filmOpt = filmRepository.findById(id);
        if(filmOpt.isEmpty()) // Si isEmpty significa que no existe y por tanto devolvemos 404 not found
            return null;

        Film filmFromDB = filmOpt.get();

        filmFromDB.setTitle(film.getTitle());
        filmFromDB.setDuration(film.getDuration());
        filmFromDB.setReleaseDate(film.getReleaseDate());

        return filmRepository.save(filmFromDB);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        filmRepository.deleteById(id);
    }




}
