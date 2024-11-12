package com.farmacia_web.farmacia_web.services;

import com.farmacia_web.farmacia_web.models.Marca;
import com.farmacia_web.farmacia_web.models.Producto;
import com.farmacia_web.farmacia_web.models.Proveedor;
import com.farmacia_web.farmacia_web.models.Subcategoria;
import com.farmacia_web.farmacia_web.repositories.MarcaRepository;
import com.farmacia_web.farmacia_web.repositories.ProductoRepository;
import com.farmacia_web.farmacia_web.repositories.ProveedorRepository;
import com.farmacia_web.farmacia_web.repositories.SubcategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private SubcategoriaRepository subcategoriaRepository;
    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private ProveedorRepository proveedorRepository;

    /**
     * Obtiene la lista completa de productos.
     *
     * @return ArrayList con todos los productos existentes.
     * @throws RuntimeException si ocurre un error al obtener los productos.
     */
    public ArrayList<Producto> obtenerProductos() {
        try {
            return (ArrayList<Producto>) productoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de productos: " + e.getMessage());
        }
    }

    /**
     * Crea un nuevo producto en la base de datos y lo guarda con el estado "Disponible".
     *
     * @param producto el producto a crear.
     * @return el producto creado.
     * @throws RuntimeException si ocurre un error al crear el producto.
     */
    public Producto crearProducto(Producto producto) {
        try {

            producto.setExistencias(0);
            producto.setEstado("Inactivo"); // Establece el estado inicial del producto como "Disponible".
            return productoRepository.save(producto);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el producto: " + e.getMessage());
        }
    }

    /**
     * Obtiene un producto por su ID.
     *
     * @param id el ID del producto.
     * @return el producto encontrado, o lanza una excepción si no se encuentra.
     * @throws RuntimeException si ocurre un error al obtener el producto.
     */
    public Producto obtenerProductoPorId(Long id) {
        try {
            Optional<Producto> producto = productoRepository.findById(id);
            return producto.orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar el producto: " + e.getMessage());
        }
    }

    /**
     * Actualiza la información de un producto existente.
     *
     * @param request el producto con los datos actualizados.
     * @return el producto actualizado.
     * @throws RuntimeException si ocurre un error al actualizar el producto.
     */
    public Producto actualizarProducto(Producto request, Long id) {
        try {
            Producto producto = productoRepository.findById(id).orElseThrow(() ->
                    new NoSuchElementException("Producto con ID " + id + " no encontrado")
            );

            producto.setId(request.getId());
            producto.setNombre(request.getNombre());
            producto.setDescripcion(request.getDescripcion());
            producto.setCodigo(request.getCodigo());
            producto.setExistencias(request.getExistencias());
            producto.setPresentacion(request.getPresentacion());
            producto.setIva(request.getIva());
            producto.setEstado(request.getEstado());
            producto.setPrecioCompra(request.getPrecioCompra());
            producto.setPrecioVenta(request.getPrecioVenta());

            // Actualizar referencias a entidades relacionadas
            Marca marca = marcaRepository.findById(request.getMarca().getId())
                    .orElseThrow(() -> new NoSuchElementException("Marca con ID " + request.getMarca().getId() + " no encontrada"));
            Proveedor proveedor = proveedorRepository.findById(request.getProveedor().getId())
                    .orElseThrow(() -> new NoSuchElementException("Proveedor con ID " + request.getProveedor().getId() + " no encontrado"));
            Subcategoria subcategoria = subcategoriaRepository.findById(request.getSubcategoria().getId())
                    .orElseThrow(() -> new NoSuchElementException("Subcategoría con ID " + request.getSubcategoria().getId() + " no encontrada"));

            // Asignar las entidades encontradas
            producto.setMarca(marca);
            producto.setProveedor(proveedor);
            producto.setSubcategoria(subcategoria);

            // Guardar el producto actualizado
            return productoRepository.save(producto);
        } catch (NoSuchElementException e) {
            throw e; // Lanza la excepción si el producto o entidad no se encuentra
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar producto con ID " + id + ": " + e.getMessage());
        }
    }



    /**
     * Elimina un producto por su ID.
     *
     * @param id el ID del producto a eliminar.
     * @throws RuntimeException si ocurre un error al eliminar el producto.
     */
    public void eliminarProducto(Long id) {
        try {
            if (productoRepository.existsById(id)) {
                productoRepository.deleteById(id);
            } else {
                throw new RuntimeException("Producto no encontrado para eliminar");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el producto: " + e.getMessage());
        }
    }

    /**
     * Obtiene todos los productos con el estado especificado.
     *
     * @param estado el estado de los productos a obtener.
     * @return una lista de productos con el estado especificado.
     * @throws RuntimeException si ocurre un error al obtener los productos.
     */
    public List<Producto> obtenerProductosPorEstado(String estado) {
        try {
            return productoRepository.findByEstado(estado);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener productos por estado: " + e.getMessage());
        }
    }
}