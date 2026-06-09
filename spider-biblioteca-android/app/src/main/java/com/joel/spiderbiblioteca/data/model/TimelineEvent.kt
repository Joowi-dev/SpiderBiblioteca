package com.joel.spiderbiblioteca.data.model

data class TimelineEvent(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val etapa: String,
    val personajes: List<String>,
    val tipo: String,
    val imagenUrl: String? = null
)
