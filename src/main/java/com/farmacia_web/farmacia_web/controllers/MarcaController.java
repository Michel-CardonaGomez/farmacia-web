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
     * Muestra una lista de todas las marcas junto con los formularios para crear o editar una marca.
     *
     * @param model El modelo que se utilizará para pasar datos a la vista.
     * @return La vista que muestra la lista de marcas y formularios.
     */
    @GetMapping
    public String listarMarcas(Model model) {
        model.addAttribute("marca", new Marca());
        model.addAttribute("marcas", marcaService.obtenerMarcas());
        return "entidades/marcas"; // Nombre de la vista para listar marcas y formularios
    }

    /**
     * Crea una nueva marca y la guarda en la base de datos.
     *
     * @param marca La nueva marca a crear.
     * @param model El modelo para manejar mensajes de éxito o error.
     * @return Redirección a la lista de marcas después de crearla.
     */
    @PostMapping
    public String crearMarca(@ModelAttribute("marca") Marca marca, Model model) {
        try {
            marcaService.crearMarca(marca);
            model.addAttribute("successMessage", "Marca creada con éxito.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al guardar la marca: " + e.getMessage());
        }
        model.addAttribute("marcas", marcaService.obtenerMarcas());
        return "redirect:/marcas";
    }

    /**
     * Muestra el formulario para editar una marca existente.
     *
     * @param id    El ID de la marca a editar.
     * @param model El modelo que contiene la marca a editar y la lista de marcas.
     * @return La vista que muestra la lista de marcas y el formulario de edición.
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Marca marca = marcaService.obtenerPorId(id);
        if (marca != null) {
            model.addAttribute("marca", marca);
        } else {
            model.addAttribute("errorMessage", "Marca no encontrada.");
        }
        model.addAttribute("marcas", marcaService.obtenerMarcas());
        return "entidades/marcas";
    }

    /**
     * Actualiza una marca existente en la base de datos con los nuevos datos proporcionados.
     *
     * @param id    El ID de la marca a actualizar.
     * @param marca La marca con los datos actualizados.
     * @param model El modelo para manejar mensajes de éxito o error.
     * @return Redirección a la lista de marcas después de actualizarla.
     */
    @PostMapping("/{id}")
    public String actualizarMarca(@PathVariable Long id, @ModelAttribute("marca") Marca marca, Model model) {
        try {
            marcaService.actualizarMarcaPorId(marca, id);
            model.addAttribute("successMessage", "Marca actualizada con éxito.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al actualizar la marca: " + e.getMessage());
        }
        model.addAttribute("marcas", marcaService.obtenerMarcas());
        return "redirect:/marcas";
    }

    /**
     * Elimina una marca específica de la base de datos.
     *
     * @param id El ID de la marca a eliminar.
     * @return Redirección a la lista de marcas después de eliminarla.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarMarca(@PathVariable Long id) {
        marcaService.eliminarMarca(id);
        return "redirect:/marcas";
    }
}



