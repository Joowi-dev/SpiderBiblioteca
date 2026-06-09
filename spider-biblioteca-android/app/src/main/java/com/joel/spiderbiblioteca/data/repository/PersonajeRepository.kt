package com.joel.spiderbiblioteca.data.repository

import com.joel.spiderbiblioteca.data.api.RetrofitInstance
import com.joel.spiderbiblioteca.data.model.Personaje

class PersonajeRepository {

    private val api = RetrofitInstance.api

    suspend fun obtenerTodos(): List<Personaje> = api.obtenerPersonajes()

    suspend fun obtenerPorId(id: Long): Personaje = api.obtenerPersonajePorId(id)

    suspend fun crear(personaje: Personaje): Personaje = api.crearPersonaje(personaje)

    suspend fun actualizar(id: Long, personaje: Personaje): Personaje =
        api.actualizarPersonaje(id, personaje)

    suspend fun eliminar(id: Long) = api.eliminarPersonaje(id)

    suspend fun buscarPorNombre(nombre: String): List<Personaje> = api.buscarPorNombre(nombre)

    suspend fun buscarPorTipo(tipo: String): List<Personaje> = api.buscarPorTipo(tipo)
}
