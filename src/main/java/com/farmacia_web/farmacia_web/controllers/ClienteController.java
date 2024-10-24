package com.farmacia_web.farmacia_web.controllers;

import org.springframework.ui.Model;
import com.farmacia_web.farmacia_web.models.Cliente;
import com.farmacia_web.farmacia_web.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.obtenerClientes());
        return "clientes"; // Nombre de la vista para listar clientes
    }

    @GetMapping("/crear")
    public String mostrarFormularioRegistrar(Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        return "crear-cliente"; // Nombre de la vista para crear un cliente
    }

    @PostMapping
    public String crearCliente(@ModelAttribute("cliente") Cliente cliente, Model model) {
        try {
            clienteService.crearCliente(cliente);
            return "redirect:/clientes";
        } catch (RuntimeException e) {
            // Capturar el error y pasarlo al modelo
            model.addAttribute("errorMessage", "Error al guardar el cliente: " + e.getMessage());
            model.addAttribute("showErrorModal", true); // Esta bandera indica que el modal debe mostrarse
            return "crear-cliente"; // Nombre de la vista para crear un cliente
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", clienteService.obtenerPorId(id));
        return "editar-cliente"; // Nombre de la vista para editar un cliente
    }

    @PostMapping("/{id}")
    public String actualizarCliente(@PathVariable Long id, @ModelAttribute("cliente") Cliente cliente, Model model) {
        clienteService.actualizarClientePorId(cliente, id);
        return "redirect:/clientes";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return "redirect:/clientes";
    }
}
