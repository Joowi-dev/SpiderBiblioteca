package com.joel.spiderbiblioteca.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.joel.spiderbiblioteca.data.local.PersonajeCacheManager
import com.joel.spiderbiblioteca.data.local.SessionManager
import com.joel.spiderbiblioteca.data.model.Personaje
import com.joel.spiderbiblioteca.data.repository.FavoritosRepository
import com.joel.spiderbiblioteca.data.repository.PersonajeRepository
import com.joel.spiderbiblioteca.data.seed.PERSONAJES_SEED
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class OrdenLista { NOMBRE_AZ, NOMBRE_ZA, TIPO }

data class PersonajeUiState(
    val personajes: List<Personaje> = emptyList(),
    val personajeSeleccionado: Personaje? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val snackbarMensaje: String? = null,
    val favoritos: Set<Long> = emptySet(),
    val orden: OrdenLista = OrdenLista.TIPO,
    val soloFavoritos: Boolean = false,
    val desdeCahe: Boolean = false,
    val totalPersonajes: Int = 0,
    val statsPorTipo: Map<String, Int> = emptyMap(),
    val personajesTodos: List<Personaje> = emptyList()
)

class PersonajeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = PersonajeRepository()
    private val favoritosRepo = FavoritosRepository(application)

    private val _uiState = MutableStateFlow(PersonajeUiState())
    val uiState: StateFlow<PersonajeUiState> = _uiState.asStateFlow()

    private var listaCompleta: List<Personaje> = emptyList()
    private var busquedaActual: String = ""
    private var tipoActual: String = ""

    init {
        viewModelScope.launch {
            favoritosRepo.favoritosFlow.collect { favs ->
                _uiState.value = _uiState.value.copy(favoritos = favs)
                aplicarFiltrosYOrden()
            }
        }
        cargarPersonajes()
    }

    fun cargarPersonajes() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, desdeCahe = false)
            var desdeCache = false
            try {
                val lista = try {
                    val online = repository.obtenerTodos()
                    PersonajeCacheManager.guardar(getApplication(), online)
                    online
                } catch (e: Exception) {
                    val cached = PersonajeCacheManager.cargar(getApplication())
                    if (cached.isNotEmpty()) {
                        desdeCache = true
                        cached
                    } else throw e
                }
                listaCompleta = lista
                val stats = lista.groupBy { it.tipo.lowercase() }.mapValues { it.value.size }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    desdeCahe = desdeCache,
                    totalPersonajes = lista.size,
                    statsPorTipo = stats,
                    personajesTodos = lista
                )
                aplicarFiltrosYOrden()
                if (lista.isEmpty() && !desdeCache) sembrarPersonajes()
                if (!desdeCache && SessionManager.isLoggedIn()) sincronizarFavoritos()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "No se pudo conectar con el servidor. Comprueba que Spring Boot esté ejecutándose."
                )
            }
        }
    }

    private fun sembrarPersonajes() {
        viewModelScope.launch {
            try {
                PERSONAJES_SEED.forEach { repository.crear(it) }
                val lista = repository.obtenerTodos()
                PersonajeCacheManager.guardar(getApplication(), lista)
                listaCompleta = lista
                val stats = lista.groupBy { it.tipo.lowercase() }.mapValues { it.value.size }
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    totalPersonajes = lista.size,
                    statsPorTipo = stats,
                    personajesTodos = lista
                )
                aplicarFiltrosYOrden()
            } catch (_: Exception) {}
        }
    }

    fun obtenerPersonaje(id: Long) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val personaje = repository.obtenerPorId(id)
                _uiState.value = _uiState.value.copy(personajeSeleccionado = personaje, isLoading = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = "No se pudo cargar el personaje.")
            }
        }
    }

    fun crearPersonaje(personaje: Personaje, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                repository.crear(personaje)
                cargarPersonajes()
                _uiState.value = _uiState.value.copy(snackbarMensaje = "Personaje creado correctamente")
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Error al crear el personaje: ${e.message}"
                )
            }
        }
    }

    fun actualizarPersonaje(id: Long, personaje: Personaje, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                repository.actualizar(id, personaje)
                cargarPersonajes()
                _uiState.value = _uiState.value.copy(snackbarMensaje = "Cambios guardados")
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Error al actualizar el personaje: ${e.message}"
                )
            }
        }
    }

    fun eliminarPersonaje(id: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                repository.eliminar(id)
                cargarPersonajes()
                _uiState.value = _uiState.value.copy(snackbarMensaje = "Personaje eliminado")
                onSuccess()
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Error al eliminar el personaje: ${e.message}"
                )
            }
        }
    }

    fun buscarCombinado(texto: String, tipo: String) {
        busquedaActual = texto
        tipoActual = tipo
        aplicarFiltrosYOrden()
    }

    fun setOrden(orden: OrdenLista) {
        _uiState.value = _uiState.value.copy(orden = orden)
        aplicarFiltrosYOrden()
    }

    fun toggleSoloFavoritos() {
        _uiState.value = _uiState.value.copy(soloFavoritos = !_uiState.value.soloFavoritos)
        aplicarFiltrosYOrden()
    }

    fun toggleFavorito(id: Long) {
        val eraFavorito = id in _uiState.value.favoritos
        viewModelScope.launch {
            favoritosRepo.toggleFavorito(id)
            if (SessionManager.isLoggedIn()) {
                try {
                    if (eraFavorito) favoritosRepo.eliminarRemoto(id)
                    else favoritosRepo.agregarRemoto(id)
                } catch (_: Exception) {}
            }
        }
    }

    fun sincronizarFavoritos() {
        viewModelScope.launch {
            try {
                favoritosRepo.sincronizarDesdeBackend()
            } catch (_: Exception) {}
        }
    }

    fun snackbarMostrado() {
        _uiState.value = _uiState.value.copy(snackbarMensaje = null)
    }

    fun limpiarError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    fun limpiarPersonajeSeleccionado() {
        _uiState.value = _uiState.value.copy(personajeSeleccionado = null)
    }

    private fun aplicarFiltrosYOrden() {
        val favs = _uiState.value.favoritos
        val soloFavs = _uiState.value.soloFavoritos
        val orden = _uiState.value.orden

        var resultado = listaCompleta

        if (tipoActual.isNotBlank() && !tipoActual.equals("todos", ignoreCase = true)) {
            resultado = resultado.filter { it.tipo.equals(tipoActual, ignoreCase = true) }
        }

        if (busquedaActual.isNotBlank()) {
            val q = busquedaActual.lowercase()
            resultado = resultado.filter {
                it.nombre.lowercase().contains(q) ||
                it.nombreReal.lowercase().contains(q) ||
                it.apodos.lowercase().contains(q)
            }
        }

        if (soloFavs) resultado = resultado.filter { it.id != null && it.id in favs }

        resultado = when (orden) {
            OrdenLista.NOMBRE_AZ -> resultado.sortedBy { it.nombre.lowercase() }
            OrdenLista.NOMBRE_ZA -> resultado.sortedByDescending { it.nombre.lowercase() }
            OrdenLista.TIPO -> resultado.sortedWith(
                compareBy(
                    { if (it.nombre.lowercase() in SPIDERMEN) 0 else 1 },
                    { ORDEN_TIPOS[it.tipo.lowercase()] ?: 99 },
                    { it.nombre.lowercase() }
                )
            )
        }

        _uiState.value = _uiState.value.copy(personajes = resultado)
    }

    companion object {
        private val SPIDERMEN = setOf(
            "spider-man", "miles morales", "spider-gwen",
            "spider-man 2099", "silk", "ben reilly", "kaine parker"
        )
        private val ORDEN_TIPOS = mapOf(
            "heroe" to 0, "aliado" to 1, "antiheroe" to 2, "villano" to 3
        )
    }
}
