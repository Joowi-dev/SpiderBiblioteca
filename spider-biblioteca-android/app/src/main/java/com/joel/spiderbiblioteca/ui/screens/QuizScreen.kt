package com.joel.spiderbiblioteca.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joel.spiderbiblioteca.data.local.ProfilePreferences
import com.joel.spiderbiblioteca.ui.theme.*
import com.joel.spiderbiblioteca.viewmodel.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    viewModel: QuizViewModel,
    onVolver: () -> Unit,
    onMenuPrincipal: () -> Unit = {}
) {
    val c = LocalAppColors.current
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    // Guardar stats cuando el quiz termina
    LaunchedEffect(uiState.finalizado) {
        if (uiState.finalizado) {
            ProfilePreferences.incrementarQuizzes(context)
            ProfilePreferences.actualizarMejorPuntuacion(context, uiState.porcentaje)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize().background(c.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text("Quiz Spider-Man", color = BlancoPuro, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = BlancoPuro)
                    }
                },
                actions = {
                    IconButton(onClick = onMenuPrincipal) {
                        Icon(Icons.Default.Home, contentDescription = "Menú principal", tint = BlancoPuro)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SpiderRedDark)
            )

            when {
                uiState.finalizado -> PantallaResultados(
                    correctas = uiState.correctas,
                    total = uiState.totalPreguntas,
                    porcentaje = uiState.porcentaje,
                    viewModel = viewModel,
                    onReintentar = { viewModel.iniciarQuiz() },
                    onVolver = onVolver,
                    colors = c
                )

                uiState.preguntaActual != null -> {
                    val pregunta = uiState.preguntaActual!!

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 20.dp)
                    ) {
                        Spacer(Modifier.height(16.dp))

                        // Categoría y dificultad
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (pregunta.categoria != "general") {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(SpiderBlue.copy(alpha = 0.2f))
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = pregunta.categoria.replaceFirstChar { it.uppercase() },
                                        color = SpiderBlueLight,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            val difColor = when (pregunta.dificultad) {
                                "facil" -> Color(0xFF2E7D32)
                                "dificil" -> Color(0xFFB71C1C)
                                else -> Color(0xFFE65100)
                            }
                            val difLabel = when (pregunta.dificultad) {
                                "facil" -> "Fácil"
                                "dificil" -> "Difícil"
                                else -> "Media"
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(difColor.copy(alpha = 0.2f))
                                    .padding(horizontal = 8.dp, vertical = 3.dp)
                            ) {
                                Text(text = difLabel, color = difColor, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }

                        Spacer(Modifier.height(8.dp))

                        // Progreso
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Pregunta ${uiState.indicePregunta + 1} de ${uiState.totalPreguntas}",
                                color = c.textSecondary, fontSize = 13.sp
                            )
                            Text(
                                text = "${uiState.correctas} correctas",
                                color = ColorAliado, fontSize = 13.sp, fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(Modifier.height(8.dp))

                        LinearProgressIndicator(
                            progress = { (uiState.indicePregunta.toFloat() + 1) / uiState.totalPreguntas },
                            modifier = Modifier.fillMaxWidth().height(6.dp),
                            color = SpiderRed,
                            trackColor = c.surfaceCard
                        )

                        Spacer(Modifier.height(20.dp))

                        // Tarjeta pregunta
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = c.surfaceCard),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = pregunta.enunciado,
                                color = c.textPrimary,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold,
                                lineHeight = 25.sp,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.padding(20.dp)
                            )
                        }

                        Spacer(Modifier.height(16.dp))

                        // Opciones
                        pregunta.opciones.forEachIndexed { index, opcion ->
                            val esSeleccionada = uiState.respuestaElegida == index
                            val esCorrecta = index == pregunta.correcta
                            val yaRespondio = uiState.mostrarResultado

                            val colorFondo by animateColorAsState(
                                targetValue = when {
                                    !yaRespondio -> c.surfaceCard
                                    esCorrecta -> ColorAliadoSurface
                                    esSeleccionada -> ColorVillanoSurface
                                    else -> c.surfaceCard
                                },
                                animationSpec = tween(300),
                                label = "opcion_bg"
                            )

                            val colorBorde = when {
                                !yaRespondio -> c.grisMedio
                                esCorrecta -> ColorAliado
                                esSeleccionada -> ColorVillano
                                else -> c.grisMedio
                            }

                            val colorTexto = when {
                                !yaRespondio -> c.textPrimary
                                esCorrecta -> ColorAliado
                                esSeleccionada -> SpiderRedLight
                                else -> c.textSecondary
                            }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 5.dp)
                                    .border(
                                        width = if (yaRespondio && (esCorrecta || esSeleccionada)) 2.dp else 1.dp,
                                        color = colorBorde,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable(enabled = !yaRespondio) { viewModel.responder(index) },
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = colorFondo)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 14.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .background(colorBorde.copy(alpha = 0.2f), RoundedCornerShape(6.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = ('A' + index).toString(),
                                            color = colorBorde, fontWeight = FontWeight.Bold, fontSize = 13.sp
                                        )
                                    }
                                    Text(
                                        text = opcion, color = colorTexto, fontSize = 15.sp,
                                        lineHeight = 20.sp, modifier = Modifier.weight(1f)
                                    )
                                    if (yaRespondio && esCorrecta)
                                        Text("✓", color = ColorAliado, fontSize = 18.sp, fontWeight = FontWeight.Black)
                                    else if (yaRespondio && esSeleccionada && !esCorrecta)
                                        Text("✗", color = ColorVillano, fontSize = 18.sp, fontWeight = FontWeight.Black)
                                }
                            }
                        }

                        // Explicación tras responder
                        if (uiState.mostrarResultado && pregunta.explicacion.isNotEmpty()) {
                            Spacer(Modifier.height(12.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = ColorAliadoSurface.copy(alpha = 0.7f)
                                )
                            ) {
                                Row(
                                    modifier = Modifier.padding(14.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Text("💡", fontSize = 18.sp)
                                    Text(
                                        text = pregunta.explicacion,
                                        color = ColorAliado,
                                        fontSize = 13.sp,
                                        lineHeight = 19.sp
                                    )
                                }
                            }
                        }

                        // Botón siguiente
                        if (uiState.mostrarResultado) {
                            Spacer(Modifier.height(20.dp))
                            Button(
                                onClick = { viewModel.siguiente() },
                                modifier = Modifier.fillMaxWidth().height(52.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = SpiderRed)
                            ) {
                                Text(
                                    text = if (uiState.indicePregunta + 1 >= uiState.totalPreguntas) "Ver resultados" else "Siguiente →",
                                    fontWeight = FontWeight.Bold, fontSize = 16.sp
                                )
                            }
                            Spacer(Modifier.height(32.dp))
                        } else {
                            Spacer(Modifier.height(32.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PantallaResultados(
    correctas: Int,
    total: Int,
    porcentaje: Int,
    viewModel: QuizViewModel,
    onReintentar: () -> Unit,
    onVolver: () -> Unit,
    colors: AppColors
) {
    val (emoji, mensaje) = viewModel.rangoDeResultado(porcentaje)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(16.dp))

        Text(emoji, fontSize = 72.sp)
        Spacer(Modifier.height(16.dp))

        Text(text = "Resultado final", color = colors.textSecondary, fontSize = 14.sp)
        Spacer(Modifier.height(8.dp))

        Text(
            text = "$correctas / $total",
            color = colors.textPrimary,
            fontSize = 56.sp,
            fontWeight = FontWeight.Black
        )
        Text(text = "respuestas correctas", color = colors.textSecondary, fontSize = 14.sp)
        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .background(SpiderRed.copy(alpha = 0.15f))
                .border(1.dp, SpiderRed.copy(alpha = 0.5f), RoundedCornerShape(20.dp))
                .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Text(
                text = "$porcentaje%",
                color = SpiderRed,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black
            )
        }

        Spacer(Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = colors.surfaceCard),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = mensaje,
                color = colors.textPrimary,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(20.dp)
            )
        }

        Spacer(Modifier.height(24.dp))

        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Aciertos", color = ColorAliado, fontSize = 13.sp)
                Text("$porcentaje%", color = ColorAliado, fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }
            LinearProgressIndicator(
                progress = { porcentaje / 100f },
                modifier = Modifier.fillMaxWidth().height(8.dp),
                color = ColorAliado,
                trackColor = colors.surfaceCard
            )
        }

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = onReintentar,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = SpiderRed)
        ) {
            Icon(Icons.Default.Replay, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text("Jugar de nuevo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

        Spacer(Modifier.height(12.dp))

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = colors.textSecondary)
        ) {
            Text("Volver al menú", fontSize = 16.sp)
        }

        Spacer(Modifier.height(24.dp))
    }
}
