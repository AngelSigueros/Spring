package com.sas.sm_app.rest;

import com.sas.sm_app.model.LikesDTO;
import com.sas.sm_app.service.LikesService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/likess", produces = MediaType.APPLICATION_JSON_VALUE)
public class LikesResource {

    private final LikesService likesService;

    public LikesResource(final LikesService likesService) {
        this.likesService = likesService;
    }

    @GetMapping
    public ResponseEntity<List<LikesDTO>> getAllLikess() {
        return ResponseEntity.ok(likesService.findAll());
    }

    @GetMapping("/{idLike}")
    public ResponseEntity<LikesDTO> getLikes(@PathVariable(name = "idLike") final Integer idLike) {
        return ResponseEntity.ok(likesService.get(idLike));
    }

    @PostMapping
    public ResponseEntity<Integer> createLikes(@RequestBody @Valid final LikesDTO likesDTO) {
        final Integer createdIdLike = likesService.create(likesDTO);
        return new ResponseEntity<>(createdIdLike, HttpStatus.CREATED);
    }

    @PutMapping("/{idLike}")
    public ResponseEntity<Integer> updateLikes(@PathVariable(name = "idLike") final Integer idLike,
            @RequestBody @Valid final LikesDTO likesDTO) {
        likesService.update(idLike, likesDTO);
        return ResponseEntity.ok(idLike);
    }

    @DeleteMapping("/{idLike}")
    public ResponseEntity<Void> deleteLikes(@PathVariable(name = "idLike") final Integer idLike) {
        likesService.delete(idLike);
        return ResponseEntity.noContent().build();
    }

}
