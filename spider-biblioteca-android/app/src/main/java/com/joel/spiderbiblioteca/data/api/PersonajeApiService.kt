package com.joel.spiderbiblioteca.data.api

import com.joel.spiderbiblioteca.data.model.Personaje
import retrofit2.http.*

interface PersonajeApiService {

    @GET("api/personajes")
    suspend fun obtenerPersonajes(): List<Personaje>

    @GET("api/personajes/{id}")
    suspend fun obtenerPersonajePorId(@Path("id") id: Long): Personaje

    @POST("api/personajes")
    suspend fun crearPersonaje(@Body personaje: Personaje): Personaje

    @PUT("api/personajes/{id}")
    suspend fun actualizarPersonaje(
        @Path("id") id: Long,
        @Body personaje: Personaje
    ): Personaje

    @DELETE("api/personajes/{id}")
    suspend fun eliminarPersonaje(@Path("id") id: Long)

    @GET("api/personajes/buscar")
    suspend fun buscarPorNombre(@Query("nombre") nombre: String): List<Personaje>

    @GET("api/personajes/tipo/{tipo}")
    suspend fun buscarPorTipo(@Path("tipo") tipo: String): List<Personaje>
}
