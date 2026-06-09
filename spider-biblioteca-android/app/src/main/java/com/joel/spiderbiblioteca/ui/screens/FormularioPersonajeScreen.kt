package com.joel.spiderbiblioteca.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.joel.spiderbiblioteca.data.model.Personaje
import com.joel.spiderbiblioteca.ui.components.colorYNombrePorTipo
import com.joel.spiderbiblioteca.ui.theme.*
import com.joel.spiderbiblioteca.ui.util.resolverImagen
import com.joel.spiderbiblioteca.viewmodel.PersonajeViewModel

private val TIPOS_VALIDOS = listOf("heroe", "villano", "aliado", "antiheroe")
private val TIPOS_MOSTRADOS = listOf("Héroe", "Villano", "Aliado", "Antihéroe")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioPersonajeScreen(
    personajeId: Long?,
    viewModel: PersonajeViewModel,
    onVolver: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val esEdicion = personajeId != null
    val context = LocalContext.current

    var nombre by remember { mutableStateOf("") }
    var nombreReal by remember { mutableStateOf("") }
    var apodos by remember { mutableStateOf("") }
    var poderes by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var primeraAparicion by remember { mutableStateOf("") }
    var tipoIndex by remember { mutableIntStateOf(0) }
    var imagenUrl by remember { mutableStateOf("") }

    var errorNombre by remember { mutableStateOf("") }
    var errorNombreReal by remember { mutableStateOf("") }
    var cargadoDesdeApi by remember { mutableStateOf(false) }
    var mostrarDialogoSalir by remember { mutableStateOf(false) }

    fun hayCambios(): Boolean {
        if (!esEdicion) return nombre.isNotBlank() || nombreReal.isNotBlank() ||
                apodos.isNotBlank() || poderes.isNotBlank() || descripcion.isNotBlank() ||
                imagenUrl.isNotBlank()
        val p = uiState.personajeSeleccionado ?: return false
        return nombre != p.nombre || nombreReal != p.nombreReal || apodos != p.apodos ||
                poderes != p.poderes || descripcion != p.descripcion ||
                primeraAparicion != p.primeraAparicion || imagenUrl != p.imagenUrl ||
                TIPOS_VALIDOS[tipoIndex] != p.tipo
    }

    fun intentarVolver() {
        if (hayCambios()) mostrarDialogoSalir = true else onVolver()
    }

    BackHandler { intentarVolver() }

    LaunchedEffect(personajeId) {
        if (esEdicion && personajeId != null) viewModel.obtenerPersonaje(personajeId)
    }

    LaunchedEffect(uiState.personajeSeleccionado) {
        if (esEdicion && !cargadoDesdeApi) {
            uiState.personajeSeleccionado?.let { p ->
                nombre = p.nombre
                nombreReal = p.nombreReal
                apodos = p.apodos
                poderes = p.poderes
                descripcion = p.descripcion
                primeraAparicion = p.primeraAparicion
                imagenUrl = p.imagenUrl
                tipoIndex = TIPOS_VALIDOS.indexOfFirst { it.equals(p.tipo, ignoreCase = true) }.coerceAtLeast(0)
                cargadoDesdeApi = true
            }
        }
    }

    if (mostrarDialogoSalir) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoSalir = false },
            containerColor = SurfaceCard,
            title = { Text("¿Descartar cambios?", color = BlancoPuro, fontWeight = FontWeight.Bold) },
            text = { Text("Los cambios no guardados se perderán.", color = TextSecondary) },
            confirmButton = {
                TextButton(onClick = { mostrarDialogoSalir = false; onVolver() }) {
                    Text("Descartar", color = SpiderRedLight, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoSalir = false }) {
                    Text("Seguir editando", color = TextSecondary)
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(SpiderRedDark)
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { intentarVolver() }) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = BlancoPuro)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (esEdicion) "Editar personaje" else "Nuevo personaje",
                    style = MaterialTheme.typography.titleLarge,
                    color = BlancoPuro,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (uiState.isLoading && !cargadoDesdeApi) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = SpiderRed)
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CampoFormulario(
                    value = nombre,
                    onValueChange = { nombre = it; errorNombre = "" },
                    label = "Nombre *",
                    error = errorNombre
                )
                CampoFormulario(
                    value = nombreReal,
                    onValueChange = { nombreReal = it; errorNombreReal = "" },
                    label = "Nombre real *",
                    error = errorNombreReal
                )
                CampoFormulario(
                    value = apodos,
                    onValueChange = { apodos = it },
                    label = "Apodos"
                )
                CampoFormulario(
                    value = poderes,
                    onValueChange = { poderes = it },
                    label = "Poderes",
                    maxLines = 3,
                    minLines = 2
                )
                CampoFormulario(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = "Descripción",
                    maxLines = 5,
                    minLines = 3
                )
                CampoFormulario(
                    value = primeraAparicion,
                    onValueChange = { primeraAparicion = it },
                    label = "Primera aparición (ej: Amazing Fantasy #15, 1962)"
                )

                // URL de imagen + preview
                CampoFormulario(
                    value = imagenUrl,
                    onValueChange = { imagenUrl = it },
                    label = "URL de imagen"
                )
                val imagenModel = resolverImagen(context, nombre, imagenUrl)
                val tieneImagen = imagenUrl.isNotBlank() || run {
                    val key = "char_" + nombre.lowercase()
                        .replace(" ", "_").replace("-", "_")
                        .replace(".", "").replace("'", "").replace("!", "")
                    context.resources.getIdentifier(key, "drawable", context.packageName) != 0
                }
                if (tieneImagen) {
                    AsyncImage(
                        model = imagenModel,
                        contentDescription = "Preview imagen",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(BackgroundCard),
                        contentScale = ContentScale.Crop
                    )
                }

                // Tipo
                Text("Tipo *", color = TextSecondary, style = MaterialTheme.typography.labelSmall)
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TIPOS_VALIDOS.forEachIndexed { index, tipo ->
                        val (colorTipo, _) = colorYNombrePorTipo(tipo)
                        val seleccionado = tipoIndex == index
                        FilterChip(
                            selected = seleccionado,
                            onClick = { tipoIndex = index },
                            label = {
                                Text(
                                    TIPOS_MOSTRADOS[index],
                                    fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = colorTipo,
                                selectedLabelColor = BlancoPuro,
                                containerColor = SurfaceCard,
                                labelColor = TextSecondary
                            )
                        )
                    }
                }

                if (uiState.error != null) {
                    Text(
                        text = uiState.error!!,
                        color = SpiderRedLight,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        var valido = true
                        if (nombre.isBlank()) { errorNombre = "El nombre es obligatorio"; valido = false }
                        if (nombreReal.isBlank()) { errorNombreReal = "El nombre real es obligatorio"; valido = false }
                        if (!valido) return@Button

                        val personaje = Personaje(
                            id = if (esEdicion) personajeId else null,
                            nombre = nombre.trim(),
                            nombreReal = nombreReal.trim(),
                            apodos = apodos.trim(),
                            poderes = poderes.trim(),
                            descripcion = descripcion.trim(),
                            primeraAparicion = primeraAparicion.trim(),
                            tipo = TIPOS_VALIDOS[tipoIndex],
                            imagenUrl = imagenUrl.trim()
                        )

                        if (esEdicion && personajeId != null) {
                            viewModel.actualizarPersonaje(personajeId, personaje) { onVolver() }
                        } else {
                            viewModel.crearPersonaje(personaje) { onVolver() }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SpiderRed),
                    shape = RoundedCornerShape(12.dp),
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            color = BlancoPuro,
                            modifier = Modifier.size(20.dp),
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text = if (esEdicion) "Guardar cambios" else "Crear personaje",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CampoFormulario(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: String = "",
    maxLines: Int = 1,
    minLines: Int = 1
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, color = TextSecondary) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        maxLines = maxLines,
        minLines = minLines,
        isError = error.isNotEmpty(),
        supportingText = if (error.isNotEmpty()) {
            { Text(error, color = SpiderRedLight) }
        } else null,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = SpiderRed,
            unfocusedBorderColor = GrisMedio,
            focusedTextColor = TextPrimary,
            unfocusedTextColor = TextPrimary,
            cursorColor = SpiderRed,
            focusedContainerColor = SurfaceCard,
            unfocusedContainerColor = SurfaceCard,
            focusedLabelColor = SpiderRed
        )
    )
}
