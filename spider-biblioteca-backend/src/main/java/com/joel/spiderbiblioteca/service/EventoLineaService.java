package com.joel.spiderbiblioteca.service;

import com.joel.spiderbiblioteca.exception.ResourceNotFoundException;
import com.joel.spiderbiblioteca.model.EventoLinea;
import com.joel.spiderbiblioteca.repository.EventoLineaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventoLineaService {

    @Autowired
    EventoLineaRepository repo;

    public List<EventoLinea> obtenerTodos() {
        return repo.findAll();
    }

    public EventoLinea obtenerPorId(Long id) {
        return repo.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("EventoLinea", id));
    }
}
