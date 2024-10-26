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

    /**
     * Muestra la lista de clientes.
     *
     * @param model El modelo para pasar datos a la vista.
     * @return El nombre de la vista que muestra la lista de clientes.
     */
    @GetMapping
    public String listarClientes(Model model) {
        model.addAttribute("clientes", clienteService.obtenerClientes());
        return "clientes/clientes"; // Nombre de la vista para listar clientes
    }

    /**
     * Muestra el formulario para registrar un nuevo cliente.
     *
     * @param model El modelo para pasar datos a la vista.
     * @return El nombre de la vista que muestra el formulario de creación de cliente.
     */
    @GetMapping("/crear")
    public String mostrarFormularioRegistrar(Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        return "clientes/crear-cliente"; // Nombre de la vista para crear un cliente
    }

    /**
     * Crea un nuevo cliente y redirige a la lista de clientes.
     *
     * @param cliente El objeto cliente que se va a crear.
     * @param model   El modelo para pasar datos a la vista en caso de error.
     * @return La redirección a la lista de clientes o el formulario de creación con un mensaje de error.
     */
    @PostMapping
    public String crearCliente(@ModelAttribute("cliente") Cliente cliente, Model model) {
        try {
            clienteService.crearCliente(cliente);
            return "redirect:/clientes";
        } catch (RuntimeException e) {
            // Capturar el error y pasarlo al modelo
            model.addAttribute("errorMessage", "Error al guardar el cliente: " + e.getMessage());
            model.addAttribute("showErrorModal", true); // Esta bandera indica que el modal debe mostrarse
            return "clientes/crear-cliente"; // Nombre de la vista para crear un cliente
        }
    }

    /**
     * Muestra el formulario para editar un cliente existente.
     *
     * @param id    El ID del cliente a editar.
     * @param model El modelo para pasar datos a la vista.
     * @return El nombre de la vista que muestra el formulario de edición de cliente.
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("cliente", clienteService.obtenerPorId(id));
        return "clientes/editar-cliente"; // Nombre de la vista para editar un cliente
    }

    /**
     * Actualiza un cliente existente y redirige a la lista de clientes.
     *
     * @param id      El ID del cliente a actualizar.
     * @param cliente El objeto cliente con la información actualizada.
     * @param model   El modelo para pasar datos a la vista en caso de error.
     * @return La redirección a la lista de clientes.
     */
    @PostMapping("/{id}")
    public String actualizarCliente(@PathVariable Long id, @ModelAttribute("cliente") Cliente cliente, Model model) {
        clienteService.actualizarClientePorId(cliente, id);
        return "redirect:/clientes";
    }

    /**
     * Elimina un cliente existente y redirige a la lista de clientes.
     *
     * @param id El ID del cliente a eliminar.
     * @return La redirección a la lista de clientes.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return "redirect:/clientes";
    }
}
