package com.joel.spiderbiblioteca.viewmodel

import androidx.lifecycle.ViewModel
import com.joel.spiderbiblioteca.data.model.SpiderSuit
import com.joel.spiderbiblioteca.data.seed.TrajesSeed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TrajesViewModel : ViewModel() {
    private val _trajes = MutableStateFlow(TrajesSeed.trajes)
    val trajes: StateFlow<List<SpiderSuit>> = _trajes.asStateFlow()

    fun obtenerPorId(id: Int): SpiderSuit? = _trajes.value.find { it.id == id }
}
