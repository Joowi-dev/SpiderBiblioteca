package com.joel.spiderbiblioteca.data.model

data class PreguntaQuiz(
    val enunciado: String,
    val opciones: List<String>,
    val correcta: Int,
    val categoria: String = "general",
    val dificultad: String = "media",
    val explicacion: String = ""
)
