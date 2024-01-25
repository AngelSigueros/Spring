package com.certidevs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HolaMundoController {

    @GetMapping("/hola")
    public String holaMundo() {
        return "Hola Spring!!!";
    }

    @GetMapping("/productos-hola")
    public String productos() {
        return "<table>\n" +
                "<tr>\n" +
                "<td>Ordenador MSI Modern</td>\n" +
                "<td>32 GB</td>\n" +
                "<td>i7</td>\n" +
                "<td>499,50 €</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>Ordenador Asus A55A</td>\n" +
                "<td>8 GB</td>\n" +
                "<td>i7</td>\n" +
                "<td>299,50 €</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td>Ordenador LG gram</td>\n" +
                "<td>32 GB</td>\n" +
                "<td>i7</td>\n" +
                "<td>999,50 €</td>\n" +
                "</tr>\n" +
                "</table>";
    }
}
