package com.farmacia_web.farmacia_web.services;

import com.farmacia_web.farmacia_web.models.Empleado;
import com.farmacia_web.farmacia_web.models.EmpleadoDetails;
import com.farmacia_web.farmacia_web.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
                .orElseThrow(() -> new UsernameNotFoundException("Empleado no encontrado"));

        return new EmpleadoDetails(empleado);
    }
}
