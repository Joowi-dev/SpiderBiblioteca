package com.joel.spiderbiblioteca.data.model

data class SpiderUniverse(
    val id: Int,
    val nombre: String,
    val codigo: String,
    val spiderManPrincipal: String,
    val descripcion: String,
    val personajesDestacados: List<String>,
    val estilo: String,
    val imagenUrl: String? = null
)
