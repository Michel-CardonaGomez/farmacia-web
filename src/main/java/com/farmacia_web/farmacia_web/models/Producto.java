package com.farmacia_web.farmacia_web.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo", unique = true, nullable = false, length = 55)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 255)
    private String nombre;

    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;

    @Column(name = "precio_compra", nullable = false)
    private BigDecimal precioCompra;

    @Column(name = "precio_venta", nullable = false)
    private BigDecimal precioVenta;

    @Column(name = "existencias", nullable = false)
    private int existencias;

    @ManyToOne(fetch = FetchType.LAZY)  // Relación con Subcategoria
    @JoinColumn(name = "id_subcategoria", nullable = false)
    private Subcategoria subcategoria;

    @ManyToOne(fetch = FetchType.LAZY)  // Relación con Proveedor
    @JoinColumn(name = "id_proveedor", nullable = true)
    private Proveedor proveedor;

    @ManyToOne(fetch = FetchType.LAZY)  // Relación con Marca
    @JoinColumn(name = "id_marca", nullable = false)
    private Marca marca;

    @Column(name = "presentacion", nullable = false, length = 100)
    private String presentacion;

    @Column(name = "estado", nullable = false, length = 100)
    private String estado;

    @Column(name = "iva", nullable = false)
    private int iva;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private Timestamp creacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private Timestamp actualizacion;
}
