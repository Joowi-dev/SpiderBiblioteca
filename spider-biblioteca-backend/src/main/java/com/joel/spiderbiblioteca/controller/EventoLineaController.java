package com.joel.spiderbiblioteca.controller;

import com.joel.spiderbiblioteca.model.EventoLinea;
import com.joel.spiderbiblioteca.service.EventoLineaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timeline")
@CrossOrigin(origins = "*")
public class EventoLineaController {

    @Autowired
    private EventoLineaService service;

    @GetMapping
    public ResponseEntity<List<EventoLinea>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoLinea> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
}
