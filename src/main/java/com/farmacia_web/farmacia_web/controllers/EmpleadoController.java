package com.farmacia_web.farmacia_web.controllers;


import org.springframework.ui.Model;
import com.farmacia_web.farmacia_web.models.Empleado;
import com.farmacia_web.farmacia_web.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/empleados")
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
        return "empleados";
    }


    /**
     * Crea un nuevo empleado.
     *
     * @param empleado El objeto Empleado que se va a crear.
     * @return El empleado creado.
     */
    @PostMapping("/crear")
    public String guardarEmpleado(Empleado empleado, Model model) {
        String resultado = empleadoService.crearEmpleado(empleado);

        model.addAttribute("mensaje", resultado); // Añadir un mensaje de éxito o error
        return "redirect:/empleados"; // Redirigir a la lista de empleados o a otra vista
    }


    /**
     * Obtiene un empleado por su ID.
     *
     * @param id El ID del empleado a buscar.
     * @return El empleado encontrado.
     */
    @GetMapping(path = "/{id}")
    public Empleado obtenerPorId(@PathVariable Long id) {
        return this.empleadoService.obtenerPorId(id);
    }

    /**
     * Actualiza un empleado existente.
     *
     * @param request El objeto Empleado con los datos actualizados.
     * @param id      El ID del empleado a actualizar.
     * @return El empleado actualizado.
     */
    @PutMapping(path = "/{id}")
    public Empleado actualizarEmpleadoPorId(@RequestBody Empleado request, @PathVariable Long id) {
        return this.empleadoService.actualizarEmpleadoPorId(request, id);
    }

    /**
     * Elimina un empleado por su ID.
     *
     * @param id El ID del empleado a eliminar.
     * @return Mensaje de éxito.
     */
    @DeleteMapping(path = "/{id}")
    public String eliminarEmpleado(@PathVariable Long id) {
        boolean ok = this.empleadoService.eliminarEmpleado(id);
        if (ok) {
            return "Empleado con id " + id + " eliminado";
        } else {
            throw new RuntimeException("ha ocurrido un error al eliminar el empleado");
        }
    }
}
