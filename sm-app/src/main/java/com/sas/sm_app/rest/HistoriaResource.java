package com.sas.sm_app.rest;

import com.sas.sm_app.model.HistoriaDTO;
import com.sas.sm_app.service.HistoriaService;
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
@RequestMapping(value = "/api/historias", produces = MediaType.APPLICATION_JSON_VALUE)
public class HistoriaResource {

    private final HistoriaService historiaService;

    public HistoriaResource(final HistoriaService historiaService) {
        this.historiaService = historiaService;
    }

    @GetMapping
    public ResponseEntity<List<HistoriaDTO>> getAllHistorias() {
        return ResponseEntity.ok(historiaService.findAll());
    }

    @GetMapping("/{idStory}")
    public ResponseEntity<HistoriaDTO> getHistoria(
            @PathVariable(name = "idStory") final Integer idStory) {
        return ResponseEntity.ok(historiaService.get(idStory));
    }

    @PostMapping
    public ResponseEntity<Integer> createHistoria(
            @RequestBody @Valid final HistoriaDTO historiaDTO) {
        final Integer createdIdStory = historiaService.create(historiaDTO);
        return new ResponseEntity<>(createdIdStory, HttpStatus.CREATED);
    }

    @PutMapping("/{idStory}")
    public ResponseEntity<Integer> updateHistoria(
            @PathVariable(name = "idStory") final Integer idStory,
            @RequestBody @Valid final HistoriaDTO historiaDTO) {
        historiaService.update(idStory, historiaDTO);
        return ResponseEntity.ok(idStory);
    }

    @DeleteMapping("/{idStory}")
    public ResponseEntity<Void> deleteHistoria(
            @PathVariable(name = "idStory") final Integer idStory) {
        historiaService.delete(idStory);
        return ResponseEntity.noContent().build();
    }

}
