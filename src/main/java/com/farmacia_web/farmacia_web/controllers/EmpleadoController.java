package com.farmacia_web.farmacia_web.controllers;


import org.springframework.ui.Model;
import com.farmacia_web.farmacia_web.models.Empleado;
import com.farmacia_web.farmacia_web.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    /**
     * Obtiene una lista de todos los empleados.
     *
     * @return Lista de empleados.
     */
    @GetMapping
    public String listarEmpleados(Model model) {
        model.addAttribute("empleados", empleadoService.obtenerEmpleados());
        return "empleados/empleados";
    }
    @GetMapping("/crear")
    public String MostrarFormularioRegistrar(Model model) {
        Empleado empleado = new Empleado();
        model.addAttribute("empleado", empleado);
        return "empleados/crear-empleado";
    }

    @PostMapping
    public String crearEmpleado(@ModelAttribute("empleado") Empleado empleado, Model model) {
        try {
            empleadoService.crearEmpleado(empleado);
            return "redirect:/admin/empleados";
        } catch (RuntimeException e) {
            // Capturar el error y pasarlo al modelo
            model.addAttribute("errorMessage", "Error al guardar el empleado: " + e.getMessage());
            model.addAttribute("showErrorModal", true); // Esta bandera indica que el modal debe mostrarse
            return "empleados/crear-empleado";
        }
    }
    @GetMapping("/editar/{id}")
    public String MostrarFormularioEditar (@PathVariable Long id, Model model) {
        model.addAttribute("empleado", empleadoService.obtenerPorId(id));
        return "empleados/editar-empleado";
    }

    @PostMapping("/{id}")
    public String ActualizarEmpleado(@PathVariable Long id, @ModelAttribute("empleado") Empleado empleado, Model model) {
        empleadoService.actualizarEmpleadoPorId(empleado, id);
        return "redirect:/admin/empleados";
    }


    /**
     * Elimina un empleado por su ID.
     *
     * @param id El ID del empleado a eliminar.
     * @return Mensaje de éxito.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
        return "redirect:/admin/empleados";
    }
}
