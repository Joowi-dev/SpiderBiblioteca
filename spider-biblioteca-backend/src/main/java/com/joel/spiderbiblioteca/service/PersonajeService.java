package com.joel.spiderbiblioteca.service;

import com.joel.spiderbiblioteca.exception.ResourceNotFoundException;
import com.joel.spiderbiblioteca.model.Personaje;
import com.joel.spiderbiblioteca.repository.PersonajeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonajeService {

    private final PersonajeRepository repository;

    public PersonajeService(PersonajeRepository repository) {
        this.repository = repository;
    }

    public List<Personaje> obtenerTodos() {
        return repository.findAll();
    }

    public Personaje obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Personaje", id));
    }

    public Personaje guardar(Personaje personaje) {
        return repository.save(personaje);
    }

    public Personaje actualizar(Long id, Personaje datos) {
        Personaje existente = obtenerPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setNombreReal(datos.getNombreReal());
        existente.setApodos(datos.getApodos());
        existente.setPoderes(datos.getPoderes());
        existente.setDescripcion(datos.getDescripcion());
        existente.setPrimeraAparicion(datos.getPrimeraAparicion());
        existente.setTipo(datos.getTipo());
        existente.setImagenUrl(datos.getImagenUrl());
        return repository.save(existente);
    }

    public void eliminar(Long id) {
        Personaje existente = obtenerPorId(id);
        repository.delete(existente);
    }

    public List<Personaje> buscarPorNombre(String nombre) {
        return repository.findByNombreContainingIgnoreCase(nombre);
    }

    public List<Personaje> buscarPorTipo(String tipo) {
        return repository.findByTipoIgnoreCase(tipo);
    }
}
