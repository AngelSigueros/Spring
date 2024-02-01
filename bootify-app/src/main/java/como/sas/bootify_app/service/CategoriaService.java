package como.sas.bootify_app.service;

import como.sas.bootify_app.domain.Categoria;
import como.sas.bootify_app.model.CategoriaDTO;
import como.sas.bootify_app.repos.CategoriaRepository;
import como.sas.bootify_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(final CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaDTO> findAll() {
        final List<Categoria> categorias = categoriaRepository.findAll(Sort.by("idCategoria"));
        return categorias.stream()
                .map(categoria -> mapToDTO(categoria, new CategoriaDTO()))
                .toList();
    }

    public CategoriaDTO get(final Integer idCategoria) {
        return categoriaRepository.findById(idCategoria)
                .map(categoria -> mapToDTO(categoria, new CategoriaDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final CategoriaDTO categoriaDTO) {
        final Categoria categoria = new Categoria();
        mapToEntity(categoriaDTO, categoria);
        return categoriaRepository.save(categoria).getIdCategoria();
    }

    public void update(final Integer idCategoria, final CategoriaDTO categoriaDTO) {
        final Categoria categoria = categoriaRepository.findById(idCategoria)
                .orElseThrow(NotFoundException::new);
        mapToEntity(categoriaDTO, categoria);
        categoriaRepository.save(categoria);
    }

    public void delete(final Integer idCategoria) {
        categoriaRepository.deleteById(idCategoria);
    }

    private CategoriaDTO mapToDTO(final Categoria categoria, final CategoriaDTO categoriaDTO) {
        categoriaDTO.setIdCategoria(categoria.getIdCategoria());
        categoriaDTO.setDescripcion(categoria.getDescripcion());
        categoriaDTO.setEstado(categoria.getEstado());
        return categoriaDTO;
    }

    private Categoria mapToEntity(final CategoriaDTO categoriaDTO, final Categoria categoria) {
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        categoria.setEstado(categoriaDTO.getEstado());
        return categoria;
    }

}
