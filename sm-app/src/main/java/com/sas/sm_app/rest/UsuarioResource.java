package com.sas.sm_app.rest;

import com.sas.sm_app.model.UsuarioDTO;
import com.sas.sm_app.service.UsuarioService;
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
@RequestMapping(value = "/api/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioResource {

    private final UsuarioService usuarioService;

    public UsuarioResource(final UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{idUser}")
    public ResponseEntity<UsuarioDTO> getUsuario(
            @PathVariable(name = "idUser") final Integer idUser) {
        return ResponseEntity.ok(usuarioService.get(idUser));
    }

    @PostMapping
    public ResponseEntity<Integer> createUsuario(@RequestBody @Valid final UsuarioDTO usuarioDTO) {
        final Integer createdIdUser = usuarioService.create(usuarioDTO);
        return new ResponseEntity<>(createdIdUser, HttpStatus.CREATED);
    }

    @PutMapping("/{idUser}")
    public ResponseEntity<Integer> updateUsuario(
            @PathVariable(name = "idUser") final Integer idUser,
            @RequestBody @Valid final UsuarioDTO usuarioDTO) {
        usuarioService.update(idUser, usuarioDTO);
        return ResponseEntity.ok(idUser);
    }

    @DeleteMapping("/{idUser}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable(name = "idUser") final Integer idUser) {
        usuarioService.delete(idUser);
        return ResponseEntity.noContent().build();
    }

}
