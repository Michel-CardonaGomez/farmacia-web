package com.farmacia_web.farmacia_web.models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
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
    private Date creacion;

    @Column(name = "emp_actualizacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualizacion;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCedula() {
        return cedula;
    }

    public void setCedula(Long cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contraseña) {
        this.contrasena = contraseña;
    }

    public Date getCreacion() {
        return creacion;
    }

    public void setCreacion(Date creacion) {
        this.creacion = creacion;
    }

    public Date getActualizacion() {
        return actualizacion;
    }

    public void setActualizacion(Date actualizacion) {
        this.actualizacion = actualizacion;
    }


}
