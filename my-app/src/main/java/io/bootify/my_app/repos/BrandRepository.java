package io.bootify.my_app.repos;

import io.bootify.my_app.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BrandRepository extends JpaRepository<Brand, Long> {
}
