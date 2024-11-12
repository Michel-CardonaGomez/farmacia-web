package com.farmacia_web.farmacia_web.repositories;

import com.farmacia_web.farmacia_web.models.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {


    @Query(value = "SELECT MAX(f.serial) FROM facturas f WHERE f.fecha_creacion LIKE CONCAT(:fecha, '%') AND f.tipo = :tipo", nativeQuery = true)
    String findUltimoNumeroFacturaPorFecha(@Param("fecha") String fecha, @Param("tipo") String tipo);

}
