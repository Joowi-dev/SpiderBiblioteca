package com.joel.spiderbiblioteca.data.model

data class SpiderSuit(
    val id: Int,
    val nombre: String,
    val usuario: String,
    val primeraAparicion: String,
    val descripcion: String,
    val habilidades: String,
    val popularidad: Int,
    val imagenUrl: String? = null
)
