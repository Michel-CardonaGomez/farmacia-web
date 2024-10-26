package com.farmacia_web.farmacia_web.services;

import com.farmacia_web.farmacia_web.models.Producto;
import com.farmacia_web.farmacia_web.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public ArrayList<Producto> obtenerProductos(){
        try {
            return (ArrayList<Producto>) productoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la lista de productos: " + e.getMessage());
        }

    }

    public Producto crearProducto(Producto producto) {
        try {
            return productoRepository.save(producto);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el producto: " + e.getMessage());
        }
    }

    public Producto obtenerProductoPorId(Long id) {
        try {
            Producto producto = productoRepository.findById(id).get();
            return producto;
        } catch (Exception e) {
            throw new RuntimeException("Error al encontrar el producto: " + e.getMessage());
        }
    }


}
