package com.joel.spiderbiblioteca.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Style
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joel.spiderbiblioteca.ui.theme.*
import com.joel.spiderbiblioteca.viewmodel.PersonajeViewModel
import com.joel.spiderbiblioteca.viewmodel.TrajesViewModel
import com.joel.spiderbiblioteca.viewmodel.UniversosViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomScreen(
    personajeViewModel: PersonajeViewModel,
    trajesViewModel: TrajesViewModel,
    universosViewModel: UniversosViewModel,
    onPersonajeClick: (Long) -> Unit,
    onTrajeClick: (Int) -> Unit,
    onUniversoClick: (Int) -> Unit,
    onVolver: () -> Unit,
    onMenuPrincipal: () -> Unit
) {
    val c = LocalAppColors.current
    val uiState by personajeViewModel.uiState.collectAsState()
    val trajes by trajesViewModel.trajes.collectAsState()
    val universos by universosViewModel.universos.collectAsState()

    var cargando by remember { mutableStateOf(false) }
    var tipoSeleccionado by remember { mutableStateOf("") }

    val rotation by rememberInfiniteTransition(label = "spin").animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    LaunchedEffect(tipoSeleccionado) {
        if (tipoSeleccionado.isNotEmpty() && !cargando) {
            cargando = true
            delay(800)
            when (tipoSeleccionado) {
                "personaje" -> {
                    val lista = uiState.personajesTodos
                    if (lista.isNotEmpty()) {
                        val aleatorio = lista.random()
                        aleatorio.id?.let { onPersonajeClick(it) }
                    }
                }
                "traje" -> {
                    if (trajes.isNotEmpty()) onTrajeClick(trajes.random().id)
                }
                "universo" -> {
                    if (universos.isNotEmpty()) onUniversoClick(universos.random().id)
                }
            }
            cargando = false
            tipoSeleccionado = ""
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(c.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text("Modo Descubrir", color = BlancoPuro, fontWeight = FontWeight.Bold) },
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF4A148C))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                Icon(
                    imageVector = Icons.Default.Casino,
                    contentDescription = null,
                    tint = Color(0xFF9C27B0),
                    modifier = Modifier
                        .size(64.dp)
                        .then(if (cargando) Modifier.rotate(rotation) else Modifier)
                )

                Text(
                    text = "¿Qué quieres descubrir?",
                    color = c.textPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Selecciona una categoría y te llevaremos\na un elemento aleatorio del Spiderverso.",
                    color = c.textSecondary,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (cargando) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF9C27B0).copy(alpha = 0.15f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            CircularProgressIndicator(
                                color = Color(0xFF9C27B0),
                                modifier = Modifier.size(24.dp),
                                strokeWidth = 3.dp
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = "Seleccionando al azar...",
                                color = Color(0xFF9C27B0),
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    }
                } else {
                    RandomOpcionCard(
                        titulo = "Personaje Aleatorio",
                        descripcion = "Descubre un personaje del universo Spider-Man",
                        icono = Icons.Default.Person,
                        color = SpiderRed,
                        enabled = uiState.personajesTodos.isNotEmpty(),
                        onClick = { tipoSeleccionado = "personaje" }
                    )

                    RandomOpcionCard(
                        titulo = "Traje Aleatorio",
                        descripcion = "Explora uno de los icónicos trajes de Spider-Man",
                        icono = Icons.Default.Style,
                        color = Color(0xFF00897B),
                        enabled = trajes.isNotEmpty(),
                        onClick = { tipoSeleccionado = "traje" }
                    )

                    RandomOpcionCard(
                        titulo = "Universo Aleatorio",
                        descripcion = "Viaja a una realidad alternativa del Spider-Verse",
                        icono = Icons.Default.Place,
                        color = SpiderBlueLight,
                        enabled = universos.isNotEmpty(),
                        onClick = { tipoSeleccionado = "universo" }
                    )
                }
            }
        }
    }
}

@Composable
private fun RandomOpcionCard(
    titulo: String,
    descripcion: String,
    icono: ImageVector,
    color: Color,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled, onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = if (enabled) 0.12f else 0.05f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (enabled) 4.dp else 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icono,
                    contentDescription = null,
                    tint = if (enabled) color else color.copy(alpha = 0.4f),
                    modifier = Modifier.size(30.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = titulo,
                    color = if (enabled) color else color.copy(alpha = 0.4f),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (enabled) descripcion else "Cargando datos...",
                    color = if (enabled) color.copy(alpha = 0.75f) else color.copy(alpha = 0.3f),
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }
            Text(
                text = "🎲",
                fontSize = 22.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}
