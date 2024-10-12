package com.farmacia_web.farmacia_web.repositories;

import com.farmacia_web.farmacia_web.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

}
