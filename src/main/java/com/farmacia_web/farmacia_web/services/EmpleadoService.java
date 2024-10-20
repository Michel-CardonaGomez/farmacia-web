package com.farmacia_web.farmacia_web.services;

import com.farmacia_web.farmacia_web.models.Empleado;
import com.farmacia_web.farmacia_web.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmpleadoService {
    @Autowired
    EmpleadoRepository empleadoRepository;


    public ArrayList<Empleado> obtenerEmpleados() {
        try {
            return (ArrayList<Empleado>) empleadoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de empleados: " + e.getMessage());
        }
    }


    public Empleado crearEmpleado(Empleado empleado) {
        try {
            empleado.setActivo(false);
            return empleadoRepository.save(empleado);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el empleado: " + e.getMessage());
        }
    }


    public Empleado obtenerPorId(Long id) {
        try {
            Optional<Empleado> empleado = empleadoRepository.findById(id);

            if (empleado.isPresent()) {
                return empleado.get();
            } else {
                throw new NoSuchElementException("Empleado con ID " + id + " no encontrado");
            }
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener empleado con ID " + id + ": " + e.getMessage());
        }
    }

    public Empleado actualizarEmpleadoPorId(Empleado request, Long id) {
        try {
            Empleado empleado = empleadoRepository.findById(id).orElseThrow(() ->
                    new NoSuchElementException("Empleado con ID " + id + " no encontrado")
            );
            empleado.setId(request.getId());
            empleado.setNombre(request.getNombre());
            empleado.setIdentificacion(request.getIdentificacion());
            empleado.setTelefono(request.getTelefono());
            empleado.setEmail(request.getEmail());
            empleado.setRol(request.getRol());
            empleado.setActivo(empleado.getActivo());
            return empleadoRepository.save(empleado);
        } catch (NoSuchElementException e) {
            throw e;  // Lanzamos esta excepción específica
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar empleado con ID " + id + ": " + e.getMessage());
        }
    }

    public boolean eliminarEmpleado(Long id) {
        try {
            if (!empleadoRepository.existsById(id)) {
                throw new NoSuchElementException("Empleado con ID " + id + " no encontrado");
            }
            empleadoRepository.deleteById(id);
            return true;
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar empleado con ID " + id + ": " + e.getMessage());
        }
    }
}
