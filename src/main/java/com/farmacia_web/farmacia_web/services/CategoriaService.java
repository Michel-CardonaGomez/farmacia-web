package com.farmacia_web.farmacia_web.services;

import com.farmacia_web.farmacia_web.models.Categoria;
import com.farmacia_web.farmacia_web.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public ArrayList<Categoria> obtenerCategorias() {
        try {
            return (ArrayList<Categoria>) categoriaRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de categorias: " + e.getMessage());
        }
    }

    public Categoria crearCategoria(Categoria categoria) {
        try {
            return categoriaRepository.save(categoria);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la categoria: " + e.getMessage());
        }
    }

    public Categoria obtenerPorId(Long id) {
        try {
            Optional<Categoria> categoria = categoriaRepository.findById(id);
            if (categoria.isPresent()) {
                return categoria.get();
            } else {
                throw new NoSuchElementException("Categoria con ID " + id + " no encontrada");
            }
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener categoria con ID " + id + ": " + e.getMessage());
        }
    }

    public Categoria actualizarCategoriaPorId(Categoria request, Long id) {
        try {
            Categoria categoria = categoriaRepository.findById(id).orElseThrow(() ->
                    new NoSuchElementException("Categoria con ID " + id + " no encontrada")
            );
            categoria.setNombre(request.getNombre());
            // Actualiza otros campos si es necesario
            return categoriaRepository.save(categoria);
        } catch (NoSuchElementException e) {
            throw e;  // Lanzamos esta excepción específica
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar categoria con ID " + id + ": " + e.getMessage());
        }
    }

    public boolean eliminarCategoria(Long id) {
        try {
            if (!categoriaRepository.existsById(id)) {
                throw new NoSuchElementException("Categoria con ID " + id + " no encontrada");
            }
            categoriaRepository.deleteById(id);
            return true;
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar categoria con ID " + id + ": " + e.getMessage());
        }
    }
}

