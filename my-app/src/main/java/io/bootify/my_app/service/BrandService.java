package io.bootify.my_app.service;

import io.bootify.my_app.domain.Brand;
import io.bootify.my_app.domain.Product;
import io.bootify.my_app.model.BrandDTO;
import io.bootify.my_app.repos.BrandRepository;
import io.bootify.my_app.repos.ProductRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class BrandService {

    private final BrandRepository brandRepository;
    private final ProductRepository productRepository;

    public BrandService(final BrandRepository brandRepository,
            final ProductRepository productRepository) {
        this.brandRepository = brandRepository;
        this.productRepository = productRepository;
    }

    public List<BrandDTO> findAll() {
        final List<Brand> brands = brandRepository.findAll(Sort.by("id"));
        return brands.stream()
                .map(brand -> mapToDTO(brand, new BrandDTO()))
                .toList();
    }

    public BrandDTO get(final Long id) {
        return brandRepository.findById(id)
                .map(brand -> mapToDTO(brand, new BrandDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final BrandDTO brandDTO) {
        final Brand brand = new Brand();
        mapToEntity(brandDTO, brand);
        return brandRepository.save(brand).getId();
    }

    public void update(final Long id, final BrandDTO brandDTO) {
        final Brand brand = brandRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(brandDTO, brand);
        brandRepository.save(brand);
    }

    public void delete(final Long id) {
        brandRepository.deleteById(id);
    }

    private BrandDTO mapToDTO(final Brand brand, final BrandDTO brandDTO) {
        brandDTO.setId(brand.getId());
        brandDTO.setName(brand.getName());
        return brandDTO;
    }

    private Brand mapToEntity(final BrandDTO brandDTO, final Brand brand) {
        brand.setName(brandDTO.getName());
        return brand;
    }

    public String getReferencedWarning(final Long id) {
        final Brand brand = brandRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Product brandProduct = productRepository.findFirstByBrand(brand);
        if (brandProduct != null) {
            return WebUtils.getMessage("brand.product.brand.referenced", brandProduct.getId());
        }
        return null;
    }

}
