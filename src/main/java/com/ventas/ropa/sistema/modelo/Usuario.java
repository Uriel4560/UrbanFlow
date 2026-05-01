package com.ventas.ropa.sistema.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "usuario", nullable = false, unique = true)
    private String usuario;
    
    @Column(name = "contraseña", nullable = false)
    private String contraseña;
    
    @Column(name = "rol", nullable = false)
    private String rol;
    
    @Column(name = "activo")
    private boolean activo;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    // Constructores
    public Usuario() {
        this.activo = true;
        this.createdAt = LocalDateTime.now();
    }
    
    public Usuario(String nombre, String email, String usuario, String contraseña, String rol) {
        this();
        this.nombre = nombre;
        this.email = email;
        this.usuario = usuario;
        this.contraseña = contraseña;
        this.rol = rol;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getUsuario() {
        return usuario;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    public String getContraseña() {
        return contraseña;
    }
    
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    public String getRol() {
        return rol;
    }
    
    public void setRol(String rol) {
        this.rol = rol;
    }
    
    public boolean isActivo() {
        return activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
