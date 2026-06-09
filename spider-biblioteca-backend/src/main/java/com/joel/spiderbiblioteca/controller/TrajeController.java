package com.joel.spiderbiblioteca.controller;

import com.joel.spiderbiblioteca.model.Traje;
import com.joel.spiderbiblioteca.service.TrajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trajes")
@CrossOrigin(origins = "*")
public class TrajeController {

    @Autowired
    private TrajeService service;

    @GetMapping
    public ResponseEntity<List<Traje>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Traje> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
}
