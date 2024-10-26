package com.farmacia_web.farmacia_web.services;

import com.farmacia_web.farmacia_web.models.Proveedor;
import com.farmacia_web.farmacia_web.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Servicio para manejar operaciones CRUD de la entidad Proveedor.
 */
@Service
public class ProveedorService {
    @Autowired
    ProveedorRepository proveedorRepository;

    /**
     * Obtiene la lista de todos los proveedores.
     *
     * @return Lista de objetos Proveedor.
     * @throws RuntimeException si ocurre un error al obtener los proveedores.
     */
    public ArrayList<Proveedor> obtenerProveedores() {
        try {
            return (ArrayList<Proveedor>) proveedorRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de proveedores: " + e.getMessage());
        }
    }

    /**
     * Crea un nuevo proveedor en la base de datos.
     *
     * @param proveedor Objeto Proveedor a crear.
     * @return El proveedor creado.
     * @throws RuntimeException si ocurre un error al crear el proveedor.
     */
    public Proveedor crearProveedor(Proveedor proveedor) {
        try {
            return proveedorRepository.save(proveedor);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el proveedor: " + e.getMessage());
        }
    }

    /**
     * Obtiene un proveedor por su ID.
     *
     * @param id ID del proveedor a obtener.
     * @return El proveedor encontrado.
     * @throws NoSuchElementException si el proveedor con el ID especificado no existe.
     * @throws RuntimeException si ocurre un error al obtener el proveedor.
     */
    public Proveedor obtenerPorId(Long id) {
        try {
            Optional<Proveedor> proveedor = proveedorRepository.findById(id);
            if (proveedor.isPresent()) {
                return proveedor.get();
            } else {
                throw new NoSuchElementException("Proveedor con ID " + id + " no encontrado");
            }
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener proveedor con ID " + id + ": " + e.getMessage());
        }
    }

    /**
     * Actualiza un proveedor existente por su ID.
     *
     * @param request Objeto Proveedor con la información actualizada.
     * @param id ID del proveedor a actualizar.
     * @return El proveedor actualizado.
     * @throws NoSuchElementException si el proveedor con el ID especificado no existe.
     * @throws RuntimeException si ocurre un error al actualizar el proveedor.
     */
    public Proveedor actualizarProveedorPorId(Proveedor request, Long id) {
        try {
            Proveedor proveedor = proveedorRepository.findById(id).orElseThrow(() ->
                    new NoSuchElementException("Proveedor con ID " + id + " no encontrado")
            );
            proveedor.setNombre(request.getNombre());
            proveedor.setEmail(request.getEmail());
            proveedor.setTelefono(request.getTelefono());
            proveedor.setDescripcion(request.getDescripcion());
            proveedor.setCiudad(request.getCiudad());
            return proveedorRepository.save(proveedor);
        } catch (NoSuchElementException e) {
            throw e;  // Lanzamos esta excepción específica
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar proveedor con ID " + id + ": " + e.getMessage());
        }
    }

    /**
     * Elimina un proveedor por su ID.
     *
     * @param id ID del proveedor a eliminar.
     * @return true si el proveedor fue eliminado correctamente.
     * @throws NoSuchElementException si el proveedor con el ID especificado no existe.
     * @throws RuntimeException si ocurre un error al eliminar el proveedor.
     */
    public boolean eliminarProveedor(Long id) {
        try {
            if (!proveedorRepository.existsById(id)) {
                throw new NoSuchElementException("Proveedor con ID " + id + " no encontrado");
            }
            proveedorRepository.deleteById(id);
            return true;
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar proveedor con ID " + id + ": " + e.getMessage());
        }
    }
}


