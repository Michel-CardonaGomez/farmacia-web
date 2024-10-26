package com.farmacia_web.farmacia_web.services;

import com.farmacia_web.farmacia_web.models.Categoria;
import com.farmacia_web.farmacia_web.models.Subcategoria;
import com.farmacia_web.farmacia_web.repositories.CategoriaRepository;
import com.farmacia_web.farmacia_web.repositories.SubcategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class SubcategoriaService {

    @Autowired
    private SubcategoriaRepository subcategoriaRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    /**
     * Obtiene una lista de todas las subcategorías.
     *
     * @return Lista de subcategorías.
     */
    public ArrayList<Subcategoria> obtenerSubcategorias() {
        try {
            return (ArrayList<Subcategoria>) subcategoriaRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de subcategorías: " + e.getMessage());
        }
    }

    /**
     * Crea una nueva subcategoría en la base de datos.
     *
     * @param subcategoria La subcategoría a crear.
     * @return La subcategoría creada.
     */
    public Subcategoria crearSubcategoria(Subcategoria subcategoria) {
        try {
            // Aquí debes establecer la categoría asociada para la clave foránea
            if (subcategoria.getCategoria() != null && subcategoria.getCategoria().getId() != null) {
                Categoria categoria = categoriaRepository.findById(subcategoria.getCategoria().getId())
                        .orElseThrow(() -> new NoSuchElementException("Categoría con ID " + subcategoria.getCategoria().getId() + " no encontrada"));
                subcategoria.setCategoria(categoria);
            }
            return subcategoriaRepository.save(subcategoria);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la subcategoría: " + e.getMessage());
        }
    }

    /**
     * Obtiene una subcategoría por su ID.
     *
     * @param id El ID de la subcategoría a obtener.
     * @return La subcategoría solicitada.
     */
    public Subcategoria obtenerPorId(Long id) {
        try {
            Optional<Subcategoria> subcategoria = subcategoriaRepository.findById(id);
            if (subcategoria.isPresent()) {
                return subcategoria.get();
            } else {
                throw new NoSuchElementException("Subcategoría con ID " + id + " no encontrada");
            }
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener subcategoría con ID " + id + ": " + e.getMessage());
        }
    }

    /**
     * Actualiza una subcategoría existente en la base de datos.
     *
     * @param request La subcategoría con los nuevos datos.
     * @param id      El ID de la subcategoría a actualizar.
     * @return La subcategoría actualizada.
     */
    public Subcategoria actualizarSubcategoriaPorId(Subcategoria request, Long id) {
        try {
            Subcategoria subcategoria = subcategoriaRepository.findById(id).orElseThrow(() ->
                    new NoSuchElementException("Subcategoría con ID " + id + " no encontrada")
            );
            Categoria categoria = new Categoria();
            subcategoria.setId(request.getId());
            subcategoria.setNombre(request.getNombre());
            categoria.setId(request.getCategoria().getId());
            subcategoria.setCategoria(categoria);
            subcategoriaRepository.save(subcategoria);

            return subcategoriaRepository.save(subcategoria);
        } catch (NoSuchElementException e) {
            throw e;  // Lanzamos esta excepción específica
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar subcategoría con ID " + id + ": " + e.getMessage());
        }
    }

    /**
     * Elimina una subcategoría por su ID.
     *
     * @param id El ID de la subcategoría a eliminar.
     * @return true si se eliminó correctamente, false de lo contrario.
     */
    public boolean eliminarSubcategoria(Long id) {
        try {
            if (!subcategoriaRepository.existsById(id)) {
                throw new NoSuchElementException("Subcategoría con ID " + id + " no encontrada");
            }
            subcategoriaRepository.deleteById(id);
            return true;
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar subcategoría con ID " + id + ": " + e.getMessage());
        }
    }
}
