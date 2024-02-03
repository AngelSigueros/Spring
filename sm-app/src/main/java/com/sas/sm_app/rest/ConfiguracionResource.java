package com.sas.sm_app.rest;

import com.sas.sm_app.model.ConfiguracionDTO;
import com.sas.sm_app.service.ConfiguracionService;
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
@RequestMapping(value = "/api/configuracions", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConfiguracionResource {

    private final ConfiguracionService configuracionService;

    public ConfiguracionResource(final ConfiguracionService configuracionService) {
        this.configuracionService = configuracionService;
    }

    @GetMapping
    public ResponseEntity<List<ConfiguracionDTO>> getAllConfiguracions() {
        return ResponseEntity.ok(configuracionService.findAll());
    }

    @GetMapping("/{idConfig}")
    public ResponseEntity<ConfiguracionDTO> getConfiguracion(
            @PathVariable(name = "idConfig") final Integer idConfig) {
        return ResponseEntity.ok(configuracionService.get(idConfig));
    }

    @PostMapping
    public ResponseEntity<Integer> createConfiguracion(
            @RequestBody @Valid final ConfiguracionDTO configuracionDTO) {
        final Integer createdIdConfig = configuracionService.create(configuracionDTO);
        return new ResponseEntity<>(createdIdConfig, HttpStatus.CREATED);
    }

    @PutMapping("/{idConfig}")
    public ResponseEntity<Integer> updateConfiguracion(
            @PathVariable(name = "idConfig") final Integer idConfig,
            @RequestBody @Valid final ConfiguracionDTO configuracionDTO) {
        configuracionService.update(idConfig, configuracionDTO);
        return ResponseEntity.ok(idConfig);
    }

    @DeleteMapping("/{idConfig}")
    public ResponseEntity<Void> deleteConfiguracion(
            @PathVariable(name = "idConfig") final Integer idConfig) {
        configuracionService.delete(idConfig);
        return ResponseEntity.noContent().build();
    }

}
