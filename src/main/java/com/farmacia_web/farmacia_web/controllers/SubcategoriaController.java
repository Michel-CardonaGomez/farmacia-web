package com.farmacia_web.farmacia_web.controllers;

import org.springframework.ui.Model;
import com.farmacia_web.farmacia_web.models.Subcategoria;
import com.farmacia_web.farmacia_web.services.CategoriaService;
import com.farmacia_web.farmacia_web.services.SubcategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/subcategorias")
public class SubcategoriaController {

    @Autowired
    private SubcategoriaService subcategoriaService;

    @Autowired
    private CategoriaService categoriaService; // Servicio para acceder a las categorías

    /**
     * Obtiene una lista de todas las subcategorías.
     *
     * @param model El modelo para pasar los datos a la vista.
     * @return La vista con la lista de subcategorías.
     */
    @GetMapping
    public String listarSubcategorias(Model model) {
        model.addAttribute("subcategorias", subcategoriaService.obtenerSubcategorias());
        return "subcategorias/subcategorias"; // Vista que muestra la lista de subcategorías
    }

    /**
     * Muestra el formulario para crear una nueva subcategoría.
     *
     * @param model El modelo para pasar los datos a la vista.
     * @return La vista con el formulario de creación de subcategoría.
     */
    @GetMapping("/crear")
    public String mostrarFormularioRegistrar(Model model) {
        Subcategoria subcategoria = new Subcategoria();
        model.addAttribute("subcategoria", subcategoria);
        model.addAttribute("categorias", categoriaService.obtenerCategorias()); // Lista de categorías para el formulario
        return "subcategorias/crear-subcategoria"; // Vista del formulario de creación de subcategoría
    }

    /**
     * Crea una nueva subcategoría en la base de datos.
     *
     * @param subcategoria La subcategoría a crear.
     * @param model El modelo para manejar errores y pasar los datos a la vista.
     * @return Redirección a la lista de subcategorías o a la vista de creación en caso de error.
     */
    @PostMapping
    public String crearSubcategoria(@ModelAttribute("subcategoria") Subcategoria subcategoria, Model model) {
        try {
            subcategoriaService.crearSubcategoria(subcategoria);
            return "redirect:/subcategorias";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Error al guardar la subcategoría: " + e.getMessage());
            model.addAttribute("showErrorModal", true); // Bandera para mostrar el modal de error
            model.addAttribute("categorias", categoriaService.obtenerCategorias()); // Lista de categorías para volver a mostrar
            return "subcategorias/crear-subcategoria"; // Regresar al formulario en caso de error
        }
    }

    /**
     * Muestra el formulario para editar una subcategoría existente.
     *
     * @param id El ID de la subcategoría a editar.
     * @param model El modelo para pasar los datos de la subcategoría y las categorías a la vista.
     * @return La vista con el formulario de edición de subcategoría.
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("subcategoria", subcategoriaService.obtenerPorId(id));
        model.addAttribute("categorias", categoriaService.obtenerCategorias()); // Lista de categorías para el formulario
        return "subcategorias/editar-subcategoria"; // Vista del formulario de edición de subcategoría
    }

    /**
     * Actualiza una subcategoría existente en la base de datos.
     *
     * @param id El ID de la subcategoría a actualizar.
     * @param subcategoria La subcategoría con los nuevos datos.
     * @param model El modelo para manejar errores y pasar los datos a la vista.
     * @return Redirección a la lista de subcategorías.
     */
    @PostMapping("/{id}")
    public String actualizarSubcategoria(@PathVariable Long id, @ModelAttribute("subcategoria") Subcategoria subcategoria, Model model) {
        try {
            subcategoriaService.actualizarSubcategoriaPorId(subcategoria, id);
            return "redirect:/subcategorias";
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage", "Error al actualizar la subcategoría: " + e.getMessage());
            model.addAttribute("showErrorModal", true); // Bandera para mostrar el modal de error
            model.addAttribute("categorias", categoriaService.obtenerCategorias()); // Lista de categorías para volver a mostrar
            return "subcategorias/editar-subcategoria"; // Regresar al formulario en caso de error
        }
    }

    /**
     * Elimina una subcategoría por su ID.
     *
     * @param id El ID de la subcategoría a eliminar.
     * @return Redirección a la lista de subcategorías.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarSubcategoria(@PathVariable Long id) {
        subcategoriaService.eliminarSubcategoria(id);
        return "redirect:/subcategorias";
    }
}
