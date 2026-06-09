package com.joel.spiderbiblioteca.data.api

import com.joel.spiderbiblioteca.data.model.Personaje
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoritoApiService {

    @GET("api/favoritos")
    suspend fun obtenerFavoritos(): List<Personaje>

    @POST("api/favoritos/{id}")
    suspend fun agregar(@Path("id") personajeId: Long): List<Personaje>

    @DELETE("api/favoritos/{id}")
    suspend fun eliminar(@Path("id") personajeId: Long): List<Personaje>
}
