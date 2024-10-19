package com.farmacia_web.farmacia_web.controllers;

import com.farmacia_web.farmacia_web.models.Cliente;
import com.farmacia_web.farmacia_web.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> obtenerClientes() {
        return this.clienteService.obtenerClientes();
    }

    @PostMapping
    public Cliente guardarCliente(@RequestBody Cliente cliente) {
        return this.clienteService.crearCliente(cliente);
    }

    @GetMapping(path = "/{id}")
    public Cliente obtenerPorId(@PathVariable Long id) {
        return this.clienteService.obtenerPorId(id);
    }

    @PutMapping(path = "/{id}")
    public Cliente actualizarClientePorId(@RequestBody Cliente request, @PathVariable Long id) {
        return this.clienteService.actualizarCliente(id, request);
    }

    @DeleteMapping(path = "/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        boolean ok = this.clienteService.eliminarCliente(id);
        if (ok) {
            return "Cliente con id " + id + " eliminado";
        } else {
            throw new RuntimeException("Ha ocurrido un error al eliminar el cliente");
        }
    }
}
