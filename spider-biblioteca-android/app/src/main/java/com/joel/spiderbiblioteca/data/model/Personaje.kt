package com.joel.spiderbiblioteca.data.model

import com.google.gson.annotations.SerializedName

data class Personaje(
    val id: Long? = null,
    val nombre: String = "",

    @SerializedName("nombreReal")
    val nombreReal: String = "",

    val apodos: String = "",
    val poderes: String = "",
    val descripcion: String = "",

    @SerializedName("primeraAparicion")
    val primeraAparicion: String = "",

    val tipo: String = "",

    @SerializedName("imagenUrl")
    val imagenUrl: String = ""
)
