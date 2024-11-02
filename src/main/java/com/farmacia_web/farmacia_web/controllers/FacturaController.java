package com.farmacia_web.farmacia_web.controllers;

import com.farmacia_web.farmacia_web.models.EmpleadoDetails;
import com.farmacia_web.farmacia_web.models.Producto;
import com.farmacia_web.farmacia_web.models.Venta;
import com.farmacia_web.farmacia_web.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    ProductoService productoService;

    @GetMapping
    public String listarFacturas(Model model, Authentication authentication) {
        List<Producto> productos = productoService.obtenerProductos();
        EmpleadoDetails empleadoDetails = (EmpleadoDetails) authentication.getPrincipal();
        int totalProductos = productos.size();
        int filas = (totalProductos + 3) / 4; // Calcular filas redondeando hacia arriba
        model.addAttribute("empleado", empleadoDetails.getName());
        model.addAttribute("productos", productoService.obtenerProductos());
        model.addAttribute("filas", filas);
        model.addAttribute("venta", new Venta());
        return "entidades/facturas";
    }
}
