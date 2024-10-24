package com.farmacia_web.farmacia_web.services;

import com.farmacia_web.farmacia_web.models.Cliente;
import com.farmacia_web.farmacia_web.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public ArrayList<Cliente> obtenerClientes() {
        try {
            return (ArrayList<Cliente>) clienteRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de clientes: " + e.getMessage());
        }
    }

    public Cliente crearCliente(Cliente cliente) {
        try {
            return clienteRepository.save(cliente);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el cliente: " + e.getMessage());
        }
    }

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

    public Cliente actualizarClientePorId(Cliente request, Long id) {
        try {
            Cliente cliente = clienteRepository.findById(id).orElseThrow(() ->
                    new NoSuchElementException("Cliente con ID " + id + " no encontrado")
            );
            cliente.setIdentificacion(request.getIdentificacion());
            cliente.setNombre(request.getNombre());
            cliente.setEmail(request.getEmail());
            cliente.setTelefono(request.getTelefono());
            // No es necesario modificar las fechas aquí, ya que se actualizan automáticamente
            return clienteRepository.save(cliente);
        } catch (NoSuchElementException e) {
            throw e;  // Lanzamos esta excepción específica
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar cliente con ID " + id + ": " + e.getMessage());
        }
    }

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

