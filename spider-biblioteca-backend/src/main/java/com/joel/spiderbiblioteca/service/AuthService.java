package com.joel.spiderbiblioteca.service;

import com.joel.spiderbiblioteca.dto.AuthResponse;
import com.joel.spiderbiblioteca.dto.LoginRequest;
import com.joel.spiderbiblioteca.dto.RegisterRequest;
import com.joel.spiderbiblioteca.dto.UsuarioPerfilDto;
import com.joel.spiderbiblioteca.model.Usuario;
import com.joel.spiderbiblioteca.repository.UsuarioRepository;
import com.joel.spiderbiblioteca.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("El nombre de usuario ya está en uso");
        }
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(passwordEncoder.encode(request.getPassword()));
        usuarioRepository.save(usuario);

        String token = jwtUtil.generateToken(usuario.getUsername());
        return new AuthResponse(token, usuario.getUsername(), usuario.getEmail());
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        Usuario usuario = usuarioRepository.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtUtil.generateToken(usuario.getUsername());
        return new AuthResponse(token, usuario.getUsername(), usuario.getEmail());
    }

    public UsuarioPerfilDto getMe(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username).orElseThrow();
        return new UsuarioPerfilDto(
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getRol(),
                usuario.getFavoritos().size()
        );
    }
}
