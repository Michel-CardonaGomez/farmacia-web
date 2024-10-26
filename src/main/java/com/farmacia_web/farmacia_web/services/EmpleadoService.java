package com.farmacia_web.farmacia_web.services;

import com.farmacia_web.farmacia_web.models.Empleado;
import com.farmacia_web.farmacia_web.repositories.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Servicio para manejar operaciones CRUD de la entidad Empleado.
 */
@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    /**
     * Obtiene la lista de todos los empleados.
     *
     * @return Lista de objetos Empleado.
     * @throws RuntimeException si ocurre un error al obtener los empleados.
     */
    public ArrayList<Empleado> obtenerEmpleados() {
        try {
            return (ArrayList<Empleado>) empleadoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de empleados: " + e.getMessage());
        }
    }

    /**
     * Crea un nuevo empleado en la base de datos y lo establece como inactivo inicialmente.
     *
     * @param empleado Objeto Empleado a crear.
     * @return El empleado creado.
     * @throws RuntimeException si ocurre un error al crear el empleado.
     */
    public Empleado crearEmpleado(Empleado empleado) {
        try {
            empleado.setActivo(false);
            return empleadoRepository.save(empleado);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el empleado: " + e.getMessage());
        }
    }

    /**
     * Obtiene un empleado por su ID.
     *
     * @param id ID del empleado a obtener.
     * @return El empleado encontrado.
     * @throws NoSuchElementException si el empleado con el ID especificado no existe.
     * @throws RuntimeException si ocurre un error al obtener el empleado.
     */
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

    /**
     * Actualiza un empleado existente por su ID.
     *
     * @param request Objeto Empleado con la información actualizada.
     * @param id ID del empleado a actualizar.
     * @return El empleado actualizado.
     * @throws NoSuchElementException si el empleado con el ID especificado no existe.
     * @throws RuntimeException si ocurre un error al actualizar el empleado.
     */
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
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar empleado con ID " + id + ": " + e.getMessage());
        }
    }

    /**
     * Elimina un empleado por su ID.
     *
     * @param id ID del empleado a eliminar.
     * @return true si el empleado fue eliminado correctamente.
     * @throws NoSuchElementException si el empleado con el ID especificado no existe.
     * @throws RuntimeException si ocurre un error al eliminar el empleado.
     */
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
