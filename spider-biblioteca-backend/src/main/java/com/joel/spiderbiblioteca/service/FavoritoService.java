package com.joel.spiderbiblioteca.service;

import com.joel.spiderbiblioteca.exception.ResourceNotFoundException;
import com.joel.spiderbiblioteca.model.Personaje;
import com.joel.spiderbiblioteca.model.Usuario;
import com.joel.spiderbiblioteca.repository.PersonajeRepository;
import com.joel.spiderbiblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class FavoritoService {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PersonajeRepository personajeRepository;

    @Transactional(readOnly = true)
    public Set<Personaje> obtenerFavoritos(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        return new HashSet<>(usuario.getFavoritos());
    }

    @Transactional
    public Set<Personaje> agregarFavorito(String username, Long personajeId) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        Personaje personaje = personajeRepository.findById(personajeId)
                .orElseThrow(() -> new ResourceNotFoundException("Personaje no encontrado: " + personajeId));
        usuario.getFavoritos().add(personaje);
        usuarioRepository.save(usuario);
        return new HashSet<>(usuario.getFavoritos());
    }

    @Transactional
    public Set<Personaje> eliminarFavorito(String username, Long personajeId) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
        boolean removed = usuario.getFavoritos().removeIf(p -> p.getId().equals(personajeId));
        if (!removed) {
            throw new ResourceNotFoundException("El personaje " + personajeId + " no está en tus favoritos");
        }
        usuarioRepository.save(usuario);
        return new HashSet<>(usuario.getFavoritos());
    }
}
