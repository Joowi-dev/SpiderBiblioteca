package com.joel.spiderbiblioteca.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.joel.spiderbiblioteca.data.local.SessionManager
import com.joel.spiderbiblioteca.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException

data class AuthUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isLoggedIn: Boolean = false,
    val username: String? = null
)

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepository(application)

    private val _uiState = MutableStateFlow(
        AuthUiState(
            isLoggedIn = SessionManager.isLoggedIn(),
            username = SessionManager.username
        )
    )
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val response = repository.login(username.trim(), password)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoggedIn = true,
                    username = response.username
                )
            } catch (e: HttpException) {
                val msg = when (e.code()) {
                    401, 403 -> "Usuario o contraseña incorrectos"
                    400 -> "Datos inválidos"
                    else -> "Error del servidor (${e.code()})"
                }
                _uiState.value = _uiState.value.copy(isLoading = false, error = msg)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "No se pudo conectar con el servidor"
                )
            }
        }
    }

    fun register(username: String, email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val response = repository.register(username.trim(), email.trim(), password)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isLoggedIn = true,
                    username = response.username
                )
            } catch (e: HttpException) {
                val msg = when (e.code()) {
                    400 -> "Datos inválidos. Revisa el formulario."
                    409 -> "Ese nombre de usuario ya existe"
                    else -> "Error del servidor (${e.code()})"
                }
                _uiState.value = _uiState.value.copy(isLoading = false, error = msg)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "No se pudo conectar con el servidor"
                )
            }
        }
    }

    fun logout() {
        repository.logout()
        _uiState.value = _uiState.value.copy(isLoggedIn = false, username = null)
    }

    fun limpiarError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
}
