package com.farmacia_web.farmacia_web.repositories;

import com.farmacia_web.farmacia_web.models.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {

    @Query(value = "SELECT COUNT(*) FROM marcas", nativeQuery = true)
    Long totalMarcas();
}
