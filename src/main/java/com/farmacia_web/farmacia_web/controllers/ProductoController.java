package com.farmacia_web.farmacia_web.controllers;

import com.farmacia_web.farmacia_web.models.Producto;
import com.farmacia_web.farmacia_web.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.obtenerProductos());
        return "entidades/productos";
    }

    @GetMapping("/crear")
    public String MostrarFormularioRegistar(Model model) {
        model.addAttribute("producto", new Producto());
        return "entidades/crear-producto";
    }

    @PostMapping
    public String guardarProducto(Model model, @ModelAttribute("producto") Producto producto) {
        productoService.crearProducto(producto);
        return "redirect:/productos";
    }

    @GetMapping("/editar/{id}")
    public String MostrarFormularioEditar(@PathVariable Long id, Model model) {
        productoService.obtenerProductoPorId(id);
        return "entidades/editar-producto";
    }
}
