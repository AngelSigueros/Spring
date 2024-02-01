package como.sas.bootify_app.service;

import como.sas.bootify_app.domain.Categoria;
import como.sas.bootify_app.domain.Producto;
import como.sas.bootify_app.model.ProductoDTO;
import como.sas.bootify_app.repos.CategoriaRepository;
import como.sas.bootify_app.repos.ProductoRepository;
import como.sas.bootify_app.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(final ProductoRepository productoRepository,
            final CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    public List<ProductoDTO> findAll() {
        final List<Producto> productoes = productoRepository.findAll(Sort.by("idProducto"));
        return productoes.stream()
                .map(producto -> mapToDTO(producto, new ProductoDTO()))
                .toList();
    }

    public ProductoDTO get(final Integer idProducto) {
        return productoRepository.findById(idProducto)
                .map(producto -> mapToDTO(producto, new ProductoDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ProductoDTO productoDTO) {
        final Producto producto = new Producto();
        mapToEntity(productoDTO, producto);
        return productoRepository.save(producto).getIdProducto();
    }

    public void update(final Integer idProducto, final ProductoDTO productoDTO) {
        final Producto producto = productoRepository.findById(idProducto)
                .orElseThrow(NotFoundException::new);
        mapToEntity(productoDTO, producto);
        productoRepository.save(producto);
    }

    public void delete(final Integer idProducto) {
        productoRepository.deleteById(idProducto);
    }

    private ProductoDTO mapToDTO(final Producto producto, final ProductoDTO productoDTO) {
        productoDTO.setIdProducto(producto.getIdProducto());
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setCodigoBarras(producto.getCodigoBarras());
        productoDTO.setPrecioVenta(producto.getPrecioVenta());
        productoDTO.setCantidadStock(producto.getCantidadStock());
        productoDTO.setEstado(producto.getEstado());
        productoDTO.setCategoria(producto.getCategoria() == null ? null : producto.getCategoria().getIdCategoria());
        return productoDTO;
    }

    private Producto mapToEntity(final ProductoDTO productoDTO, final Producto producto) {
        producto.setNombre(productoDTO.getNombre());
        producto.setCodigoBarras(productoDTO.getCodigoBarras());
        producto.setPrecioVenta(productoDTO.getPrecioVenta());
        producto.setCantidadStock(productoDTO.getCantidadStock());
        producto.setEstado(productoDTO.getEstado());
        final Categoria categoria = productoDTO.getCategoria() == null ? null : categoriaRepository.findById(productoDTO.getCategoria())
                .orElseThrow(() -> new NotFoundException("categoria not found"));
        producto.setCategoria(categoria);
        return producto;
    }

}
