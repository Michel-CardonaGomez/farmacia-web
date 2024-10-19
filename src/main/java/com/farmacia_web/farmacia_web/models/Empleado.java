package com.farmacia_web.farmacia_web.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@Table(name = "empleados")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "identificacion", nullable = false, unique = true)
    private Long identificacion;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "telefono", nullable = false)
    private Long telefono;

    @Column(name = "rol", nullable = false)
    private String rol;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "usuario", unique = true)
    private String usuario;

    @Column(name = "contraseña")
    private String contrasena;

    @CreationTimestamp
    @Column(name = "fecha_creacion", updatable = false)
    private Timestamp creacion;

    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private Timestamp actualizacion;

    @Column(name = "activo")
    private Boolean activo;

}
