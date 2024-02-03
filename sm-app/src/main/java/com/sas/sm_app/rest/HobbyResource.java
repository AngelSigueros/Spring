package com.sas.sm_app.rest;

import com.sas.sm_app.model.HobbyDTO;
import com.sas.sm_app.service.HobbyService;
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
@RequestMapping(value = "/api/hobbies", produces = MediaType.APPLICATION_JSON_VALUE)
public class HobbyResource {

    private final HobbyService hobbyService;

    public HobbyResource(final HobbyService hobbyService) {
        this.hobbyService = hobbyService;
    }

    @GetMapping
    public ResponseEntity<List<HobbyDTO>> getAllHobbies() {
        return ResponseEntity.ok(hobbyService.findAll());
    }

    @GetMapping("/{idHobby}")
    public ResponseEntity<HobbyDTO> getHobby(
            @PathVariable(name = "idHobby") final Integer idHobby) {
        return ResponseEntity.ok(hobbyService.get(idHobby));
    }

    @PostMapping
    public ResponseEntity<Integer> createHobby(@RequestBody @Valid final HobbyDTO hobbyDTO) {
        final Integer createdIdHobby = hobbyService.create(hobbyDTO);
        return new ResponseEntity<>(createdIdHobby, HttpStatus.CREATED);
    }

    @PutMapping("/{idHobby}")
    public ResponseEntity<Integer> updateHobby(
            @PathVariable(name = "idHobby") final Integer idHobby,
            @RequestBody @Valid final HobbyDTO hobbyDTO) {
        hobbyService.update(idHobby, hobbyDTO);
        return ResponseEntity.ok(idHobby);
    }

    @DeleteMapping("/{idHobby}")
    public ResponseEntity<Void> deleteHobby(@PathVariable(name = "idHobby") final Integer idHobby) {
        hobbyService.delete(idHobby);
        return ResponseEntity.noContent().build();
    }

}
