package com.joel.spiderbiblioteca.viewmodel

import androidx.lifecycle.ViewModel
import com.joel.spiderbiblioteca.data.model.SpiderUniverse
import com.joel.spiderbiblioteca.data.seed.UniversosSeed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UniversosViewModel : ViewModel() {
    private val _universos = MutableStateFlow(UniversosSeed.universos)
    val universos: StateFlow<List<SpiderUniverse>> = _universos.asStateFlow()

    fun obtenerPorId(id: Int): SpiderUniverse? = _universos.value.find { it.id == id }
}
