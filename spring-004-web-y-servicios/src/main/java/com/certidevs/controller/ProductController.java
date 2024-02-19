package com.certidevs.controller;

import com.certidevs.model.Product;
import com.certidevs.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor //Hace la inyeccion
@RestController
@RequestMapping("/products")
public class ProductController {

    //@Autowired
    private ProductService productService;

    @GetMapping("/")
    public String start() {

        return "Products!!!";

    }

    @GetMapping("/all")
    public List<Product> findAll() {

        List<Product> products = this.productService.findAll();

        return products;

    }

    //@GetMapping("products")
    public ResponseEntity<List<Product>> findAllProducts() {

        List<Product> products = this.productService.findAll();

        return ResponseEntity.ok(products);

    }
}
