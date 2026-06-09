package com.joel.spiderbiblioteca.ui.screens

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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joel.spiderbiblioteca.ui.theme.*
import com.joel.spiderbiblioteca.viewmodel.QuizViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun QuizSetupScreen(
    viewModel: QuizViewModel,
    onIniciarQuiz: () -> Unit,
    onVolver: () -> Unit,
    onMenuPrincipal: () -> Unit
) {
    val c = LocalAppColors.current

    val categorias = listOf(
        "todas" to "Todas",
        "personajes" to "Personajes",
        "poderes" to "Poderes",
        "enemigos" to "Enemigos",
        "aliados" to "Aliados",
        "trajes" to "Trajes",
        "universos" to "Universos",
        "comics" to "Cómics",
        "peliculas" to "Películas"
    )
    val dificultades = listOf("todas" to "Todas", "facil" to "Fácil", "media" to "Media", "dificil" to "Difícil")
    val cantidades = listOf(5, 10, 15, 20)

    var categoriaSeleccionada by remember { mutableStateOf("todas") }
    var dificultadSeleccionada by remember { mutableStateOf("todas") }
    var cantidadSeleccionada by remember { mutableIntStateOf(10) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(c.background)
    ) {
        TopAppBar(
            title = { Text("Configurar Quiz", color = BlancoPuro, fontWeight = FontWeight.Bold) },
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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = "Personaliza tu quiz",
                color = c.textPrimary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Black
            )

            // Categoría
            SetupSection(titulo = "Categoría", textColor = c.textPrimary) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    categorias.forEach { (clave, nombre) ->
                        val seleccionada = categoriaSeleccionada == clave
                        SetupChip(
                            texto = nombre,
                            seleccionado = seleccionada,
                            color = SpiderRed,
                            surfaceCard = c.surfaceCard,
                            textSecondary = c.textSecondary,
                            onClick = { categoriaSeleccionada = clave }
                        )
                    }
                }
            }

            // Dificultad
            SetupSection(titulo = "Dificultad", textColor = c.textPrimary) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    dificultades.forEach { (clave, nombre) ->
                        val seleccionada = dificultadSeleccionada == clave
                        val color = when (clave) {
                            "facil" -> Color(0xFF2E7D32)
                            "media" -> Color(0xFFE65100)
                            "dificil" -> Color(0xFFB71C1C)
                            else -> SpiderBlueLight
                        }
                        SetupChip(
                            texto = nombre,
                            seleccionado = seleccionada,
                            color = color,
                            surfaceCard = c.surfaceCard,
                            textSecondary = c.textSecondary,
                            onClick = { dificultadSeleccionada = clave },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Cantidad
            SetupSection(titulo = "Número de preguntas", textColor = c.textPrimary) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    cantidades.forEach { cantidad ->
                        val seleccionado = cantidadSeleccionada == cantidad
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { cantidadSeleccionada = cantidad }
                                .then(
                                    if (seleccionado)
                                        Modifier.border(2.dp, SpiderRed, RoundedCornerShape(12.dp))
                                    else Modifier
                                ),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (seleccionado) SpiderRed.copy(alpha = 0.15f) else c.surfaceCard
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 14.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$cantidad",
                                    color = if (seleccionado) SpiderRed else c.textSecondary,
                                    fontSize = 18.sp,
                                    fontWeight = if (seleccionado) FontWeight.Black else FontWeight.Normal
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.iniciarQuizConConfig(
                        categoriaSeleccionada,
                        dificultadSeleccionada,
                        cantidadSeleccionada
                    )
                    onIniciarQuiz()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = SpiderRed)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(22.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "¡Empezar Quiz!",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SetupSection(
    titulo: String,
    textColor: androidx.compose.ui.graphics.Color,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = titulo,
            color = textColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.3.sp
        )
        content()
    }
}

@Composable
private fun SetupChip(
    texto: String,
    seleccionado: Boolean,
    color: Color,
    surfaceCard: Color,
    textSecondary: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (seleccionado) color.copy(alpha = 0.2f) else surfaceCard
            )
            .then(
                if (seleccionado)
                    Modifier.border(1.5.dp, color, RoundedCornerShape(20.dp))
                else Modifier.border(1.dp, textSecondary.copy(alpha = 0.3f), RoundedCornerShape(20.dp))
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = texto,
            color = if (seleccionado) color else textSecondary,
            fontSize = 13.sp,
            fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal
        )
    }
}
