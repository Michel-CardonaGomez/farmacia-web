package com.farmacia_web.farmacia_web.controllers;

import com.farmacia_web.farmacia_web.models.Proveedor;
import com.farmacia_web.farmacia_web.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    /**
     * Obtiene una lista de todos los proveedores.
     *
     * @return Lista de proveedores.
     */
    @GetMapping
    public String listarproveedores(Model model) {
        model.addAttribute("proveedores", proveedorService.obtenerProveedores());
        return "proveedores";
    }
    @GetMapping("/crear")
    public String MostrarFormularioRegistrar(Model model) {
        Proveedor proveedor = new Proveedor();
        model.addAttribute("proveedor", proveedor);
        return "crear-proveedor";
    }

    @PostMapping
    public String crearproveedor(@ModelAttribute("proveedor") Proveedor proveedor, Model model) {
        try {
            proveedorService.crearProveedor(proveedor);
            return "redirect:/admin/proveedores";
        } catch (RuntimeException e) {
            // Capturar el error y pasarlo al modelo
            model.addAttribute("errorMessage", "Error al guardar el proveedor: " + e.getMessage());
            model.addAttribute("showErrorModal", true); // Esta bandera indica que el modal debe mostrarse
            return "crear-proveedor";
        }
    }
    @GetMapping("/editar/{id}")
    public String MostrarFormularioEditar (@PathVariable Long id, Model model) {
        model.addAttribute("proveedor", proveedorService.obtenerPorId(id));
        return "editar-proveedor";
    }

    @PostMapping("/{id}")
    public String Actualizarproveedor(@PathVariable Long id, @ModelAttribute("proveedor") Proveedor proveedor, Model model) {
        proveedorService.actualizarProveedorPorId(proveedor, id);
        return "redirect:/admin/proveedores";
    }


    /**
     * Elimina un proveedor por su ID.
     *
     * @param id El ID del proveedor a eliminar.
     * @return Mensaje de éxito.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarproveedor(@PathVariable Long id) {
        proveedorService.eliminarProveedor(id);
        return "redirect:/admin/proveedores";
    }
}

