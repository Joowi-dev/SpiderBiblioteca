package com.joel.spiderbiblioteca.controller;

import com.joel.spiderbiblioteca.model.Personaje;
import com.joel.spiderbiblioteca.service.PersonajeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personajes")
public class PersonajeController {

    private final PersonajeService service;

    public PersonajeController(PersonajeService service) {
        this.service = service;
    }

    // GET /api/personajes
    @GetMapping
    public ResponseEntity<List<Personaje>> obtenerTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    // GET /api/personajes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Personaje> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    // POST /api/personajes
    @PostMapping
    public ResponseEntity<Personaje> crear(@Valid @RequestBody Personaje personaje) {
        Personaje creado = service.guardar(personaje);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // PUT /api/personajes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Personaje> actualizar(@PathVariable Long id,
                                                 @Valid @RequestBody Personaje personaje) {
        return ResponseEntity.ok(service.actualizar(id, personaje));
    }

    // DELETE /api/personajes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // GET /api/personajes/buscar?nombre=spider
    @GetMapping("/buscar")
    public ResponseEntity<List<Personaje>> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(service.buscarPorNombre(nombre));
    }

    // GET /api/personajes/tipo/{tipo}
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<Personaje>> buscarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(service.buscarPorTipo(tipo));
    }
}
