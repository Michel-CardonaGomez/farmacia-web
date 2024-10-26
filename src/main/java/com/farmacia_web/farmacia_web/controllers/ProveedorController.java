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
     * Muestra la lista de todos los proveedores.
     *
     * @param model El modelo para pasar datos a la vista.
     * @return El nombre de la vista que muestra la lista de proveedores.
     */
    @GetMapping
    public String listarProveedores(Model model) {
        model.addAttribute("proveedores", proveedorService.obtenerProveedores());
        return "proveedores/proveedores"; // Nombre de la vista para listar proveedores
    }

    /**
     * Muestra el formulario para registrar un nuevo proveedor.
     *
     * @param model El modelo para pasar datos a la vista.
     * @return El nombre de la vista que muestra el formulario de creación de proveedor.
     */
    @GetMapping("/crear")
    public String mostrarFormularioRegistrar(Model model) {
        Proveedor proveedor = new Proveedor();
        model.addAttribute("proveedor", proveedor);
        return "proveedores/crear-proveedor"; // Nombre de la vista para crear un proveedor
    }

    /**
     * Crea un nuevo proveedor y redirige a la lista de proveedores.
     *
     * @param proveedor El objeto proveedor que se va a crear.
     * @param model    El modelo para pasar datos a la vista en caso de error.
     * @return La redirección a la lista de proveedores o el formulario de creación con un mensaje de error.
     */
    @PostMapping
    public String crearProveedor(@ModelAttribute("proveedor") Proveedor proveedor, Model model) {
        try {
            proveedorService.crearProveedor(proveedor);
            return "redirect:/admin/proveedores";
        } catch (RuntimeException e) {
            // Capturar el error y pasarlo al modelo
            model.addAttribute("errorMessage", "Error al guardar el proveedor: " + e.getMessage());
            model.addAttribute("showErrorModal", true); // Esta bandera indica que el modal debe mostrarse
            return "proveedores/crear-proveedor"; // Nombre de la vista para crear un proveedor
        }
    }

    /**
     * Muestra el formulario para editar un proveedor existente.
     *
     * @param id    El ID del proveedor a editar.
     * @param model El modelo para pasar datos a la vista.
     * @return El nombre de la vista que muestra el formulario de edición de proveedor.
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("proveedor", proveedorService.obtenerPorId(id));
        return "proveedores/editar-proveedor"; // Nombre de la vista para editar un proveedor
    }

    /**
     * Actualiza un proveedor existente y redirige a la lista de proveedores.
     *
     * @param id       El ID del proveedor a actualizar.
     * @param proveedor El objeto proveedor con la información actualizada.
     * @param model    El modelo para pasar datos a la vista en caso de error.
     * @return La redirección a la lista de proveedores.
     */
    @PostMapping("/{id}")
    public String actualizarProveedor(@PathVariable Long id, @ModelAttribute("proveedor") Proveedor proveedor, Model model) {
        proveedorService.actualizarProveedorPorId(proveedor, id);
        return "redirect:/admin/proveedores";
    }

    /**
     * Elimina un proveedor por su ID y redirige a la lista de proveedores.
     *
     * @param id El ID del proveedor a eliminar.
     * @return La redirección a la lista de proveedores.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarProveedor(@PathVariable Long id) {
        proveedorService.eliminarProveedor(id);
        return "redirect:/admin/proveedores";
    }
}


