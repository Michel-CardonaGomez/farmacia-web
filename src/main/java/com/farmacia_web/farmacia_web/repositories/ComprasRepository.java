package com.farmacia_web.farmacia_web.repositories;

import com.farmacia_web.farmacia_web.models.Compra;
import com.farmacia_web.farmacia_web.models.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComprasRepository extends JpaRepository<Compra, Long> {


    Optional<Compra> findByFactura(Factura factura);

    @Query(value = "SELECT SUM(total) FROM compras", nativeQuery = true)
    Long importePagado();
}
