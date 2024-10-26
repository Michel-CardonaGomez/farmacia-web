package com.farmacia_web.farmacia_web.services;

import com.farmacia_web.farmacia_web.models.Empleado;
import com.farmacia_web.farmacia_web.models.EmpleadoDetails;
import com.farmacia_web.farmacia_web.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
/**
 * Servicio de detalles de empleado para la autenticación y autorización.
 * Implementa la interfaz UserDetailsService de Spring Security.
 */
@Service
public class EmpleadoDetailsService implements UserDetailsService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    /**
     * Carga un empleado por su correo electrónico (username) para su uso en autenticación.
     *
     * @param email Correo electrónico del empleado a cargar.
     * @return UserDetails del empleado encontrado.
     * @throws UsernameNotFoundException si no se encuentra un empleado con el correo electrónico especificado.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Empleado empleado = empleadoRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Empleado no encontrado"));

        return new EmpleadoDetails(empleado);
    }
}

