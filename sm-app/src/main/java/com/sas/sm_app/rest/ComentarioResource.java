package com.sas.sm_app.rest;

import com.sas.sm_app.model.ComentarioDTO;
import com.sas.sm_app.service.ComentarioService;
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
@RequestMapping(value = "/api/comentarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class ComentarioResource {

    private final ComentarioService comentarioService;

    public ComentarioResource(final ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    @GetMapping
    public ResponseEntity<List<ComentarioDTO>> getAllComentarios() {
        return ResponseEntity.ok(comentarioService.findAll());
    }

    @GetMapping("/{idComment}")
    public ResponseEntity<ComentarioDTO> getComentario(
            @PathVariable(name = "idComment") final Integer idComment) {
        return ResponseEntity.ok(comentarioService.get(idComment));
    }

    @PostMapping
    public ResponseEntity<Integer> createComentario(
            @RequestBody @Valid final ComentarioDTO comentarioDTO) {
        final Integer createdIdComment = comentarioService.create(comentarioDTO);
        return new ResponseEntity<>(createdIdComment, HttpStatus.CREATED);
    }

    @PutMapping("/{idComment}")
    public ResponseEntity<Integer> updateComentario(
            @PathVariable(name = "idComment") final Integer idComment,
            @RequestBody @Valid final ComentarioDTO comentarioDTO) {
        comentarioService.update(idComment, comentarioDTO);
        return ResponseEntity.ok(idComment);
    }

    @DeleteMapping("/{idComment}")
    public ResponseEntity<Void> deleteComentario(
            @PathVariable(name = "idComment") final Integer idComment) {
        comentarioService.delete(idComment);
        return ResponseEntity.noContent().build();
    }

}
