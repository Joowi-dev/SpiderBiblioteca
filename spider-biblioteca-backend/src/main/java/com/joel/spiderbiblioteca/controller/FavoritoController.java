package com.joel.spiderbiblioteca.controller;

import com.joel.spiderbiblioteca.model.Personaje;
import com.joel.spiderbiblioteca.service.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @GetMapping
    public ResponseEntity<Set<Personaje>> obtenerFavoritos(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(favoritoService.obtenerFavoritos(userDetails.getUsername()));
    }

    @PostMapping("/{personajeId}")
    public ResponseEntity<Set<Personaje>> agregar(@AuthenticationPrincipal UserDetails userDetails,
                                                   @PathVariable Long personajeId) {
        return ResponseEntity.ok(favoritoService.agregarFavorito(userDetails.getUsername(), personajeId));
    }

    @DeleteMapping("/{personajeId}")
    public ResponseEntity<Set<Personaje>> eliminar(@AuthenticationPrincipal UserDetails userDetails,
                                                    @PathVariable Long personajeId) {
        return ResponseEntity.ok(favoritoService.eliminarFavorito(userDetails.getUsername(), personajeId));
    }
}
