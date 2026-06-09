package com.joel.spiderbiblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "universos")
public class Universo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, length = 200)
    private String nombre;

    @Column(length = 100)
    private String codigo;

    @Column(name = "spider_man_principal", length = 200)
    private String spiderManPrincipal;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "personajes_destacados", columnDefinition = "TEXT")
    private String personajesDestacados;

    @Column(length = 100)
    private String estilo;

    @Column(name = "imagen_url", length = 500)
    private String imagenUrl;

    public Universo() {}

    public Universo(Long id, String nombre, String codigo, String spiderManPrincipal,
                    String descripcion, String personajesDestacados, String estilo, String imagenUrl) {
        this.id = id;
        this.nombre = nombre;
        this.codigo = codigo;
        this.spiderManPrincipal = spiderManPrincipal;
        this.descripcion = descripcion;
        this.personajesDestacados = personajesDestacados;
        this.estilo = estilo;
        this.imagenUrl = imagenUrl;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCodigo() { return codigo; }
    public String getSpiderManPrincipal() { return spiderManPrincipal; }
    public String getDescripcion() { return descripcion; }
    public String getPersonajesDestacados() { return personajesDestacados; }
    public String getEstilo() { return estilo; }
    public String getImagenUrl() { return imagenUrl; }

    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public void setSpiderManPrincipal(String spiderManPrincipal) { this.spiderManPrincipal = spiderManPrincipal; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setPersonajesDestacados(String personajesDestacados) { this.personajesDestacados = personajesDestacados; }
    public void setEstilo(String estilo) { this.estilo = estilo; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
}
