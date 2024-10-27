package com.farmacia_web.farmacia_web.controllers;

import com.farmacia_web.farmacia_web.models.Subcategoria;
import com.farmacia_web.farmacia_web.services.SubcategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/subcategorias")
public class SubcategoriaController {

    @Autowired
    private SubcategoriaService subcategoriaService;

    /**
     * Muestra una lista de todas las subcategorías junto con los formularios para crear o editar una subcategoría.
     *
     * @param model El modelo que se utilizará para pasar datos a la vista.
     * @return La vista que muestra la lista de subcategorías y formularios.
     */
    @GetMapping
    public String listarSubcategorias(Model model) {
        model.addAttribute("subcategoria", new Subcategoria());
        model.addAttribute("subcategorias", subcategoriaService.obtenerSubcategorias());
        return "entidades/subcategorias"; // Nombre de la vista para listar subcategorías y formularios
    }

    /**
     * Crea una nueva subcategoría y la guarda en la base de datos.
     *
     * @param subcategoria La nueva subcategoría a crear.
     * @param model El modelo para manejar mensajes de éxito o error.
     * @return Redirección a la lista de subcategorías después de crearla.
     */
    @PostMapping
    public String crearSubcategoria(@ModelAttribute("subcategoria") Subcategoria subcategoria, Model model) {
        try {
            subcategoriaService.crearSubcategoria(subcategoria);
            model.addAttribute("successMessage", "Subcategoría creada con éxito.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al guardar la subcategoría: " + e.getMessage());
        }
        model.addAttribute("subcategorias", subcategoriaService.obtenerSubcategorias());
        return "redirect:/subcategorias";
    }

    /**
     * Muestra el formulario para editar una subcategoría existente.
     *
     * @param id    El ID de la subcategoría a editar.
     * @param model El modelo que contiene la subcategoría a editar y la lista de subcategorías.
     * @return La vista que muestra la lista de subcategorías y el formulario de edición.
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Subcategoria subcategoria = subcategoriaService.obtenerPorId(id);
        if (subcategoria != null) {
            model.addAttribute("subcategoria", subcategoria);
        } else {
            model.addAttribute("errorMessage", "Subcategoría no encontrada.");
        }
        model.addAttribute("subcategorias", subcategoriaService.obtenerSubcategorias());
        return "entidades/subcategorias"; // Nombre de la vista para listar subcategorías y formularios
    }

    /**
     * Actualiza una subcategoría existente en la base de datos con los nuevos datos proporcionados.
     *
     * @param id    El ID de la subcategoría a actualizar.
     * @param subcategoria La subcategoría con los datos actualizados.
     * @param model El modelo para manejar mensajes de éxito o error.
     * @return Redirección a la lista de subcategorías después de actualizarla.
     */
    @PostMapping("/{id}")
    public String actualizarSubcategoria(@PathVariable Long id, @ModelAttribute("subcategoria") Subcategoria subcategoria, Model model) {
        try {
            subcategoriaService.actualizarSubcategoriaPorId(subcategoria, id);
            model.addAttribute("successMessage", "Subcategoría actualizada con éxito.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al actualizar la subcategoría: " + e.getMessage());
        }
        model.addAttribute("subcategorias", subcategoriaService.obtenerSubcategorias());
        return "redirect:/subcategorias";
    }

    /**
     * Elimina una subcategoría específica de la base de datos.
     *
     * @param id El ID de la subcategoría a eliminar.
     * @return Redirección a la lista de subcategorías después de eliminarla.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarSubcategoria(@PathVariable Long id) {
        subcategoriaService.eliminarSubcategoria(id);
        return "redirect:/subcategorias"; // Redirige a la lista de subcategorías
    }
}
