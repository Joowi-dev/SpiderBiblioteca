package com.joel.spiderbiblioteca.service;

import com.joel.spiderbiblioteca.exception.ResourceNotFoundException;
import com.joel.spiderbiblioteca.model.Universo;
import com.joel.spiderbiblioteca.repository.UniversoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UniversoService {

    @Autowired
    UniversoRepository repo;

    public List<Universo> obtenerTodos() {
        return repo.findAll();
    }

    public Universo obtenerPorId(Long id) {
        return repo.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Universo", id));
    }
}
