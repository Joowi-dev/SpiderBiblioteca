package com.joel.spiderbiblioteca.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "eventos_linea")
public class EventoLinea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 100)
    private String etapa;

    @Column(name = "personajes_relacionados", columnDefinition = "TEXT")
    private String personajesRelacionados;

    @NotBlank(message = "El tipo es obligatorio")
    @Column(nullable = false, length = 50)
    private String tipo;

    @Column(name = "imagen_url", length = 500)
    private String imagenUrl;

    public EventoLinea() {}

    public EventoLinea(Long id, String titulo, String descripcion, String etapa,
                       String personajesRelacionados, String tipo, String imagenUrl) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.etapa = etapa;
        this.personajesRelacionados = personajesRelacionados;
        this.tipo = tipo;
        this.imagenUrl = imagenUrl;
    }

    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getEtapa() { return etapa; }
    public String getPersonajesRelacionados() { return personajesRelacionados; }
    public String getTipo() { return tipo; }
    public String getImagenUrl() { return imagenUrl; }

    public void setId(Long id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setEtapa(String etapa) { this.etapa = etapa; }
    public void setPersonajesRelacionados(String personajesRelacionados) { this.personajesRelacionados = personajesRelacionados; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setImagenUrl(String imagenUrl) { this.imagenUrl = imagenUrl; }
}
