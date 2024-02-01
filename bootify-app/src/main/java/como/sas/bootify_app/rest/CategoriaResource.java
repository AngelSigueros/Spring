package como.sas.bootify_app.rest;

import como.sas.bootify_app.model.CategoriaDTO;
import como.sas.bootify_app.service.CategoriaService;
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
@RequestMapping(value = "/api/categorias", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoriaResource {

    private final CategoriaService categoriaService;

    public CategoriaResource(final CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> getAllCategorias() {
        return ResponseEntity.ok(categoriaService.findAll());
    }

    @GetMapping("/{idCategoria}")
    public ResponseEntity<CategoriaDTO> getCategoria(
            @PathVariable(name = "idCategoria") final Integer idCategoria) {
        return ResponseEntity.ok(categoriaService.get(idCategoria));
    }

    @PostMapping
    public ResponseEntity<Integer> createCategoria(
            @RequestBody @Valid final CategoriaDTO categoriaDTO) {
        final Integer createdIdCategoria = categoriaService.create(categoriaDTO);
        return new ResponseEntity<>(createdIdCategoria, HttpStatus.CREATED);
    }

    @PutMapping("/{idCategoria}")
    public ResponseEntity<Integer> updateCategoria(
            @PathVariable(name = "idCategoria") final Integer idCategoria,
            @RequestBody @Valid final CategoriaDTO categoriaDTO) {
        categoriaService.update(idCategoria, categoriaDTO);
        return ResponseEntity.ok(idCategoria);
    }

    @DeleteMapping("/{idCategoria}")
    public ResponseEntity<Void> deleteCategoria(
            @PathVariable(name = "idCategoria") final Integer idCategoria) {
        categoriaService.delete(idCategoria);
        return ResponseEntity.noContent().build();
    }

}
