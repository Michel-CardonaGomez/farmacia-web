package com.farmacia_web.farmacia_web.repositories;

import com.farmacia_web.farmacia_web.models.DetallesVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetallesVentaRepository extends JpaRepository<DetallesVenta, Long> {

    @Query(value = "SELECT SUM(cantidad) FROM detalles_ventas", nativeQuery = true)
    Long existenciasvendidas();

    @Query(value = "SELECT \n" +
            "    CONCAT(p.nombre, ' ', m.nombre, ' ', p.presentacion) AS producto,\n" +
            "    SUM(dv.cantidad) AS total_vendido\n" +
            "FROM \n" +
            "    detalles_ventas dv\n" +
            "JOIN \n" +
            "    productos p ON dv.id_producto = p.id\n" +
            "JOIN \n" +
            "    marcas m ON p.id_marca = m.id\n" +
            "GROUP BY \n" +
            "    producto\n" +
            "ORDER BY \n" +
            "    total_vendido DESC\n" +
            "LIMIT 10;", nativeQuery = true)
    List<Object[]> topProductos();

    @Query(value = "SELECT p.nombre, SUM(dv.cantidad) AS total_vendido\n" +
            "FROM detalles_ventas dv\n" +
            "JOIN productos pr ON dv.id_producto = pr.id\n" +
            "JOIN proveedores p ON pr.id_proveedor = p.id\n" +
            "GROUP BY p.nombre\n" +
            "ORDER BY total_vendido DESC\n" +
            "LIMIT 10;", nativeQuery = true)
    List<Object[]> topProveedores();


}
