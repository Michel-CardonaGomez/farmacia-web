package com.farmacia_web.farmacia_web.repositories;

import com.farmacia_web.farmacia_web.models.Factura;
import com.farmacia_web.farmacia_web.models.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    Optional<Venta> findByFactura(Factura factura);

    @Query(value = "SELECT COUNT(*) FROM ventas", nativeQuery = true)
    Long totalVentas();

    @Query(value = "SELECT SUM(total) FROM ventas", nativeQuery = true)
    Long importeVendido();

    @Query(value = "SELECT SUM(d.cantidad * (p.precio_venta - p.precio_compra)) - " +
            "SUM(d.cantidad * p.precio_venta * (p.iva / 100)) AS beneficioNeto " +
            "FROM detalles_ventas d " +
            "JOIN productos p ON d.id_producto = p.id " +
            "JOIN ventas v ON d.id_venta = v.id", nativeQuery = true)
    Long obtenerBeneficioNeto();

    @Query(value = "SELECT SUM(d.cantidad * (p.precio_venta - p.precio_compra)) AS beneficioBruto " +
            "FROM detalles_ventas d " +
            "JOIN productos p ON d.id_producto = p.id " +
            "JOIN ventas v ON d.id_venta = v.id", nativeQuery = true)
    Long obtenerBeneficioBruto();

    @Query(value = "SELECT SUM(d.cantidad * p.precio_venta * (p.iva / 100)) AS totalIva " +
            "FROM detalles_ventas d " +
            "JOIN productos p ON d.id_producto = p.id " +
            "JOIN ventas v ON d.id_venta = v.id", nativeQuery = true)
    Long obtenerTotalIva();

    @Query(value = "SELECT e.nombre, COUNT(v.id) AS total_ventas " +
            "FROM empleados e " +
            "JOIN ventas v ON e.id = v.id_empleado " +
            "GROUP BY e.nombre " +
            "ORDER BY total_ventas DESC", nativeQuery = true)
    List<Object[]> findEmpleadosConMasVentas();

   @Query(value = "SELECT metodo_pago, COUNT(*) AS cantidad_transacciones\n" +
           "FROM ventas\n" +
           "GROUP BY metodo_pago", nativeQuery = true)
   List<Object[]> contarMetodosPago();


}
