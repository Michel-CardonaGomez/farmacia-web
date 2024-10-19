package com.farmacia_web.farmacia_web.repositories;

import com.farmacia_web.farmacia_web.models.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    @Query(value = "SELECT id, identificacion, nombre, telefono, email, rol, fecha_creacion, fecha_actualizacion, activo FROM empleados", nativeQuery = true)
    List<Map<String, Object>> listarEmpleados();

}
