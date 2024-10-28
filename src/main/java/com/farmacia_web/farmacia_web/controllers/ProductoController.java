package com.farmacia_web.farmacia_web.controllers;

import com.farmacia_web.farmacia_web.models.Producto;
import com.farmacia_web.farmacia_web.services.MarcaService;
import com.farmacia_web.farmacia_web.services.ProductoService;
import com.farmacia_web.farmacia_web.services.ProveedorService;
import com.farmacia_web.farmacia_web.services.SubcategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private SubcategoriaService subcategoriaService;

    /**
     * Muestra una lista de todos los productos junto con los formularios para crear o editar un producto.
     *
     * @param model El modelo que se utilizará para pasar datos a la vista.
     * @return La vista que muestra la lista de productos y formularios.
     */
    @GetMapping
    public String listarProductos(Model model) {
        model.addAttribute("productos", productoService.obtenerProductos());
        model.addAttribute("marcas", marcaService.obtenerMarcas());
        model.addAttribute("proveedores", proveedorService.obtenerProveedores());
        model.addAttribute("subcategorias", subcategoriaService.obtenerSubcategorias());
        model.addAttribute("producto", new Producto());
        return "entidades/productos"; // Nombre de la vista para listar productos y formularios
    }

    /**
     * Crea un nuevo producto y lo guarda en la base de datos.
     *
     * @param producto El nuevo producto a crear.
     * @param model El modelo para manejar mensajes de éxito o error.
     * @return Redirección a la lista de productos después de crearla.
     */
    @PostMapping
    public String crearProducto(@ModelAttribute("producto") Producto producto, Model model) {
        try {
            productoService.crearProducto(producto);
            model.addAttribute("successMessage", "Producto creado con éxito.");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al guardar el producto: " + e.getMessage());
        }
        return "redirect:/productos";
    }

    /**
     * Muestra el formulario para editar un producto existente.
     *
     * @param id    El ID del producto a editar.
     * @param model El modelo que contiene el producto a editar y las listas necesarias.
     * @return La vista que muestra la lista de productos y el formulario de edición.
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        Producto producto = productoService.obtenerProductoPorId(id);
        if (producto != null) {
            model.addAttribute("producto", producto);
        } else {
            model.addAttribute("errorMessage", "Producto no encontrado.");
        }
        model.addAttribute("marcas", marcaService.obtenerMarcas());
        model.addAttribute("proveedores", proveedorService.obtenerProveedores());
        model.addAttribute("subcategorias", subcategoriaService.obtenerSubcategorias());
        model.addAttribute("productos", productoService.obtenerProductos());
        return "entidades/productos"; // Nombre de la vista para listar productos y formularios
    }

    /**
     * Actualiza un producto existente en la base de datos con los nuevos datos proporcionados.
     *
     * @param id    El ID del producto a actualizar.
     * @param producto El producto con los datos actualizados.
     * @param model El modelo para manejar mensajes de éxito o error.
     * @return Redirección a la lista de productos después de actualizarlo.
     */
    @PostMapping("/{id}")
    public String actualizarProducto(@PathVariable Long id, @ModelAttribute("producto") Producto producto, Model model) {
        try {
            productoService.actualizarProducto(producto, id);
            model.addAttribute("successMessage", "Producto actualizado con éxito.");

        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error al actualizar el producto: " + e.getMessage());
            return "inicio";
        }

        return "redirect:/productos";
    }

    /**
     * Elimina un producto específico de la base de datos.
     *
     * @param id El ID del producto a eliminar.
     * @return Redirección a la lista de productos después de eliminarlo.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        productoService.eliminarProducto(id);
        return "redirect:/productos"; // Redirige a la lista de productos
    }
}
