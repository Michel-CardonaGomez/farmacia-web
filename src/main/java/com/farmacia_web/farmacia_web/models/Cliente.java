package com.farmacia_web.farmacia_web.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name = "clientes")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "cedula", nullable = false)
    private Long cedula;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "email")
    private String email;

    @Column(name = "telefono")
    private Long telefono;

    @Column(name = "fecha_creacion")
    private LocalDateTime creacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime actualizacion;
}
