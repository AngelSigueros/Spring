package com.certidevs.service;

import com.certidevs.model.Product;
import com.certidevs.model.ProductType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private List<Product> products;

    public ProductService() {

        products = new ArrayList<>();
        products.add(Product.builder().id(1L).title("Producto1").price(321.65).type(ProductType.MONITOR).available(true).build());
        products.add(Product.builder().id(2L).title("Producto2").price(213.99).type(ProductType.SMART_PHONE).available(true).build());
        products.add(Product.builder().id(3L).title("Producto3").price(377.20).type(ProductType.SMART_WATCH).available(true).build());
        products.add(Product.builder().id(4L).title("Producto4").price(157.25).type(ProductType.PC).available(true).build());
        products.add(Product.builder().id(5L).title("Producto5").price(954.75).type(ProductType.LAPTOP).available(true).build());
    }

    public List<Product> findAll() {
        return new ArrayList<>(products);
    }

    public Double calculateTotalPrice() {

        return 100d;
    }

    public Boolean shopCartEmpty() {
        return this.products.isEmpty();
    }
}
