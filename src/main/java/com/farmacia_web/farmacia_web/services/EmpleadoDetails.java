package com.farmacia_web.farmacia_web.services;

import com.farmacia_web.farmacia_web.models.Empleado;
import com.farmacia_web.farmacia_web.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmpleadoDetailsService implements UserDetailsService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Empleado empleado = empleadoRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email no encontrado: " + email));

        // Verificar si el usuario está activo
        if (!empleado.getActivo()) {
            throw new DisabledException("La cuenta no está activada. Regístrate primero.");
        }

        // Convertir el Empleado a un objeto UserDetails para Spring Security
        return org.springframework.security.core.userdetails.User
                .withUsername(empleado.getEmail()) // Usar 'empleado' en lugar de 'user'
                .password(empleado.getContrasena()) // Asegúrate de usar el método correcto para obtener la contraseña
                .roles(empleado.getRol()) // Asumiendo que 'getRoles()' devuelve una lista de objetos 'Role' y 'getName()' devuelve el nombre del rol
                .build();
    }
}
