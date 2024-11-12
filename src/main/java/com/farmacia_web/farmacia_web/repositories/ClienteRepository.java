package com.farmacia_web.farmacia_web.repositories;

import com.farmacia_web.farmacia_web.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {

    Optional<Cliente> findByIdentificacion(Long identificacion);

    @Query(value = "SELECT COUNT(*) FROM clientes", nativeQuery = true)
    Long totalClientes();
}
