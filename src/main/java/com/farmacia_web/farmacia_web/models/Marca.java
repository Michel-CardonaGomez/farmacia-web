package com.farmacia_web.farmacia_web.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "marcas")
public class Marca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private Timestamp creacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private Timestamp actualizacion;
}
