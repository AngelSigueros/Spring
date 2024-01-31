package com.certidevs.controller;

import com.certidevs.model.Product;
import com.certidevs.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("products")
    public ResponseEntity<List<Product>> findAll() {

        List<Product> products = this.productService.findAll();

        return ResponseEntity.ok(products);

    }
}
