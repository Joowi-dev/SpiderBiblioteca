package com.joel.spiderbiblioteca.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joel.spiderbiblioteca.data.model.TimelineEvent
import com.joel.spiderbiblioteca.ui.theme.*
import com.joel.spiderbiblioteca.viewmodel.TimelineViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimelineScreen(
    viewModel: TimelineViewModel,
    onVolver: () -> Unit,
    onMenuPrincipal: () -> Unit
) {
    val c = LocalAppColors.current
    val eventos by viewModel.eventos.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(c.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Línea Temporal",
                    color = BlancoPuro,
                    fontWeight = FontWeight.Bold
                )
            },
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

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(0.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(eventos) { index, evento ->
                TimelineItem(
                    evento = evento,
                    index = index,
                    isLast = index == eventos.lastIndex
                )
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun TimelineItem(
    evento: TimelineEvent,
    index: Int,
    isLast: Boolean
) {
    val c = LocalAppColors.current
    val tipoColor = when (evento.tipo) {
        "comic" -> SpiderRed
        "pelicula" -> Color(0xFFE65100)
        "juego" -> Color(0xFF1B5E20)
        "multiverso" -> Color(0xFF4A148C)
        else -> GrisMedio
    }
    val tipoLabel = when (evento.tipo) {
        "comic" -> "Cómic"
        "pelicula" -> "Película"
        "juego" -> "Juego"
        "multiverso" -> "Multiverso"
        else -> evento.tipo
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Línea y círculo del timeline
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(36.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(tipoColor.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${index + 1}",
                    color = tipoColor,
                    fontWeight = FontWeight.Black,
                    fontSize = 13.sp
                )
            }
            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(16.dp)
                        .background(c.grisMedio.copy(alpha = 0.4f))
                )
            }
        }

        // Tarjeta del evento
        Card(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 12.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = c.surfaceCard),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = evento.titulo,
                            color = c.textPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = evento.etapa,
                            color = SpiderRedLight,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = tipoColor.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = tipoLabel,
                            color = tipoColor,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = evento.descripcion,
                    color = c.textSecondary,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )

                if (evento.personajes.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Personajes: ${evento.personajes.joinToString(", ")}",
                        color = c.textSecondary.copy(alpha = 0.7f),
                        fontSize = 11.sp,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }
        }
    }
}
