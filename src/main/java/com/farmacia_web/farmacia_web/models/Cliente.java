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
    @Column(name = "cli_id", nullable = false)
    private Long id;

    @Column(name = "cli_cedula", nullable = false)
    private Long cedula;

    @Column(name = "cli_nombre", nullable = false)
    private String nombre;

    @Column(name = "cli_email")
    private String email;

    @Column(name = "cli_telefono")
    private Long telefono;

    @Column(name = "cli_creacion")
    private LocalDateTime creacion;

    @Column(name = "cli_actualizacion")
    private LocalDateTime actualizacion;
}
