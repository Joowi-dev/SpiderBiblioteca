package com.joel.spiderbiblioteca.dto;

public class UsuarioPerfilDto {

    private String username;
    private String email;
    private String rol;
    private int totalFavoritos;

    public UsuarioPerfilDto(String username, String email, String rol, int totalFavoritos) {
        this.username = username;
        this.email = email;
        this.rol = rol;
        this.totalFavoritos = totalFavoritos;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getRol() { return rol; }
    public int getTotalFavoritos() { return totalFavoritos; }
}
