package com.farmacia_web.farmacia_web.services;

import com.farmacia_web.farmacia_web.models.Marca;
import com.farmacia_web.farmacia_web.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    public ArrayList<Marca> obtenerMarcas() {
        try {
            return (ArrayList<Marca>) marcaRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de marcas: " + e.getMessage());
        }
    }

    public Marca crearMarca(Marca marca) {
        try {
            return marcaRepository.save(marca);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la marca: " + e.getMessage());
        }
    }

    public Marca obtenerPorId(Long id) {
        try {
            Optional<Marca> marca = marcaRepository.findById(id);
            if (marca.isPresent()) {
                return marca.get();
            } else {
                throw new NoSuchElementException("Marca con ID " + id + " no encontrada");
            }
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener marca con ID " + id + ": " + e.getMessage());
        }
    }

    public Marca actualizarMarcaPorId(Marca request, Long id) {
        try {
            Marca marca = marcaRepository.findById(id).orElseThrow(() ->
                    new NoSuchElementException("Marca con ID " + id + " no encontrada")
            );
            marca.setNombre(request.getNombre());
            // Actualiza otros campos si es necesario
            return marcaRepository.save(marca);
        } catch (NoSuchElementException e) {
            throw e;  // Lanzamos esta excepción específica
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar marca con ID " + id + ": " + e.getMessage());
        }
    }

    public boolean eliminarMarca(Long id) {
        try {
            if (!marcaRepository.existsById(id)) {
                throw new NoSuchElementException("Marca con ID " + id + " no encontrada");
            }
            marcaRepository.deleteById(id);
            return true;
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar marca con ID " + id + ": " + e.getMessage());
        }
    }
}
}
