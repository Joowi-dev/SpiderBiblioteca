package com.joel.spiderbiblioteca.viewmodel

import androidx.lifecycle.ViewModel
import com.joel.spiderbiblioteca.data.model.PreguntaQuiz
import com.joel.spiderbiblioteca.data.seed.PREGUNTAS_QUIZ
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class QuizUiState(
    val preguntas: List<PreguntaQuiz> = emptyList(),
    val indicePregunta: Int = 0,
    val respuestaElegida: Int? = null,
    val mostrarResultado: Boolean = false,
    val correctas: Int = 0,
    val finalizado: Boolean = false,
    val categoriaActual: String = "Todas",
    val dificultadActual: String = "todas",
    val cantidadActual: Int = 10
) {
    val preguntaActual: PreguntaQuiz? get() = preguntas.getOrNull(indicePregunta)
    val totalPreguntas: Int get() = preguntas.size
    val progreso: Float get() = if (totalPreguntas == 0) 0f else indicePregunta.toFloat() / totalPreguntas
    val porcentaje: Int get() = if (totalPreguntas == 0) 0 else (correctas * 100) / totalPreguntas
}

class QuizViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    init {
        iniciarQuiz()
    }

    fun iniciarQuiz() {
        iniciarQuizConConfig("todas", "todas", 10)
    }

    fun iniciarQuizConConfig(categoria: String, dificultad: String, cantidad: Int) {
        val filtradas = PREGUNTAS_QUIZ
            .filter { categoria == "todas" || it.categoria == categoria }
            .filter { dificultad == "todas" || it.dificultad == dificultad }
            .shuffled()
            .take(cantidad)

        val preguntasFinales = if (filtradas.isEmpty()) PREGUNTAS_QUIZ.shuffled().take(cantidad) else filtradas

        _uiState.value = QuizUiState(
            preguntas = preguntasFinales,
            categoriaActual = categoria,
            dificultadActual = dificultad,
            cantidadActual = cantidad
        )
    }

    fun responder(opcionIndex: Int) {
        val state = _uiState.value
        val pregunta = state.preguntaActual ?: return
        if (state.mostrarResultado) return

        val esCorrecta = opcionIndex == pregunta.correcta
        _uiState.value = state.copy(
            respuestaElegida = opcionIndex,
            mostrarResultado = true,
            correctas = if (esCorrecta) state.correctas + 1 else state.correctas
        )
    }

    fun siguiente() {
        val state = _uiState.value
        val nuevoIndice = state.indicePregunta + 1
        if (nuevoIndice >= state.totalPreguntas) {
            _uiState.value = state.copy(finalizado = true, mostrarResultado = false, respuestaElegida = null)
        } else {
            _uiState.value = state.copy(
                indicePregunta = nuevoIndice,
                respuestaElegida = null,
                mostrarResultado = false
            )
        }
    }

    fun rangoDeResultado(porcentaje: Int): Pair<String, String> = when {
        porcentaje == 100 -> "🏆" to "¡Perfecto! Eres un Maestro del Spiderverso"
        porcentaje >= 80 -> "🕷️" to "¡Impresionante, True Believer!"
        porcentaje >= 60 -> "🕸️" to "¡Nada mal, Spider-Fan Experto!"
        porcentaje >= 40 -> "📖" to "¡Buen intento, Spider-Fan!"
        else -> "🆕" to "¡Sigue practicando, el Spiderverso es enorme!"
    }
}
