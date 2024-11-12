package com.farmacia_web.farmacia_web.repositories;

import com.farmacia_web.farmacia_web.models.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    Optional<Proveedor> findByNombre(String nombre);

    @Query(value = "SELECT COUNT(*) FROM proveedores", nativeQuery = true)
    Long totalProveedores();

}
