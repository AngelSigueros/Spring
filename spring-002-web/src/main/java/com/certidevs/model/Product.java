package com.certidevs.model;

import java.time.LocalDate;

/*
Los records son como clases abreviadas
Objetos son inmutables, no tienen métodos setter

 */
public record Product(Long id, String title, Double price,
                      Boolean available,
                      LocalDate creationDate) {
}
