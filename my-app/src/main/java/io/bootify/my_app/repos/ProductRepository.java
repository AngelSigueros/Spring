package io.bootify.my_app.repos;

import io.bootify.my_app.domain.Brand;
import io.bootify.my_app.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findFirstByBrand(Brand brand);

}
