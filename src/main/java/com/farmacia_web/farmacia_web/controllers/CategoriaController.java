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
     * Obtiene una lista de todas las categorías.
     *
     * @param model El modelo para pasar los datos a la vista.
     * @return La vista con la lista de categorías.
     */
    @GetMapping
    public String listarCategorias(Model model) {
        model.addAttribute("categorias", categoriaService.obtenerCategorias());
        return "categorias/categorias"; // Nombre de la vista que muestra la lista de categorías
    }

    /**
     * Muestra el formulario para crear una nueva categoría.
     *
     * @param model El modelo para pasar los datos a la vista.
     * @return La vista con el formulario de creación de categoría.
     */
    @GetMapping("/crear")
    public String mostrarFormularioRegistrar(Model model) {
        Categoria categoria = new Categoria();
        model.addAttribute("categoria", categoria);
        return "categorias/crear-categoria"; // Nombre de la vista del formulario de creación
    }

    /**
     * Crea una nueva categoría en la base de datos.
     *
     * @param categoria La categoría a crear.
     * @param model El modelo para manejar errores y pasar los datos a la vista.
     * @return Redirección a la lista de categorías o a la vista de creación en caso de error.
     */
    @PostMapping
    public String crearCategoria(@ModelAttribute("categoria") Categoria categoria, Model model) {
        try {
            categoriaService.crearCategoria(categoria);
            return "redirect:/categorias";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Error al guardar la categoría: " + e.getMessage());
            model.addAttribute("showErrorModal", true); // Bandera para mostrar el modal de error
            return "categorias/crear-categoria"; // Regresar al formulario en caso de error
        }
    }

    /**
     * Muestra el formulario para editar una categoría existente.
     *
     * @param id    El ID de la categoría a editar.
     * @param model El modelo para pasar los datos de la categoría a la vista.
     * @return La vista con el formulario de edición de categoría.
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("categoria", categoriaService.obtenerPorId(id));
        return "categorias/editar-categoria"; // Nombre de la vista del formulario de edición
    }

    /**
     * Actualiza una categoría existente en la base de datos.
     *
     * @param id    El ID de la categoría a actualizar.
     * @param categoria La categoría con los nuevos datos.
     * @param model El modelo para manejar errores y pasar los datos a la vista.
     * @return Redirección a la lista de categorías.
     */
    @PostMapping("/{id}")
    public String actualizarCategoria(@PathVariable Long id, @ModelAttribute("categoria") Categoria categoria, Model model) {
        categoriaService.actualizarCategoriaPorId(categoria, id);
        return "redirect:/categorias";
    }

    /**
     * Elimina una categoría por su ID.
     *
     * @param id El ID de la categoría a eliminar.
     * @return Redirección a la lista de categorías.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarCategoria(@PathVariable Long id) {
        categoriaService.eliminarCategoria(id);
        return "redirect:/categorias";
    }
}

