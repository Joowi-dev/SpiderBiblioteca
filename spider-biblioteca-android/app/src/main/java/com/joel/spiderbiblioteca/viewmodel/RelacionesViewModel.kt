package com.joel.spiderbiblioteca.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joel.spiderbiblioteca.data.model.CharacterRelationship
import com.joel.spiderbiblioteca.data.seed.RELACIONES
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class RelacionesViewModel : ViewModel() {

    val TIPOS = listOf("Todos", "Familiar", "Pareja", "Enemigo", "Aliado", "Mentor", "Rival", "Clon")

    private val _filtroTipo = MutableStateFlow("Todos")
    val filtroTipo: StateFlow<String> = _filtroTipo

    val relacionesFiltradas: StateFlow<List<CharacterRelationship>> = _filtroTipo
        .map { tipo ->
            if (tipo == "Todos") RELACIONES
            else RELACIONES.filter { it.tipo == tipo }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RELACIONES
        )

    fun setFiltro(tipo: String) {
        _filtroTipo.value = tipo
    }
}
