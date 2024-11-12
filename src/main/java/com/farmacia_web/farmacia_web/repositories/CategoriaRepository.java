package com.farmacia_web.farmacia_web.repositories;

import com.farmacia_web.farmacia_web.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    @Query(value = "SELECT c.nombre, SUM(d.cantidad) AS cantidad_vendida " +
            "FROM detalles_ventas d " +
            "JOIN productos p ON d.id_producto = p.id " +
            "JOIN subcategorias s ON p.id_subcategoria = s.id " +
            "JOIN categorias c ON s.id_categoria = c.id " +
            "GROUP BY c.nombre",
            nativeQuery = true)
    List<Object[]> cantidadVendidaPorCategoria();
}
