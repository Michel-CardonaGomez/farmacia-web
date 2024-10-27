package com.farmacia_web.farmacia_web.controllers;

import com.farmacia_web.farmacia_web.models.Proveedor;
import com.farmacia_web.farmacia_web.services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    /**
     * Muestra una lista de todos los proveedores junto con los formularios para crear o editar un proveedor.
     *
     * @param model El modelo que se utilizará para pasar datos a la vista.
     * @return La vista que muestra la lista de proveedores y formularios.
     */
    @GetMapping
    public String listarProveedores(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        model.addAttribute("proveedores", proveedorService.obtenerProveedores());
        return "entidades/proveedores"; // Nombre de la vista para listar proveedores y formularios
    }

    /**
     * Crea un nuevo proveedor y lo guarda en la base de datos.
     *
     * @param proveedor El nuevo proveedor a crear.
     * @param model El modelo para manejar mensajes de éxito o error.
     * @return Redirección a la lista de proveedores después de crearlo.
     */
    @PostMapping
    public String crearProveedor(@ModelAttribute("proveedor") Proveedor proveedor, Model model) {
        try {
            proveedorService.crearProveedor(proveedor);
            model.addAttribute("successMessage", "Proveedor creado con éxito.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al guardar el proveedor: " + e.getMessage());
        }
        model.addAttribute("proveedores", proveedorService.obtenerProveedores());
        return "redirect:/proveedores";
    }

    /**
     * Muestra el formulario para editar un proveedor existente.
     *
     * @param id    El ID del proveedor a editar.
     * @param model El modelo que contiene el proveedor a editar y la lista de proveedores.
     * @return La vista que muestra la lista de proveedores y el formulario de edición.
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Proveedor proveedor = proveedorService.obtenerPorId(id);
        if (proveedor != null) {
            model.addAttribute("proveedor", proveedor);
        } else {
            model.addAttribute("errorMessage", "Proveedor no encontrado.");
        }
        model.addAttribute("proveedores", proveedorService.obtenerProveedores());
        return "entidades/proveedores";
    }

    /**
     * Actualiza un proveedor existente en la base de datos con los nuevos datos proporcionados.
     *
     * @param id        El ID del proveedor a actualizar.
     * @param proveedor El proveedor con los datos actualizados.
     * @param model     El modelo para manejar mensajes de éxito o error.
     * @return Redirección a la lista de proveedores después de actualizarlo.
     */
    @PostMapping("/{id}")
    public String actualizarProveedor(@PathVariable Long id, @ModelAttribute("proveedor") Proveedor proveedor, Model model) {
        try {
            proveedorService.actualizarProveedorPorId(proveedor, id);
            model.addAttribute("successMessage", "Proveedor actualizado con éxito.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al actualizar el proveedor: " + e.getMessage());
        }
        model.addAttribute("proveedores", proveedorService.obtenerProveedores());
        return "redirect:/proveedores";
    }

    /**
     * Elimina un proveedor específico de la base de datos.
     *
     * @param id El ID del proveedor a eliminar.
     * @return Redirección a la lista de proveedores después de eliminarlo.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable Long id) {
        proveedorService.eliminarProveedor(id);
        return "redirect:/proveedores";
    }
}
