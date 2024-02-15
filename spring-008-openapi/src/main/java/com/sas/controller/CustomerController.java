package com.sas.controller;

import com.sas.dto.Customer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {

    List<Customer> customers = new ArrayList<>();


    @GetMapping("/customer/{phone}")
    public Customer findById(@PathVariable String phone) {
        return this.customers.stream().filter(p->p.phone().equals(phone)).findFirst().orElse(null);
    }

    @GetMapping("/customer/all")
    public List<Customer> findAll() {
        this.customers.addAll(List.of(
                new Customer("aaa@aaa.aa", "521423587"),
                new Customer("bbb@bbb.bb", "325874125")));
        return this.customers;
    }

    @PostMapping("/customer")
    public Customer save(@RequestBody Customer customer) {

        customers.add(customer);
        return customer;
    }

    @PutMapping("/customer/{phone}")
    public Customer update(@PathVariable String phone, @RequestBody Customer customer) {

//        Customer customer1 = this.customers.stream().filter(b -> b.phone().equals(phone)).findFirst().orElse(null);
//        if (customer1 == null)
//            return null;
//        else {
//            this.customers
//        }
//        return customer1;

        int index = -1;
        for (Customer customer1 : this.customers) {
            if (customer1.phone().equals(phone))
                index = this.customers.indexOf(customer1);
        }

        if (index != -1)
            this.customers.add(index, customer);

        return customer;
    }


    @DeleteMapping("/customer/{phone}")
    public void deleteById(@PathVariable String phone) {

        int index = -1;
        for (Customer customer : this.customers) {
            if (customer.phone().equals(phone))
                index = this.customers.indexOf(customer);
        }

        if (index != -1)
            this.customers.remove(index);

    }

    @DeleteMapping("/customer")
    public ResponseEntity<Void> deleteAll() {

        if (!this.customers.isEmpty())
            this.customers.clear();
        return ResponseEntity.noContent().build();

    }

}
