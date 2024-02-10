package com.sas.controller;

import com.sas.model.Address;
import com.sas.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/address")
@CrossOrigin(origins = "http://localhost:4200")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;


    @GetMapping
    public List<Address> findAll() {
        return addressRepository.findAll();
    }

    @GetMapping("/{id}")
    public Address findById(@PathVariable Long id) {
        return addressRepository.findById(id).get();
    }

    @PostMapping
    public Address create(@RequestBody Address address) {
        return addressRepository.save(address);
    }

    @PutMapping("{id}")
    public Address update(@PathVariable Long id, @RequestBody Address address) {
        Optional<Address> addressOpt = addressRepository.findById(id);
        if(addressOpt.isEmpty()) // Si isEmpty significa que no existe y por tanto devolvemos 404 not found
            return null;

        Address addressFromDB = addressOpt.get();

        addressFromDB.setStreet(address.getStreet());
        addressFromDB.setCity(address.getCity());

        return addressRepository.save(addressFromDB);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Long id) {
        addressRepository.deleteById(id);
    }




}
