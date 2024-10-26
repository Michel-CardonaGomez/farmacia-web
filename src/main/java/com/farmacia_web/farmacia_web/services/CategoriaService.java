package com.farmacia_web.farmacia_web.services;

import com.farmacia_web.farmacia_web.models.Categoria;
import com.farmacia_web.farmacia_web.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Servicio para manejar operaciones CRUD de la entidad Categoria.
 */
@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * Obtiene la lista de todas las categorías.
     *
     * @return Lista de objetos Categoria.
     * @throws RuntimeException si ocurre un error al obtener las categorías.
     */
    public ArrayList<Categoria> obtenerCategorias() {
        try {
            return (ArrayList<Categoria>) categoriaRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de categorias: " + e.getMessage());
        }
    }

    /**
     * Crea una nueva categoría en la base de datos.
     *
     * @param categoria Objeto Categoria a crear.
     * @return La categoría creada.
     * @throws RuntimeException si ocurre un error al crear la categoría.
     */
    public Categoria crearCategoria(Categoria categoria) {
        try {
            return categoriaRepository.save(categoria);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la categoria: " + e.getMessage());
        }
    }

    /**
     * Obtiene una categoría por su ID.
     *
     * @param id ID de la categoría a obtener.
     * @return La categoría encontrada.
     * @throws NoSuchElementException si la categoría con el ID especificado no existe.
     * @throws RuntimeException si ocurre un error al obtener la categoría.
     */
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

    /**
     * Actualiza una categoría existente por su ID.
     *
     * @param request Objeto Categoria con la información actualizada.
     * @param id ID de la categoría a actualizar.
     * @return La categoría actualizada.
     * @throws NoSuchElementException si la categoría con el ID especificado no existe.
     * @throws RuntimeException si ocurre un error al actualizar la categoría.
     */
    public Categoria actualizarCategoriaPorId(Categoria request, Long id) {
        try {
            Categoria categoria = categoriaRepository.findById(id).orElseThrow(() ->
                    new NoSuchElementException("Categoria con ID " + id + " no encontrada")
            );
            categoria.setNombre(request.getNombre());
            return categoriaRepository.save(categoria);
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar categoria con ID " + id + ": " + e.getMessage());
        }
    }

    /**
     * Elimina una categoría por su ID.
     *
     * @param id ID de la categoría a eliminar.
     * @return true si la categoría fue eliminada correctamente.
     * @throws NoSuchElementException si la categoría con el ID especificado no existe.
     * @throws RuntimeException si ocurre un error al eliminar la categoría.
     */
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

