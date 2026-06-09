package com.joel.spiderbiblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "trajes")
public class Traje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(length = 200)
    private String usuario;

    @Column(name = "primera_aparicion", length = 200)
    private String primeraAparicion;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "TEXT")
    private String habilidades;

    @Column(nullable = false)
    private int popularidad;

    @Column(name = "imagen_url", length = 500)
    private String imagenUrl;

    public Traje() {}

    public Traje(Long id, String nombre, String usuario, String primeraAparicion,
                 String descripcion, String habilidades, int popularidad, String imagenUrl) {
        this.id = id;
        this.nombre = nombre;
        this.usuario = usuario;
        this.primeraAparicion = primeraAparicion;
        this.descripcion = descripcion;
        this.habilidades = habilidades;
        this.popularidad = popularidad;
        this.imagenUrl = imagenUrl;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getUsuario() { return usuario; }
    public String getPrimeraAparicion() { return primeraAparicion; }
    public String getDescripcion() { return descripcion; }
    public String getHabilidades() { return habilidades; }
    public int getPopularidad() { return popularidad; }
    public String getImagenUrl() { return imagenUrl; }

    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setUsuario(String usuario) { this.usuario = usuario; }
    public void setPrimeraAparicion(String primeraAparicion) { this.primeraAparicion = primeraAparicion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setHabilidades(String habilidades) { this.habilidades = habilidades; }
    public void setPopularidad(int popularidad) { this.popularidad = popularidad; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
}
