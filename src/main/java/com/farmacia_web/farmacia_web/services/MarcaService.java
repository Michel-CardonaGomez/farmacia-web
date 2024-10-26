package com.farmacia_web.farmacia_web.services;

import com.farmacia_web.farmacia_web.models.Marca;
import com.farmacia_web.farmacia_web.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;
/**
 * Servicio para manejar operaciones CRUD de la entidad Marca.
 */
@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    /**
     * Obtiene la lista de todas las marcas.
     *
     * @return Lista de objetos Marca.
     * @throws RuntimeException si ocurre un error al obtener las marcas.
     */
    public ArrayList<Marca> obtenerMarcas() {
        try {
            return (ArrayList<Marca>) marcaRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de marcas: " + e.getMessage());
        }
    }

    /**
     * Crea una nueva marca en la base de datos.
     *
     * @param marca Objeto Marca a crear.
     * @return La marca creada.
     * @throws RuntimeException si ocurre un error al crear la marca.
     */
    public Marca crearMarca(Marca marca) {
        try {
            return marcaRepository.save(marca);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la marca: " + e.getMessage());
        }
    }

    /**
     * Obtiene una marca por su ID.
     *
     * @param id ID de la marca a obtener.
     * @return La marca encontrada.
     * @throws NoSuchElementException si la marca con el ID especificado no existe.
     * @throws RuntimeException si ocurre un error al obtener la marca.
     */
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

    /**
     * Actualiza una marca existente por su ID.
     *
     * @param request Objeto Marca con la información actualizada.
     * @param id ID de la marca a actualizar.
     * @return La marca actualizada.
     * @throws NoSuchElementException si la marca con el ID especificado no existe.
     * @throws RuntimeException si ocurre un error al actualizar la marca.
     */
    public Marca actualizarMarcaPorId(Marca request, Long id) {
        try {
            Marca marca = marcaRepository.findById(id).orElseThrow(() ->
                    new NoSuchElementException("Marca con ID " + id + " no encontrada")
            );
            marca.setNombre(request.getNombre());
            // Actualiza otros campos si es necesario
            return marcaRepository.save(marca);
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar marca con ID " + id + ": " + e.getMessage());
        }
    }

    /**
     * Elimina una marca por su ID.
     *
     * @param id ID de la marca a eliminar.
     * @return true si la marca fue eliminada correctamente.
     * @throws NoSuchElementException si la marca con el ID especificado no existe.
     * @throws RuntimeException si ocurre un error al eliminar la marca.
     */
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
