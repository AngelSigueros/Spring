package com.sas.sm_app.rest;

import com.sas.sm_app.model.PublicacionDTO;
import com.sas.sm_app.service.PublicacionService;
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
@RequestMapping(value = "/api/publicacions", produces = MediaType.APPLICATION_JSON_VALUE)
public class PublicacionResource {

    private final PublicacionService publicacionService;

    public PublicacionResource(final PublicacionService publicacionService) {
        this.publicacionService = publicacionService;
    }

    @GetMapping
    public ResponseEntity<List<PublicacionDTO>> getAllPublicacions() {
        return ResponseEntity.ok(publicacionService.findAll());
    }

    @GetMapping("/{idPost}")
    public ResponseEntity<PublicacionDTO> getPublicacion(
            @PathVariable(name = "idPost") final Integer idPost) {
        return ResponseEntity.ok(publicacionService.get(idPost));
    }

    @PostMapping
    public ResponseEntity<Integer> createPublicacion(
            @RequestBody @Valid final PublicacionDTO publicacionDTO) {
        final Integer createdIdPost = publicacionService.create(publicacionDTO);
        return new ResponseEntity<>(createdIdPost, HttpStatus.CREATED);
    }

    @PutMapping("/{idPost}")
    public ResponseEntity<Integer> updatePublicacion(
            @PathVariable(name = "idPost") final Integer idPost,
            @RequestBody @Valid final PublicacionDTO publicacionDTO) {
        publicacionService.update(idPost, publicacionDTO);
        return ResponseEntity.ok(idPost);
    }

    @DeleteMapping("/{idPost}")
    public ResponseEntity<Void> deletePublicacion(
            @PathVariable(name = "idPost") final Integer idPost) {
        publicacionService.delete(idPost);
        return ResponseEntity.noContent().build();
    }

}
