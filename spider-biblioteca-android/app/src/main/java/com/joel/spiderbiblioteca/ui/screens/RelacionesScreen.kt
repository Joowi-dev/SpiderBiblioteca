package com.joel.spiderbiblioteca.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joel.spiderbiblioteca.data.model.CharacterRelationship
import com.joel.spiderbiblioteca.ui.theme.*
import com.joel.spiderbiblioteca.viewmodel.RelacionesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RelacionesScreen(
    viewModel: RelacionesViewModel,
    onVolver: () -> Unit,
    onMenuPrincipal: () -> Unit
) {
    val c = LocalAppColors.current
    val relaciones by viewModel.relacionesFiltradas.collectAsState()
    val filtroActual by viewModel.filtroTipo.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(c.background)
    ) {
        TopAppBar(
            title = { Text("Relaciones", color = BlancoPuro, fontWeight = FontWeight.Bold) },
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

        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.TIPOS) { tipo ->
                val seleccionado = filtroActual == tipo
                FilterChip(
                    selected = seleccionado,
                    onClick = { viewModel.setFiltro(tipo) },
                    label = {
                        Text(
                            tipo,
                            fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 13.sp
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = tipoRelacionColor(tipo),
                        selectedLabelColor = BlancoPuro,
                        containerColor = c.surfaceCard,
                        labelColor = c.textSecondary
                    )
                )
            }
        }

        Text(
            text = "${relaciones.size} relaciones",
            color = c.textSecondary,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
        )

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(relaciones) { relacion ->
                RelacionCard(relacion = relacion, colors = c)
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun RelacionCard(relacion: CharacterRelationship, colors: AppColors) {
    val tipoColor = tipoRelacionColor(relacion.tipo)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    PersonajeBadge(nombre = relacion.personaje1, color = SpiderRed)
                    Text("↔", color = colors.textSecondary, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    PersonajeBadge(nombre = relacion.personaje2, color = SpiderBlueLight)
                }
                TipoBadgeRelacion(tipo = relacion.tipo, color = tipoColor)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = relacion.descripcion,
                color = colors.textSecondary,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Importancia",
                    color = colors.textSecondary,
                    fontSize = 11.sp
                )
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    repeat(10) { i ->
                        Box(
                            modifier = Modifier
                                .size(width = 16.dp, height = 6.dp)
                                .clip(RoundedCornerShape(3.dp))
                                .background(
                                    if (i < relacion.importancia) tipoColor
                                    else tipoColor.copy(alpha = 0.2f)
                                )
                        )
                    }
                    Text(
                        text = "${relacion.importancia}/10",
                        color = tipoColor,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun PersonajeBadge(nombre: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color.copy(alpha = 0.15f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = nombre,
            color = color,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
    }
}

@Composable
private fun TipoBadgeRelacion(tipo: String, color: Color) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color.copy(alpha = 0.2f))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = tipo.uppercase(),
            color = color,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

fun tipoRelacionColor(tipo: String): Color = when (tipo) {
    "Familiar" -> Color(0xFFE65100)
    "Pareja" -> Color(0xFFE91E63)
    "Enemigo" -> Color(0xFFB71C1C)
    "Aliado" -> Color(0xFF2E7D32)
    "Mentor" -> Color(0xFF1565C0)
    "Rival" -> Color(0xFF6A1B9A)
    "Clon" -> Color(0xFF4E342E)
    else -> Color(0xFF546E7A)
}
