package com.farmacia_web.farmacia_web.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
@Data
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "identificacion", nullable = false)
    private Long identificacion;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "email")
    private String email;

    @Column(name = "telefono")
    private Long telefono;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private Timestamp creacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private Timestamp actualizacion;
}
