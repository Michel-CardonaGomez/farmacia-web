package com.farmacia_web.farmacia_web.controllers;

import com.farmacia_web.farmacia_web.models.Empleado;
import com.farmacia_web.farmacia_web.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Mostrar la página de registro
    @GetMapping
    public String mostrarFormularioDeRegistro(Model model) {
        model.addAttribute("empleado", new Empleado());
        return "registro";  // La plantilla 'registro.html' debe ser creada en templates
    }

    // Manejar la lógica de registro
    @PostMapping
    public String registrarEmpleado(@ModelAttribute("empleado") Empleado empleado, @RequestParam("contrasena") String contrasena, Model model) {
        // Buscar al empleado en la base de datos por correo electrónico
        Optional<Empleado> empleadoOptional = empleadoRepository.findByEmail(empleado.getEmail());

        if (!empleadoOptional.isPresent()) {
            // Si el correo no está registrado en la tabla empleados
            model.addAttribute("error", "Correo no registrado como empleado.");
            return "registro";
        }

        Empleado empleadoExistente = empleadoOptional.get();

        // Verificar si el empleado ya ha creado una cuenta
        if (empleadoExistente.getActivo()) {
            model.addAttribute("error", "Ya has creado tu cuenta.");
            return "registro";
        }


        // Actualizar los datos del empleado
        empleadoExistente.setContrasena(passwordEncoder.encode(contrasena)); // Encriptar la contraseña
        empleadoExistente.setActivo(true); // Cambiar el estado de 'activo' a true
        empleadoRepository.save(empleadoExistente); // Guardar los cambios en la base de datos

        return "redirect:/login?registroExitoso"; // Redirigir a la página de inicio de sesión
    }
}
