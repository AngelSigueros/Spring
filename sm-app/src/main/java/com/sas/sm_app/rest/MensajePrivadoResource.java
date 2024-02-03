package com.sas.sm_app.rest;

import com.sas.sm_app.model.MensajePrivadoDTO;
import com.sas.sm_app.service.MensajePrivadoService;
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
@RequestMapping(value = "/api/mensajePrivados", produces = MediaType.APPLICATION_JSON_VALUE)
public class MensajePrivadoResource {

    private final MensajePrivadoService mensajePrivadoService;

    public MensajePrivadoResource(final MensajePrivadoService mensajePrivadoService) {
        this.mensajePrivadoService = mensajePrivadoService;
    }

    @GetMapping
    public ResponseEntity<List<MensajePrivadoDTO>> getAllMensajePrivados() {
        return ResponseEntity.ok(mensajePrivadoService.findAll());
    }

    @GetMapping("/{idMensaje}")
    public ResponseEntity<MensajePrivadoDTO> getMensajePrivado(
            @PathVariable(name = "idMensaje") final Integer idMensaje) {
        return ResponseEntity.ok(mensajePrivadoService.get(idMensaje));
    }

    @PostMapping
    public ResponseEntity<Integer> createMensajePrivado(
            @RequestBody @Valid final MensajePrivadoDTO mensajePrivadoDTO) {
        final Integer createdIdMensaje = mensajePrivadoService.create(mensajePrivadoDTO);
        return new ResponseEntity<>(createdIdMensaje, HttpStatus.CREATED);
    }

    @PutMapping("/{idMensaje}")
    public ResponseEntity<Integer> updateMensajePrivado(
            @PathVariable(name = "idMensaje") final Integer idMensaje,
            @RequestBody @Valid final MensajePrivadoDTO mensajePrivadoDTO) {
        mensajePrivadoService.update(idMensaje, mensajePrivadoDTO);
        return ResponseEntity.ok(idMensaje);
    }

    @DeleteMapping("/{idMensaje}")
    public ResponseEntity<Void> deleteMensajePrivado(
            @PathVariable(name = "idMensaje") final Integer idMensaje) {
        mensajePrivadoService.delete(idMensaje);
        return ResponseEntity.noContent().build();
    }

}
