package com.farmacia_web.farmacia_web.repositories;

import com.farmacia_web.farmacia_web.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    ArrayList<Producto> findByEstado(String estado);
}
