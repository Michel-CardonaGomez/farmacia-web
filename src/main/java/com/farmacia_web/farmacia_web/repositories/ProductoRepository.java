package com.farmacia_web.farmacia_web.repositories;

import com.farmacia_web.farmacia_web.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    ArrayList<Producto> findByEstado(String estado);

    @Query(value = "SELECT * FROM productos p WHERE p.id_proveedor = :idProveedor", nativeQuery = true)
        ArrayList<Producto> findByProveedorId(@Param("idProveedor") Long idProveedor);

    @Query(value = "SELECT COUNT(*) FROM productos", nativeQuery = true)
    Long totalProductos();

    @Query(value = "SELECT SUM(existencias) FROM productos", nativeQuery = true)
    Long existenciasActuales();





}
