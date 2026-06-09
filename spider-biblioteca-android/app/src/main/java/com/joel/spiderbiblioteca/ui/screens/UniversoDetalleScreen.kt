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
import com.joel.spiderbiblioteca.data.model.SpiderUniverse
import com.joel.spiderbiblioteca.ui.components.VisorImagenCompleta
import com.joel.spiderbiblioteca.ui.theme.*
import com.joel.spiderbiblioteca.ui.util.resolverImagenConPrefijo
import com.joel.spiderbiblioteca.viewmodel.UniversosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversoDetalleScreen(
    universoId: Int,
    viewModel: UniversosViewModel,
    onVolver: () -> Unit,
    onMenuPrincipal: () -> Unit
) {
    val c = LocalAppColors.current
    val universo = viewModel.obtenerPorId(universoId)
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
                        text = universo?.nombre ?: "Universo",
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SpiderBlueDark)
            )

            if (universo == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Universo no encontrado", color = c.textSecondary)
                }
            } else {
                UniversoDetalleContenido(
                    universo = universo,
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
                nombre = universo?.nombre ?: "",
                onCerrar = { mostrarVisor = false }
            )
        }
    }
}

@Composable
private fun UniversoDetalleContenido(
    universo: SpiderUniverse,
    onImageClick: (Any) -> Unit
) {
    val c = LocalAppColors.current
    val estiloColor = estiloDetalleColor(universo.estilo)
    val context = LocalContext.current
    val imagenModel = resolverImagenConPrefijo(context, "univ_", universo.nombre, universo.imagenUrl)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Header del universo
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = estiloColor.copy(alpha = 0.1f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(estiloColor.copy(alpha = 0.2f))
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
                            contentDescription = universo.nombre,
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
                        Text(
                            text = universo.codigo.take(8),
                            color = estiloColor,
                            fontWeight = FontWeight.Black,
                            fontSize = 11.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = universo.nombre,
                    color = c.textPrimary,
                    fontWeight = FontWeight.Black,
                    fontSize = 22.sp
                )
                Text(
                    text = universo.codigo,
                    color = estiloColor,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = estiloColor.copy(alpha = 0.2f)
                ) {
                    Text(
                        text = universo.estilo,
                        color = estiloColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }
            }
        }

        // Spider-Man principal
        DetalleSeccion(titulo = "Spider-Man Principal") {
            Text(
                text = universo.spiderManPrincipal,
                color = SpiderRedLight,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }

        // Descripción
        DetalleSeccion(titulo = "Descripción") {
            Text(
                text = universo.descripcion,
                color = c.textSecondary,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }

        // Personajes destacados
        DetalleSeccion(titulo = "Personajes Destacados") {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                universo.personajesDestacados.forEach { personaje ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .background(estiloColor, RoundedCornerShape(50))
                        )
                        Text(
                            text = personaje,
                            color = c.textSecondary,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun DetalleSeccion(
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

private fun estiloDetalleColor(estilo: String): Color = when (estilo.lowercase()) {
    "clásico", "clasico" -> Color(0xFFCC0000)
    "moderno" -> Color(0xFF1565C0)
    "alternativo" -> Color(0xFF00838F)
    "noir" -> Color(0xFF78909C)
    "futurista" -> Color(0xFF0288D1)
    "punk" -> Color(0xFF558B2F)
    "humorístico", "humoristico" -> Color(0xFFFF8F00)
    "anime / mecha" -> Color(0xFFAD1457)
    "oscuro / estratégico", "oscuro / estrategico" -> Color(0xFF4A148C)
    "clones" -> Color(0xFF4E342E)
    "cinematográfico", "cinematografico" -> Color(0xFFE65100)
    "videojuego moderno" -> Color(0xFF1B5E20)
    else -> Color(0xFF546E7A)
}
