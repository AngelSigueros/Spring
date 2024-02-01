package como.sas.bootify_app.repos;

import como.sas.bootify_app.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductoRepository extends JpaRepository<Producto, Integer> {
}
