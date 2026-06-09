package com.joel.spiderbiblioteca.data.model

data class CharacterRelationship(
    val id: Int,
    val personaje1: String,
    val personaje2: String,
    val tipo: String,
    val descripcion: String,
    val importancia: Int
)
