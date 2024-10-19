package com.farmacia_web.farmacia_web.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id", nullable = false)
    private Long id;

    @Column(name = "emp_cedula", nullable = false)
    private Long cedula;

    @Column(name = "emp_nombre", nullable = false)
    private String nombre;

    @Column(name = "emp_telefono", nullable = false)
    private Long telefono;

    @Column(name = "emp_rol", nullable = false)
    private String rol;

    @Column(name = "emp_usuario", nullable = false)
    private String usuario;

    @Column(name = "emp_contraseña", nullable = false)
    private String contrasena;

    @Column(name = "emp_creacion", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime creacion;

    @Column(name = "emp_actualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime actualizacion;

}
