package com.joel.spiderbiblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "personajes")
public class Personaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El nombre real es obligatorio")
    @Column(name = "nombre_real", nullable = false, length = 100)
    private String nombreReal;

    @Column(length = 255)
    private String apodos;

    @Column(columnDefinition = "TEXT")
    private String poderes;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "primera_aparicion", length = 100)
    private String primeraAparicion;

    @NotBlank(message = "El tipo es obligatorio (heroe, villano, aliado, antiheroe)")
    @Column(nullable = false, length = 50)
    private String tipo;

    @Column(name = "imagen_url", length = 500)
    private String imagenUrl;

    // ── Constructores ──────────────────────────────────────────────────────────

    public Personaje() {}

    public Personaje(Long id, String nombre, String nombreReal, String apodos,
                     String poderes, String descripcion, String primeraAparicion,
                     String tipo, String imagenUrl) {
        this.id = id;
        this.nombre = nombre;
        this.nombreReal = nombreReal;
        this.apodos = apodos;
        this.poderes = poderes;
        this.descripcion = descripcion;
        this.primeraAparicion = primeraAparicion;
        this.tipo = tipo;
        this.imagenUrl = imagenUrl;
    }

    // ── Getters ────────────────────────────────────────────────────────────────

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getNombreReal() { return nombreReal; }
    public String getApodos() { return apodos; }
    public String getPoderes() { return poderes; }
    public String getDescripcion() { return descripcion; }
    public String getPrimeraAparicion() { return primeraAparicion; }
    public String getTipo() { return tipo; }
    public String getImagenUrl() { return imagenUrl; }

    // ── Setters ────────────────────────────────────────────────────────────────

    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setNombreReal(String nombreReal) { this.nombreReal = nombreReal; }
    public void setApodos(String apodos) { this.apodos = apodos; }
    public void setPoderes(String poderes) { this.poderes = poderes; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setPrimeraAparicion(String primeraAparicion) { this.primeraAparicion = primeraAparicion; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }

    @Override
    public String toString() {
        return "Personaje{id=" + id + ", nombre='" + nombre + "', tipo='" + tipo + "'}";
    }
}
