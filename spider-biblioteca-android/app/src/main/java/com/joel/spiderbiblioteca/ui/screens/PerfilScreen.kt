package com.joel.spiderbiblioteca.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.joel.spiderbiblioteca.data.local.ProfilePreferences
import com.joel.spiderbiblioteca.data.local.SessionManager
import com.joel.spiderbiblioteca.data.local.ThemeManager
import com.joel.spiderbiblioteca.ui.theme.*
import com.joel.spiderbiblioteca.ui.util.resolverImagen
import com.joel.spiderbiblioteca.viewmodel.PersonajeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    personajeViewModel: PersonajeViewModel,
    onVolver: () -> Unit,
    onMenuPrincipal: () -> Unit = {},
    onLogout: () -> Unit
) {
    val c = LocalAppColors.current
    val context = LocalContext.current
    val estado by personajeViewModel.uiState.collectAsState()
    val isSimbionte by ThemeManager.isSimbionte.collectAsState()

    val username = SessionManager.username ?: "Usuario"
    val total = estado.totalPersonajes
    val stats = estado.statsPorTipo
    val totalFavoritos = estado.favoritos.size

    val quizzesCompletados = ProfilePreferences.getQuizzesCompletados(context)
    val mejorPuntuacion = ProfilePreferences.getMejorPuntuacion(context)
    val rango = ProfilePreferences.calcularRango(quizzesCompletados, mejorPuntuacion)
    val emojiRango = ProfilePreferences.emojiRango(rango)

    var avatarNombre by remember { mutableStateOf(SessionManager.avatarNombre) }
    var avatarUrl by remember { mutableStateOf(SessionManager.avatarUrl) }
    var mostrarSelectorAvatar by remember { mutableStateOf(false) }
    var mostrarDialogoLogout by remember { mutableStateOf(false) }

    val tiposOrdenados = listOf("heroe", "villano", "aliado", "antiheroe")
    val nombreTipos = mapOf("heroe" to "Héroes", "villano" to "Villanos", "aliado" to "Aliados", "antiheroe" to "Antihéroes")
    val coloresTipos = mapOf("heroe" to ColorHeroe, "villano" to ColorVillano, "aliado" to ColorAliado, "antiheroe" to ColorAntiheroe)

    Box(modifier = Modifier.fillMaxSize().background(c.background)) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SpiderRedDark)
                    .statusBarsPadding()
                    .padding(horizontal = 8.dp, vertical = 12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = BlancoPuro)
                    }
                    Text(
                        "Mi Perfil", color = BlancoPuro, fontSize = 20.sp,
                        fontWeight = FontWeight.Black, modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onMenuPrincipal) {
                        Icon(Icons.Default.Home, contentDescription = "Menú principal", tint = BlancoPuro)
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Tarjeta usuario + avatar
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = c.surfaceCard)
                ) {
                    Row(
                        modifier = Modifier.padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(modifier = Modifier.size(72.dp).clickable { mostrarSelectorAvatar = true }) {
                            if (avatarNombre != null) {
                                AsyncImage(
                                    model = resolverImagen(context, avatarNombre!!, avatarUrl),
                                    contentDescription = "Avatar",
                                    modifier = Modifier.size(72.dp).clip(CircleShape).border(2.dp, SpiderRed, CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Box(
                                    modifier = Modifier.size(72.dp).clip(CircleShape).background(SpiderRed),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(username.take(1).uppercase(), color = BlancoPuro, fontWeight = FontWeight.Black, fontSize = 30.sp)
                                }
                            }
                            Box(
                                modifier = Modifier.align(Alignment.BottomEnd).size(22.dp).clip(CircleShape).background(SpiderBlue),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.CameraAlt, contentDescription = null, tint = BlancoPuro, modifier = Modifier.size(13.dp))
                            }
                        }
                        Column {
                            Text(username, color = c.textPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                            Text(rango, color = SpiderRed, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                            Text("$emojiRango Toca la foto para cambiarla", color = c.textSecondary, fontSize = 11.sp)
                        }
                    }
                }

                // Contadores rápidos
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    StatCounter("Personajes", "$total", Modifier.weight(1f), colors = c)
                    StatCounter("Favoritos", "$totalFavoritos", Modifier.weight(1f), colors = c,
                        icono = { Icon(Icons.Default.Favorite, null, tint = SpiderRed, modifier = Modifier.size(14.dp)) })
                    StatCounter("Quizzes", "$quizzesCompletados", Modifier.weight(1f), colors = c)
                }

                // Rango de fan
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = c.surfaceCard)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(emojiRango, fontSize = 40.sp)
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Rango de Fan", color = c.textSecondary, fontSize = 12.sp)
                            Text(rango, color = SpiderRed, fontSize = 18.sp, fontWeight = FontWeight.Black)
                            if (quizzesCompletados > 0) {
                                Text(
                                    "Mejor puntuación: $mejorPuntuacion%",
                                    color = c.textSecondary,
                                    fontSize = 12.sp
                                )
                            } else {
                                Text("Completa un quiz para subir de rango", color = c.textSecondary, fontSize = 12.sp)
                            }
                        }
                    }
                }

                // Tema actual
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = c.surfaceCard)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Tema Visual", color = c.textSecondary, fontSize = 12.sp)
                            Text(
                                text = if (isSimbionte) "🖤 Modo Simbionte" else "🔴 Modo Spider-Man",
                                color = c.textPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = if (isSimbionte) "Oscuro, elegante y agresivo" else "Claro, colorido y llamativo",
                                color = c.textSecondary,
                                fontSize = 12.sp
                            )
                        }
                        Switch(
                            checked = isSimbionte,
                            onCheckedChange = { ThemeManager.toggle(context) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = BlancoPuro,
                                checkedTrackColor = Color(0xFF3A0B55),
                                uncheckedThumbColor = BlancoPuro,
                                uncheckedTrackColor = SpiderRedDark
                            )
                        )
                    }
                }

                // Distribución por tipo
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = c.surfaceCard)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text("Distribución por tipo", color = c.textPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        tiposOrdenados.forEach { tipo ->
                            val count = stats[tipo] ?: 0
                            val fraccion = if (total > 0) count.toFloat() / total else 0f
                            val color = coloresTipos[tipo] ?: GrisMedio
                            val nombre = nombreTipos[tipo] ?: tipo

                            Column(modifier = Modifier.padding(bottom = 12.dp)) {
                                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                    Text(nombre, color = color, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                                    Text("$count personajes", color = c.textSecondary, fontSize = 12.sp)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                LinearProgressIndicator(
                                    progress = { fraccion },
                                    modifier = Modifier.fillMaxWidth().height(8.dp).clip(RoundedCornerShape(4.dp)),
                                    color = color,
                                    trackColor = c.backgroundCard
                                )
                            }
                        }
                    }
                }

                Button(
                    onClick = { mostrarDialogoLogout = true },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = SpiderRedDark),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Logout, contentDescription = null, tint = BlancoPuro)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cerrar sesión", color = BlancoPuro, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        if (mostrarDialogoLogout) {
            AlertDialog(
                onDismissRequest = { mostrarDialogoLogout = false },
                containerColor = c.surfaceCard,
                title = { Text("Cerrar sesión", color = c.textPrimary, fontWeight = FontWeight.Bold) },
                text = { Text("¿Seguro que quieres cerrar sesión?", color = c.textSecondary) },
                confirmButton = {
                    TextButton(onClick = { mostrarDialogoLogout = false; onLogout() }) {
                        Text("Salir", color = SpiderRedLight, fontWeight = FontWeight.Bold)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { mostrarDialogoLogout = false }) {
                        Text("Cancelar", color = c.textSecondary)
                    }
                }
            )
        }
    }

    if (mostrarSelectorAvatar) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = { mostrarSelectorAvatar = false },
            sheetState = sheetState,
            containerColor = c.surfaceCard,
            dragHandle = { BottomSheetDefaults.DragHandle(color = c.grisMedio) }
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Elige tu foto de perfil",
                    color = c.textPrimary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
                Text(
                    text = "Selecciona un personaje como avatar",
                    color = c.textSecondary,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 12.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (avatarNombre == null) SpiderRed.copy(alpha = 0.15f) else c.backgroundCard)
                        .border(
                            width = if (avatarNombre == null) 2.dp else 0.dp,
                            color = if (avatarNombre == null) SpiderRed else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable {
                            SessionManager.clearAvatar(context)
                            avatarNombre = null
                            avatarUrl = ""
                            mostrarSelectorAvatar = false
                        }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier.size(48.dp).clip(CircleShape).background(c.grisOscuro),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(username.take(1).uppercase(), color = BlancoPuro, fontWeight = FontWeight.Black, fontSize = 20.sp)
                    }
                    Column {
                        Text("Sin foto de perfil", color = c.textPrimary, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        Text("Usar inicial del nombre", color = c.textSecondary, fontSize = 12.sp)
                    }
                    if (avatarNombre == null) {
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier.size(20.dp).clip(CircleShape).background(SpiderRed),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("✓", color = BlancoPuro, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (estado.personajesTodos.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = SpiderRed)
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth().height(420.dp)
                    ) {
                        items(estado.personajesTodos) { personaje ->
                            val seleccionado = avatarNombre == personaje.nombre
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (seleccionado) SpiderRed.copy(alpha = 0.15f) else c.backgroundCard)
                                    .border(
                                        width = if (seleccionado) 2.dp else 0.dp,
                                        color = if (seleccionado) SpiderRed else Color.Transparent,
                                        shape = RoundedCornerShape(12.dp)
                                    )
                                    .clickable {
                                        SessionManager.saveAvatar(context, personaje.nombre, personaje.imagenUrl)
                                        avatarNombre = personaje.nombre
                                        avatarUrl = personaje.imagenUrl
                                        mostrarSelectorAvatar = false
                                    },
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box {
                                    AsyncImage(
                                        model = resolverImagen(context, personaje.nombre, personaje.imagenUrl),
                                        contentDescription = personaje.nombre,
                                        modifier = Modifier.fillMaxWidth().aspectRatio(1f)
                                            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                                        contentScale = ContentScale.Crop
                                    )
                                    if (seleccionado) {
                                        Box(
                                            modifier = Modifier.align(Alignment.TopEnd).padding(4.dp)
                                                .size(20.dp).clip(CircleShape).background(SpiderRed),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text("✓", color = BlancoPuro, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                                Text(
                                    text = personaje.nombre,
                                    color = if (seleccionado) SpiderRed else c.textSecondary,
                                    fontSize = 11.sp,
                                    fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
private fun StatCounter(
    titulo: String,
    valor: String,
    modifier: Modifier = Modifier,
    colors: AppColors,
    icono: (@Composable () -> Unit)? = null
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = colors.backgroundCard)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 14.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                if (icono != null) {
                    icono()
                    Spacer(modifier = Modifier.width(4.dp))
                }
                Text(valor, color = SpiderRed, fontSize = 22.sp, fontWeight = FontWeight.Black)
            }
            Text(titulo, color = colors.textSecondary, fontSize = 11.sp)
        }
    }
}
