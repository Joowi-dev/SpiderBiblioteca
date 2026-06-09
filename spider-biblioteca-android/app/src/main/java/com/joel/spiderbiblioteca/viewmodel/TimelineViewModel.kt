package com.joel.spiderbiblioteca.viewmodel

import androidx.lifecycle.ViewModel
import com.joel.spiderbiblioteca.data.model.TimelineEvent
import com.joel.spiderbiblioteca.data.seed.TimelineSeed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TimelineViewModel : ViewModel() {
    private val _eventos = MutableStateFlow(TimelineSeed.eventos)
    val eventos: StateFlow<List<TimelineEvent>> = _eventos.asStateFlow()
}
