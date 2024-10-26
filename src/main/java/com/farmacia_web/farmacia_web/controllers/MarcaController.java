package com.farmacia_web.farmacia_web.controllers;

import com.farmacia_web.farmacia_web.models.Marca;
import com.farmacia_web.farmacia_web.services.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/marcas")
public class MarcaController {

    @Autowired
    private MarcaService marcaService;

    /**
     * Obtiene una lista de todas las marcas.
     *
     * @param model El modelo para pasar los datos a la vista.
     * @return La vista con la lista de marcas.
     */
    @GetMapping
    public String listarMarcas(Model model) {
        model.addAttribute("marcas", marcaService.obtenerMarcas());
        return "marcas/marcas"; // Nombre de la vista para listar marcas
    }

    /**
     * Muestra el formulario para crear una nueva marca.
     *
     * @param model El modelo para pasar los datos a la vista.
     * @return La vista con el formulario de creación de marca.
     */
    @GetMapping("/crear")
    public String mostrarFormularioRegistrar(Model model) {
        Marca marca = new Marca();
        model.addAttribute("marca", marca);
        return "marcas/crear-marca"; // Nombre de la vista para crear una marca
    }

    /**
     * Crea una nueva marca en la base de datos.
     *
     * @param marca La marca a crear.
     * @param model El modelo para manejar errores y pasar los datos a la vista.
     * @return Redirección a la lista de marcas o a la vista de creación en caso de error.
     */
    @PostMapping
    public String crearMarca(@ModelAttribute("marca") Marca marca, Model model) {
        try {
            marcaService.crearMarca(marca);
            return "redirect:/marcas";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Error al guardar la marca: " + e.getMessage());
            model.addAttribute("showErrorModal", true); // Bandera para mostrar el modal de error
            return "marcas/crear-marca"; // Nombre de la vista para crear una marca
        }
    }

    /**
     * Muestra el formulario para editar una marca existente.
     *
     * @param id    El ID de la marca a editar.
     * @param model El modelo para pasar los datos de la marca a la vista.
     * @return La vista con el formulario de edición de marca.
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("marca", marcaService.obtenerPorId(id));
        return "marcas/editar-marca"; // Nombre de la vista para editar una marca
    }

    /**
     * Actualiza una marca existente en la base de datos.
     *
     * @param id    El ID de la marca a actualizar.
     * @param marca La marca con los nuevos datos.
     * @param model El modelo para manejar errores y pasar los datos a la vista.
     * @return Redirección a la lista de marcas.
     */
    @PostMapping("/{id}")
    public String actualizarMarca(@PathVariable Long id, @ModelAttribute("marca") Marca marca, Model model) {
        marcaService.actualizarMarcaPorId(marca, id);
        return "redirect:/marcas";
    }

    /**
     * Elimina una marca por su ID.
     *
     * @param id El ID de la marca a eliminar.
     * @return Redirección a la lista de marcas.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarMarca(@PathVariable Long id) {
        marcaService.eliminarMarca(id);
        return "redirect:/marcas";
    }
}
