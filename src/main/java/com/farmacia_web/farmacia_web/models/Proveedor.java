package com.farmacia_web.farmacia_web.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "telefono", nullable = false)
    private Long telefono;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "ciudad", nullable = false)
    private String ciudad;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private Timestamp creacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private Timestamp actualizacion;
}

