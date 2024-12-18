package com.farmacia_web.farmacia_web.repositories;

import com.farmacia_web.farmacia_web.models.DetallesCompra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetallesCompraRepository extends JpaRepository<DetallesCompra, Long> {

}
