package com.farmacia_web.farmacia_web.services;

import com.farmacia_web.farmacia_web.models.Cliente;
import com.farmacia_web.farmacia_web.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Servicio para manejar operaciones CRUD de la entidad Cliente.
 */
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Obtiene la lista de todos los clientes.
     *
     * @return Lista de objetos Cliente.
     * @throws RuntimeException si ocurre un error al obtener los clientes.
     */
    public ArrayList<Cliente> obtenerClientes() {
        try {
            return (ArrayList<Cliente>) clienteRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de clientes: " + e.getMessage());
        }
    }

    /**
     * Crea un nuevo cliente en la base de datos.
     *
     * @param cliente Objeto Cliente a crear.
     * @return El cliente creado.
     * @throws RuntimeException si ocurre un error al crear el cliente.
     */
    public Cliente crearCliente(Cliente cliente) {
        try {
            return clienteRepository.save(cliente);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el cliente: " + e.getMessage());
        }
    }

    /**
     * Obtiene un cliente por su ID.
     *
     * @param id ID del cliente a obtener.
     * @return El cliente encontrado.
     * @throws NoSuchElementException si el cliente con el ID especificado no existe.
     * @throws RuntimeException si ocurre un error al obtener el cliente.
     */
    public Cliente obtenerPorId(Long id) {
        try {
            Optional<Cliente> cliente = clienteRepository.findById(id);
            if (cliente.isPresent()) {
                return cliente.get();
            } else {
                throw new NoSuchElementException("Cliente con ID " + id + " no encontrado");
            }
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener cliente con ID " + id + ": " + e.getMessage());
        }
    }

    /**
     * Actualiza un cliente existente por su ID.
     *
     * @param request Objeto Cliente con la información actualizada.
     * @param id ID del cliente a actualizar.
     * @return El cliente actualizado.
     * @throws NoSuchElementException si el cliente con el ID especificado no existe.
     * @throws RuntimeException si ocurre un error al actualizar el cliente.
     */
    public Cliente actualizarClientePorId(Cliente request, Long id) {
        try {
            Cliente cliente = clienteRepository.findById(id).orElseThrow(() ->
                    new NoSuchElementException("Cliente con ID " + id + " no encontrado")
            );
            cliente.setId(request.getId());
            cliente.setIdentificacion(request.getIdentificacion());
            cliente.setNombre(request.getNombre());
            cliente.setEmail(request.getEmail());
            cliente.setTelefono(request.getTelefono());
            return clienteRepository.save(cliente);
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar cliente con ID " + id + ": " + e.getMessage());
        }
    }

    /**
     * Elimina un cliente por su ID.
     *
     * @param id ID del cliente a eliminar.
     * @return true si el cliente fue eliminado correctamente.
     * @throws NoSuchElementException si el cliente con el ID especificado no existe.
     * @throws RuntimeException si ocurre un error al eliminar el cliente.
     */
    public boolean eliminarCliente(Long id) {
        try {
            if (!clienteRepository.existsById(id)) {
                throw new NoSuchElementException("Cliente con ID " + id + " no encontrado");
            }
            clienteRepository.deleteById(id);
            return true;
        } catch (NoSuchElementException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar cliente con ID " + id + ": " + e.getMessage());
        }
    }
}
