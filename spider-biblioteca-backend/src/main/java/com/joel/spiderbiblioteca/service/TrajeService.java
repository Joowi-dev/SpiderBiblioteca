package com.joel.spiderbiblioteca.service;

import com.joel.spiderbiblioteca.exception.ResourceNotFoundException;
import com.joel.spiderbiblioteca.model.Traje;
import com.joel.spiderbiblioteca.repository.TrajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrajeService {

    @Autowired
    TrajeRepository repo;

    public List<Traje> obtenerTodos() {
        return repo.findAll();
    }

    public Traje obtenerPorId(Long id) {
        return repo.findById(id).orElseThrow(
            () -> new ResourceNotFoundException("Traje", id));
    }
}
