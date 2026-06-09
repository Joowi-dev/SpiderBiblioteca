package com.joel.spiderbiblioteca.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.joel.spiderbiblioteca.data.model.SpiderSuit
import com.joel.spiderbiblioteca.ui.components.VisorImagenCompleta
import com.joel.spiderbiblioteca.ui.theme.*
import com.joel.spiderbiblioteca.ui.util.resolverImagenConPrefijo
import com.joel.spiderbiblioteca.viewmodel.TrajesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrajeDetalleScreen(
    trajeId: Int,
    viewModel: TrajesViewModel,
    onVolver: () -> Unit,
    onMenuPrincipal: () -> Unit
) {
    val c = LocalAppColors.current
    val traje = viewModel.obtenerPorId(trajeId)
    var mostrarVisor by remember { mutableStateOf(false) }
    var imagenVisor by remember { mutableStateOf<Any?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(c.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                    Text(
                        text = traje?.nombre ?: "Traje",
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

            if (traje == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Traje no encontrado", color = c.textSecondary)
                }
            } else {
                TrajeDetalleContenido(
                    traje = traje,
                    onImageClick = { modelo ->
                        imagenVisor = modelo
                        mostrarVisor = true
                    }
                )
            }
        }

        if (mostrarVisor && imagenVisor != null) {
            VisorImagenCompleta(
                imagenModel = imagenVisor!!,
                nombre = traje?.nombre ?: "",
                onCerrar = { mostrarVisor = false }
            )
        }
    }
}

@Composable
private fun TrajeDetalleContenido(
    traje: SpiderSuit,
    onImageClick: (Any) -> Unit
) {
    val c = LocalAppColors.current
    val popularidadColor = when {
        traje.popularidad >= 10 -> Color(0xFFFFD700)
        traje.popularidad >= 8 -> SpiderRed
        traje.popularidad >= 6 -> SpiderBlueLight
        else -> GrisMedio
    }
    val context = LocalContext.current
    val imagenModel = resolverImagenConPrefijo(context, "traje_", traje.nombre, traje.imagenUrl)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header con imagen
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = popularidadColor.copy(alpha = 0.08f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(popularidadColor.copy(alpha = 0.15f))
                        .then(
                            if (imagenModel != null)
                                Modifier.clickable { onImageClick(imagenModel) }
                            else Modifier
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (imagenModel != null) {
                        AsyncImage(
                            model = imagenModel,
                            contentDescription = traje.nombre,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                        // Indicador "Toca para ampliar"
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 6.dp)
                                .background(
                                    color = Color.Black.copy(alpha = 0.55f),
                                    shape = RoundedCornerShape(6.dp)
                                )
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "Toca para ampliar",
                                color = Color.White,
                                fontSize = 9.sp
                            )
                        }
                    } else {
                        Text(text = "🕷", fontSize = 72.sp)
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = traje.nombre,
                    color = c.textPrimary,
                    fontWeight = FontWeight.Black,
                    fontSize = 22.sp
                )
                Text(
                    text = traje.usuario,
                    color = SpiderRedLight,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(traje.popularidad) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = popularidadColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                Text(
                    text = "Popularidad: ${traje.popularidad}/10",
                    color = popularidadColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        TrajeSeccion(titulo = "Primera Aparición") {
            Text(text = traje.primeraAparicion, color = c.textSecondary, fontSize = 14.sp)
        }

        TrajeSeccion(titulo = "Descripción") {
            Text(text = traje.descripcion, color = c.textSecondary, fontSize = 14.sp, lineHeight = 20.sp)
        }

        TrajeSeccion(titulo = "Habilidades Especiales") {
            Text(text = traje.habilidades, color = c.textSecondary, fontSize = 14.sp, lineHeight = 20.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun TrajeSeccion(
    titulo: String,
    content: @Composable () -> Unit
) {
    val c = LocalAppColors.current
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = c.surfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = titulo,
                color = c.textPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                letterSpacing = 0.5.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}
