//package com.farmacia_web.farmacia_web.services;
//
//import com.farmacia_web.farmacia_web.models.Cliente;
//import com.farmacia_web.farmacia_web.repositories.ClienteRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.Optional;
//
//@Service
//public class ClienteService {
//    @Autowired
//    ClienteRepository clienteRepository;
//
//    public Cliente crearCliente(Cliente request) {
//        try {
//            Cliente cliente = new Cliente();
//            cliente.setCedula(request.getCedula());
//            cliente.setNombre(request.getNombre());
//            cliente.setEmail(request.getEmail());
//            cliente.setTelefono(request.getTelefono());
//            return clienteRepository.save(cliente);
//        } catch (Exception e) {
//            throw new RuntimeException("Error al crear el cliente: " + e.getMessage());
//        }
//    }
//
//    public List<Cliente> obtenerClientes() {
//        try {
//            return clienteRepository.findAll();
//        } catch (Exception e) {
//            throw new RuntimeException("Error al obtener los clientes: " + e.getMessage());
//        }
//    }
//
//    public Cliente obtenerPorId(Long id) {
//        try {
//            Optional<Cliente> cliente = clienteRepository.findById(id);
//
//            if (cliente.isPresent()) {
//                return cliente.get();
//            } else {
//                throw new NoSuchElementException("Cliente con ID " + id + " no encontrado");
//            }
//        } catch (NoSuchElementException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new RuntimeException("Error al obtener cliente con ID " + id + ": " + e.getMessage());
//        }
//    }
//
//    public Cliente actualizarCliente(Long id, Cliente clienteActualizado) {
//        try {
//            if (!clienteRepository.existsById(id)) {
//                throw new RuntimeException("Cliente no encontrado con ID " + id);
//            }
//
//            return clienteRepository.save(clienteActualizado);
//        } catch (Exception e) {
//            throw new RuntimeException("Error al actualizar el cliente: " + e.getMessage());
//        }
//    }
//
//    public boolean eliminarCliente(Long id) {
//        try {
//            if (!clienteRepository.existsById(id)) {
//                throw new NoSuchElementException("Empleado con ID " + id + " no encontrado");
//            }
//            clienteRepository.deleteById(id);
//            return true;
//        } catch (NoSuchElementException e) {
//            throw e;
//        } catch (Exception e) {
//            throw new RuntimeException("Error al eliminar empleado con ID " + id + ": " + e.getMessage());
//        }
//    }
//}

