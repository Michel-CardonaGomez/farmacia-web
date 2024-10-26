package com.farmacia_web.farmacia_web.services;

import com.farmacia_web.farmacia_web.models.Proveedor;
import com.farmacia_web.farmacia_web.repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProveedorService {
    @Autowired
    ProveedorRepository proveedorRepository;

    public ArrayList<Proveedor> obtenerProveedores() {
        try {
            return (ArrayList<Proveedor>) proveedorRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de proveedores: " + e.getMessage());
        }
    }

    public Proveedor crearProveedor(Proveedor proveedor) {
        try {
            return proveedorRepository.save(proveedor);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el proveedor: " + e.getMessage());
        }
    }

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

