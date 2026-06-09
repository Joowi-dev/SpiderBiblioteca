package com.joel.spiderbiblioteca.data.repository

import android.content.Context
import com.joel.spiderbiblioteca.data.api.RetrofitInstance
import com.joel.spiderbiblioteca.data.local.SessionManager
import com.joel.spiderbiblioteca.data.model.AuthResponse
import com.joel.spiderbiblioteca.data.model.LoginRequest
import com.joel.spiderbiblioteca.data.model.RegisterRequest

class AuthRepository(private val context: Context) {

    private val api = RetrofitInstance.authApi

    suspend fun login(username: String, password: String): AuthResponse {
        val response = api.login(LoginRequest(username, password))
        SessionManager.saveSession(context, response.token, response.username)
        return response
    }

    suspend fun register(username: String, email: String, password: String): AuthResponse {
        val response = api.register(RegisterRequest(username, email, password))
        SessionManager.saveSession(context, response.token, response.username)
        return response
    }

    fun logout() {
        SessionManager.clearSession(context)
    }
}
