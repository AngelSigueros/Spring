package com.certidevs.controller;

import com.certidevs.model.ShopCartHasProducts;
import com.certidevs.model.ShopCartTotalPrice;
import com.certidevs.model.User;
import com.certidevs.service.ProductService;
import com.certidevs.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/shop-cart/")
public class ShopCartController {

    private ProductService productService;
    private UserService userService;

    @GetMapping("/")
    public String hello() {
        return "Shop Cart!";
    }

    @GetMapping("/shop-cart")
    public String find() {
        return null;
    }

    @GetMapping("/calculate-total-price")
    public Double getTotalPrice() {

        return productService.calculateTotalPrice();
    }

    @GetMapping("/calculate-total-price2")
    public ShopCartTotalPrice getTotalPrice2() {

        Double price = productService.calculateTotalPrice();
        return new ShopCartTotalPrice(price);
    }

    @GetMapping("/calculate-total-price3")
    public ResponseEntity<Double> getTotalPrice3() {

        Double price = productService.calculateTotalPrice();
        return ResponseEntity.ok(price);
    }

    @GetMapping("/empty-cart")
    public Boolean isCartEmpty() {

        return productService.shopCartEmpty();
    }

    @GetMapping("/empty-cart2")
    public ShopCartHasProducts isCartEmpty2() {
        Boolean exist = productService.shopCartEmpty();
        return new ShopCartHasProducts(exist);
    }

    @GetMapping("/shop-cart-user/{id}")
    public User findUserById(@PathVariable Long id) {
        return userService.findById(id);
    }
}
