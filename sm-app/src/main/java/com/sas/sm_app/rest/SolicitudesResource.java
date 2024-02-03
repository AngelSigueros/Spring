package com.sas.sm_app.rest;

import com.sas.sm_app.model.SolicitudesDTO;
import com.sas.sm_app.service.SolicitudesService;
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
@RequestMapping(value = "/api/solicitudess", produces = MediaType.APPLICATION_JSON_VALUE)
public class SolicitudesResource {

    private final SolicitudesService solicitudesService;

    public SolicitudesResource(final SolicitudesService solicitudesService) {
        this.solicitudesService = solicitudesService;
    }

    @GetMapping
    public ResponseEntity<List<SolicitudesDTO>> getAllSolicitudess() {
        return ResponseEntity.ok(solicitudesService.findAll());
    }

    @GetMapping("/{idRequest}")
    public ResponseEntity<SolicitudesDTO> getSolicitudes(
            @PathVariable(name = "idRequest") final Integer idRequest) {
        return ResponseEntity.ok(solicitudesService.get(idRequest));
    }

    @PostMapping
    public ResponseEntity<Integer> createSolicitudes(
            @RequestBody @Valid final SolicitudesDTO solicitudesDTO) {
        final Integer createdIdRequest = solicitudesService.create(solicitudesDTO);
        return new ResponseEntity<>(createdIdRequest, HttpStatus.CREATED);
    }

    @PutMapping("/{idRequest}")
    public ResponseEntity<Integer> updateSolicitudes(
            @PathVariable(name = "idRequest") final Integer idRequest,
            @RequestBody @Valid final SolicitudesDTO solicitudesDTO) {
        solicitudesService.update(idRequest, solicitudesDTO);
        return ResponseEntity.ok(idRequest);
    }

    @DeleteMapping("/{idRequest}")
    public ResponseEntity<Void> deleteSolicitudes(
            @PathVariable(name = "idRequest") final Integer idRequest) {
        solicitudesService.delete(idRequest);
        return ResponseEntity.noContent().build();
    }

}
