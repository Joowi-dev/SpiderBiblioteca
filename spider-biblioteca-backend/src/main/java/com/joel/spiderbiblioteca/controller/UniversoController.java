package com.joel.spiderbiblioteca.controller;

import com.joel.spiderbiblioteca.model.Universo;
import com.joel.spiderbiblioteca.service.UniversoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/universos")
@CrossOrigin(origins = "*")
public class UniversoController {

    @Autowired
    private UniversoService service;

    @GetMapping
    public ResponseEntity<List<Universo>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Universo> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
}
