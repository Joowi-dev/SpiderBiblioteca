package com.joel.spiderbiblioteca.data.api

import com.joel.spiderbiblioteca.data.model.AuthResponse
import com.joel.spiderbiblioteca.data.model.LoginRequest
import com.joel.spiderbiblioteca.data.model.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @POST("api/auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse
}
