package com.joel.spiderbiblioteca.ui.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.joel.spiderbiblioteca.ui.components.VisorImagenCompleta
import com.joel.spiderbiblioteca.ui.components.colorYNombrePorTipo
import com.joel.spiderbiblioteca.ui.theme.*
import com.joel.spiderbiblioteca.ui.util.resolverImagen
import com.joel.spiderbiblioteca.viewmodel.PersonajeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallePersonajeScreen(
    personajeId: Long,
    viewModel: PersonajeViewModel,
    onVolver: () -> Unit,
    onEditar: (Long) -> Unit,
    onMenuPrincipal: () -> Unit = {}
) {
    val c = LocalAppColors.current
    val uiState by viewModel.uiState.collectAsState()
    var mostrarDialogoEliminar by remember { mutableStateOf(false) }
    var mostrarImagenCompleta by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var imagenVisor by remember { mutableStateOf<Any?>(null) }
    var nombreVisor by remember { mutableStateOf("") }

    LaunchedEffect(personajeId) {
        viewModel.obtenerPersonaje(personajeId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(c.background)
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = SpiderRed
                )
            }

            uiState.error != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(uiState.error!!, color = SpiderRedLight)
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = onVolver,
                        colors = ButtonDefaults.buttonColors(containerColor = SpiderRed)
                    ) {
                        Text("Volver")
                    }
                }
            }

            uiState.personajeSeleccionado != null -> {
                val personaje = uiState.personajeSeleccionado!!
                val (colorTipo, nombreTipo) = colorYNombrePorTipo(personaje.tipo)
                val imagenModel = resolverImagen(context, personaje.nombre, personaje.imagenUrl)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Imagen + cabecera
                    Box(modifier = Modifier.fillMaxWidth()) {
                        AsyncImage(
                            model = imagenModel,
                            contentDescription = "Imagen de ${personaje.nombre}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(280.dp)
                                .background(c.backgroundCard)
                                .clickable {
                                    imagenVisor = imagenModel
                                    nombreVisor = personaje.nombre
                                    mostrarImagenCompleta = true
                                },
                            contentScale = ContentScale.Crop
                        )

                        // Indicador táctil en esquina
                        Box(
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(10.dp)
                                .background(
                                    color = Color.Black.copy(alpha = 0.55f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Toca para ampliar",
                                color = Color.White,
                                fontSize = 10.sp
                            )
                        }

                        // Barra superior con botón volver
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .statusBarsPadding()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(
                                onClick = onVolver,
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = Color.Black.copy(alpha = 0.7f)
                                )
                            ) {
                                Icon(
                                    Icons.Default.ArrowBack,
                                    contentDescription = "Volver",
                                    tint = BlancoPuro
                                )
                            }

                            Row {
                                IconButton(
                                    onClick = onMenuPrincipal,
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = Color.Black.copy(alpha = 0.7f)
                                    )
                                ) {
                                    Icon(Icons.Default.Home, contentDescription = "Menú principal", tint = BlancoPuro)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                IconButton(
                                    onClick = {
                                        val texto = buildString {
                                            append("${personaje.nombre} (${personaje.nombreReal})\n")
                                            if (personaje.poderes.isNotBlank()) append("Poderes: ${personaje.poderes}\n")
                                            if (personaje.descripcion.isNotBlank()) append("\n${personaje.descripcion}")
                                        }
                                        val intent = Intent(Intent.ACTION_SEND).apply {
                                            type = "text/plain"
                                            putExtra(Intent.EXTRA_TEXT, texto)
                                        }
                                        context.startActivity(Intent.createChooser(intent, "Compartir personaje"))
                                    },
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = Color.Black.copy(alpha = 0.7f)
                                    )
                                ) {
                                    Icon(Icons.Default.Share, contentDescription = "Compartir", tint = BlancoPuro)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                IconButton(
                                    onClick = { onEditar(personajeId) },
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = SpiderBlue.copy(alpha = 0.85f)
                                    )
                                ) {
                                    Icon(Icons.Default.Edit, contentDescription = "Editar", tint = BlancoPuro)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                IconButton(
                                    onClick = { mostrarDialogoEliminar = true },
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = SpiderRedDark.copy(alpha = 0.85f)
                                    )
                                ) {
                                    Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = BlancoPuro)
                                }
                            }
                        }
                    }

                    // Información
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = personaje.nombre,
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = c.textPrimary,
                                    fontWeight = FontWeight.Black
                                )
                                Text(
                                    text = personaje.nombreReal,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = c.textSecondary
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(colorTipo.copy(alpha = 0.25f))
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = nombreTipo.uppercase(),
                                    color = colorTipo,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        if (personaje.apodos.isNotBlank()) {
                            SeccionDetalle(titulo = "Apodos", contenido = personaje.apodos)
                        }
                        if (personaje.poderes.isNotBlank()) {
                            SeccionDetalle(titulo = "Poderes", contenido = personaje.poderes)
                        }
                        if (personaje.descripcion.isNotBlank()) {
                            SeccionDetalle(titulo = "Descripción", contenido = personaje.descripcion)
                        }
                        if (personaje.primeraAparicion.isNotBlank()) {
                            SeccionDetalle(titulo = "Primera aparición", contenido = personaje.primeraAparicion)
                        }
                    }
                }

            }
        }

        // Visor de imagen a pantalla completa (overlay sobre todo el contenido)
        if (mostrarImagenCompleta && imagenVisor != null) {
            VisorImagenCompleta(
                imagenModel = imagenVisor!!,
                nombre = nombreVisor,
                onCerrar = { mostrarImagenCompleta = false }
            )
        }
    }

    // Diálogo confirmación eliminar
    if (mostrarDialogoEliminar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoEliminar = false },
            containerColor = c.surfaceCard,
            title = {
                Text("¿Eliminar personaje?", color = c.textPrimary, fontWeight = FontWeight.Bold)
            },
            text = {
                Text(
                    "Esta acción no se puede deshacer.",
                    color = c.textSecondary
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarDialogoEliminar = false
                        viewModel.eliminarPersonaje(personajeId) { onVolver() }
                    }
                ) {
                    Text("Eliminar", color = SpiderRedLight, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoEliminar = false }) {
                    Text("Cancelar", color = c.textSecondary)
                }
            }
        )
    }
}


@Composable
private fun SeccionDetalle(titulo: String, contenido: String) {
    val c = LocalAppColors.current
    Column(modifier = Modifier.padding(bottom = 16.dp)) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.labelSmall,
            color = SpiderRed,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = contenido,
            style = MaterialTheme.typography.bodyMedium,
            color = c.textPrimary
        )
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(color = c.surfaceCard, thickness = 1.dp)
    }
}
