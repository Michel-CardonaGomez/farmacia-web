package com.farmacia_web.farmacia_web.controllers;

import com.farmacia_web.farmacia_web.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    ProductoService productoService;

    @GetMapping
    public String listarFacturas(Model model) {
        model.addAttribute("productos", productoService.obtenerProductos());
        return "entidades/facturas";
    }
}
