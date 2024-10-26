package com.farmacia_web.farmacia_web.repositories;

import com.farmacia_web.farmacia_web.models.Subcategoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubcategoriaRepository extends JpaRepository<Subcategoria, Long> {
}
