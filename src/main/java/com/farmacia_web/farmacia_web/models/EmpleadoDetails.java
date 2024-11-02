package com.farmacia_web.farmacia_web.models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class EmpleadoDetails implements UserDetails {

    private Empleado empleado;

    public EmpleadoDetails(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getName() {
        return empleado.getNombre();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_" + empleado.getRol().toUpperCase();
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return empleado.getContrasena();
    }


    @Override
    public String getUsername() {
        return empleado.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // No implementamos lógica de expiración de cuenta
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // No implementamos lógica de cuenta bloqueada
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // No implementamos lógica de expiración de credenciales
    }

    @Override
    public boolean isEnabled() {
        return empleado.getActivo(); // Solo los empleados activos pueden loguearse
    }
}
