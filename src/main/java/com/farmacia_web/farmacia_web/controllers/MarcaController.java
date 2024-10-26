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
     * Obtiene una lista de todas las marcas y muestra la vista con los formularios.
     *
     * @param model El modelo para pasar los datos a la vista.
     * @return La vista con la lista de marcas y formularios.
     */
    @GetMapping
    public String listarMarcas(Model model) {
        model.addAttribute("marca", new Marca());
        model.addAttribute("marcas", marcaService.obtenerMarcas());
        return "marcas/marcas"; // Nombre de la vista para listar marcas y formularios
    }

    /**
     * Crea una nueva marca en la base de datos.
     *
     * @param marca La marca a crear.
     * @param model El modelo para manejar errores y pasar los datos a la vista.
     * @return Redirección a la lista de marcas o a la vista con errores.
     */
    @PostMapping
    public String crearMarca(@ModelAttribute("marca") Marca marca, Model model) {
        try {
            marcaService.crearMarca(marca);
            model.addAttribute("successMessage", "Marca creada con éxito.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al guardar la marca: " + e.getMessage());
        }
        // Retorna a la misma vista para mostrar la lista de marcas y formularios nuevamente
        model.addAttribute("marcas", marcaService.obtenerMarcas());
        return "marcas/marcas"; // Nombre de la vista para listar marcas y formularios
    }

    /**
     * Muestra el formulario para editar una marca existente.
     *
     * @param id    El ID de la marca a editar.
     * @param model El modelo para pasar los datos de la marca a la vista.
     * @return La vista con la lista de marcas y el formulario de edición.
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Marca marca = marcaService.obtenerPorId(id); // Obtiene la marca por ID
        if (marca != null) {
            model.addAttribute("marca", marca); // Agrega la marca al modelo para editar
        } else {
            model.addAttribute("errorMessage", "Marca no encontrada.");
        }
        model.addAttribute("marcas", marcaService.obtenerMarcas()); // Mantiene la lista de marcas
        return "marcas/marcas"; // Nombre de la vista para listar marcas y formularios
    }

    /**
     * Actualiza una marca existente en la base de datos.
     *
     * @param id    El ID de la marca a actualizar.
     * @param marca La marca con los nuevos datos.
     * @param model El modelo para manejar errores y pasar los datos a la vista.
     * @return Redirección a la lista de marcas o a la vista con errores.
     */
    @PostMapping("/{id}")
    public String actualizarMarca(@PathVariable Long id, @ModelAttribute("marca") Marca marca, Model model) {
        try {
            marcaService.actualizarMarcaPorId(marca, id);
            model.addAttribute("successMessage", "Marca actualizada con éxito.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al actualizar la marca: " + e.getMessage());
        }
        // Retorna a la misma vista para mostrar la lista de marcas y formularios nuevamente
        model.addAttribute("marcas", marcaService.obtenerMarcas());
        return "marcas/marcas"; // Nombre de la vista para listar marcas y formularios
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


