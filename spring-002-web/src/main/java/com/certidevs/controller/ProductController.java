package com.certidevs.controller;

import com.certidevs.model.Product;
import com.certidevs.model.Producto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    @GetMapping("/")
    public Product find() {

        return new Product(180L, "Bat Movil", 3300.00, false, null);
    }

    @GetMapping("producto/productos")
    public List<Producto> findAll() {

        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto(1,"Ford", "Fiesta", true, LocalDate.now()));
        productos.add(new Producto(2,"Ford", "Orion", false, null));
        productos.add(new Producto(3,"Renault", "5", true, LocalDate.now()));
        productos.add(new Producto(4,"Audi", "A3", false, null));

        return productos;
    }

    @GetMapping("producto/productos2")
    public List<Producto> findAll2() {

        return List.of(new Producto(5, "Audi", "100", true, null),
                new Producto(6, "Mercedes", "100", true, null),
                new Producto(7, "Mercedes", "100", true, null),
                new Producto(8, "Rolls Royce", "100", true, null)
                );
    }

    @GetMapping("producto/producto-demo")
    public Producto findProducto() {

        return new Producto(8, "Rolls Royce", "100", true, null);
    }

    @GetMapping("producto/product")
    public Product findProduct() {

        return new Product(9L, "Bat Movil", 100.00, true, null);
    }

    @GetMapping("producto/product/{id}")
    public Product findProductById(@PathVariable Long id) {

        return new Product(10L, "Motoreta", 20.00, true, null);
    }

    @GetMapping("producto/product/{title}")
    public Product findProductByTitle(@PathVariable String title) {

        return new Product(11L, "11 Motoreta", 2.10, false, null);
    }

    @GetMapping("producto/productos/{max}/{min}")
    public List<Product> findByMaxMin(@PathVariable int max, @PathVariable int min) {

        System.out.println(max + " > " + min);
        // if (min >= max)
        //    throw new IllegalArgumentException(); // Tratar excepciones con ResponseEntity

        return List.of(
                new Product(13L, "TV Samsung 52", 450.0, true, LocalDate.now()), // hardcoded values. Replace with real data from database
                new Product(14L, "PC MSI Modern", 600.0, false, null)
        );
    }

    // RECIBIR DATOS DESDE EL EXTERIOR EN JSON
    @PostMapping("products") // no colisiona con GetMapping
    public Product create(@RequestBody Product product) {

        System.out.println(product);
        System.out.println(product.id());
        System.out.println(product.title());
        System.out.println(product.price());
        // guardar en base de datos
        // se le genera un nuevo id ....
        // devolver el product con el nuevo id
        return product;
    }

    @PutMapping("products/{id}")
    public Product update(@PathVariable long id, @RequestBody Product product) {
        System.out.println("En PUT - update");
        System.out.println(product);
        System.out.println(product.id());
        System.out.println(product.title());
        System.out.println(product.price());
        return product;
    }

    @DeleteMapping("products/{id}")
    public void delete(@PathVariable long id) {
        System.out.println("En DELETE - delete");
        System.out.println("Producto: "+id+" eliminado");
    }
}
