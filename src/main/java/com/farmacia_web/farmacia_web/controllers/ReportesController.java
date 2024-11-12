package com.farmacia_web.farmacia_web.controllers;

import com.farmacia_web.farmacia_web.repositories.ComprasRepository;
import com.farmacia_web.farmacia_web.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/reportes")
public class ReportesController {

@Autowired
private VentaRepository ventaRepository;
    @Autowired
    private ComprasRepository comprasRepository;

    @GetMapping
    public String mostrarHistoriales(Model model) {
        model.addAttribute("ventas", ventaRepository.findAll());
        model.addAttribute("compras", comprasRepository.findAll());
        return "entidades/reportes";
    }
}
