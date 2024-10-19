package com.farmacia_web.farmacia_web.repositories;

import com.farmacia_web.farmacia_web.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
}
