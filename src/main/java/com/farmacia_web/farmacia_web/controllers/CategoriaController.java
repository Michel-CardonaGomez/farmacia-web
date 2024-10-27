package com.farmacia_web.farmacia_web.controllers;

import org.springframework.ui.Model;
import com.farmacia_web.farmacia_web.models.Categoria;
import com.farmacia_web.farmacia_web.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    /**
     * Muestra una lista de todas las categorías junto con los formularios para crear o editar una categoría.
     *
     * @param model El modelo que se utilizará para pasar datos a la vista.
     * @return La vista que muestra la lista de categorías y formularios.
     */
    @GetMapping
    public String listarCategorias(Model model) {
        model.addAttribute("categoria", new Categoria());
        model.addAttribute("categorias", categoriaService.obtenerCategorias());
        return "entidades/categorias"; // Nombre de la vista para listar categorías y formularios
    }

    /**
     * Crea una nueva categoría y la guarda en la base de datos.
     *
     * @param categoria La nueva categoría a crear.
     * @param model El modelo para manejar mensajes de éxito o error.
     * @return Redirección a la lista de categorías después de crearla.
     */
    @PostMapping
    public String crearCategoria(@ModelAttribute("categoria") Categoria categoria, Model model) {
        try {
            categoriaService.crearCategoria(categoria);
            model.addAttribute("successMessage", "Categoría creada con éxito.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al guardar la categoría: " + e.getMessage());
        }
        model.addAttribute("categorias", categoriaService.obtenerCategorias());
        return "redirect:/categorias";
    }

    /**
     * Muestra el formulario para editar una categoría existente.
     *
     * @param id    El ID de la categoría a editar.
     * @param model El modelo que contiene la categoría a editar y la lista de categorías.
     * @return La vista que muestra la lista de categorías y el formulario de edición.
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Categoria categoria = categoriaService.obtenerPorId(id);
        if (categoria != null) {
            model.addAttribute("categoria", categoria);
        } else {
            model.addAttribute("errorMessage", "Categoría no encontrada.");
        }
        model.addAttribute("categorias", categoriaService.obtenerCategorias());
        return "entidades/categorias";
    }

    /**
     * Actualiza una categoría existente en la base de datos con los nuevos datos proporcionados.
     *
     * @param id       El ID de la categoría a actualizar.
     * @param categoria La categoría con los datos actualizados.
     * @param model    El modelo para manejar mensajes de éxito o error.
     * @return Redirección a la lista de categorías después de actualizarla.
     */
    @PostMapping("/{id}")
    public String actualizarCategoria(@PathVariable Long id, @ModelAttribute("categoria") Categoria categoria, Model model) {
        try {
            categoriaService.actualizarCategoriaPorId(categoria, id);
            model.addAttribute("successMessage", "Categoría actualizada con éxito.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al actualizar la categoría: " + e.getMessage());
        }
        model.addAttribute("categorias", categoriaService.obtenerCategorias());
        return "redirect:/categorias";
    }

    /**
     * Elimina una categoría específica de la base de datos.
     *
     * @param id El ID de la categoría a eliminar.
     * @return Redirección a la lista de categorías después de eliminarla.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return "redirect:/categorias";
    }
}