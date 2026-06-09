package com.joel.spiderbiblioteca.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.joel.spiderbiblioteca.R
import com.joel.spiderbiblioteca.data.local.SessionManager
import com.joel.spiderbiblioteca.data.local.ThemeManager
import com.joel.spiderbiblioteca.ui.theme.*
import com.joel.spiderbiblioteca.ui.util.resolverImagen
import com.joel.spiderbiblioteca.viewmodel.PersonajeViewModel

@Composable
fun MenuPrincipalScreen(
    viewModel: PersonajeViewModel,
    onPersonajesClick: () -> Unit,
    onQuizClick: () -> Unit,
    onPerfilClick: () -> Unit,
    onTimelineClick: () -> Unit,
    onUniversosClick: () -> Unit,
    onTrajesClick: () -> Unit,
    onRelacionesClick: () -> Unit,
    onDescubrirClick: () -> Unit,
    onLogout: () -> Unit
) {
    val c = LocalAppColors.current
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val username = SessionManager.username ?: "Usuario"
    val avatarNombre = SessionManager.avatarNombre
    val avatarUrl = SessionManager.avatarUrl
    val isSimbionte by ThemeManager.isSimbionte.collectAsState()

    var mostrarDialogoLogout by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(c.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header con gradiente
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(SpiderRedDark, c.background),
                            startY = 0f,
                            endY = 400f
                        )
                    )
                    .statusBarsPadding()
                    .padding(horizontal = 24.dp, vertical = 28.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(R.drawable.ic_spider_logo),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = "Spider-Biblioteca",
                        color = BlancoPuro,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = "Universo Marvel de Spider-Man",
                        color = BlancoPuro.copy(alpha = 0.65f),
                        fontSize = 13.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    // Toggle de tema
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color.Black.copy(alpha = 0.3f))
                            .clickable { ThemeManager.toggle(context) }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (isSimbionte) "🖤" else "🔴",
                            fontSize = 16.sp
                        )
                        Text(
                            text = if (isSimbionte) "Modo Simbionte" else "Modo Spider-Man",
                            color = BlancoPuro,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Icon(
                            imageVector = Icons.Default.SwapHoriz,
                            contentDescription = "Cambiar tema",
                            tint = BlancoPuro,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(4.dp))

            // Saludo con avatar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                if (avatarNombre != null) {
                    AsyncImage(
                        model = resolverImagen(context, avatarNombre, avatarUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .border(2.dp, SpiderRed, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(CircleShape)
                            .background(SpiderRed),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = username.take(1).uppercase(),
                            color = BlancoPuro,
                            fontWeight = FontWeight.Black,
                            fontSize = 24.sp
                        )
                    }
                }
                Column {
                    Text(text = "¡Hola de nuevo,", color = c.textSecondary, fontSize = 13.sp)
                    Text(text = username, color = c.textPrimary, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(8.dp))

            // Personajes - card grande
            MenuCardGrande(
                titulo = "Personajes",
                descripcion = if (uiState.totalPersonajes > 0)
                    "${uiState.totalPersonajes} personajes del universo Spider-Man"
                else "Explorar el universo Spider-Man",
                color = SpiderRed,
                colorSurface = Color(0xFF2A0000),
                icono = Icons.Default.Groups,
                onClick = onPersonajesClick,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(12.dp))

            // Quiz y Perfil
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MenuCardChica(
                    titulo = "Quiz",
                    descripcion = "¿Cuánto sabes del Spiderverso?",
                    color = SpiderBlueLight,
                    colorSurface = Color(0xFF00112B),
                    icono = Icons.Default.EmojiEvents,
                    onClick = onQuizClick,
                    modifier = Modifier.weight(1f)
                )
                MenuCardChica(
                    titulo = "Mi Perfil",
                    descripcion = "Estadísticas, rango y avatar",
                    color = Color(0xFF546E7A),
                    colorSurface = Color(0xFF0D1B20),
                    icono = Icons.Default.ManageAccounts,
                    onClick = onPerfilClick,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(12.dp))

            // Línea Temporal - card grande
            MenuCardGrande(
                titulo = "Línea Temporal",
                descripcion = "18 eventos clave del universo Spider-Man",
                color = Color(0xFF9C27B0),
                colorSurface = Color(0xFF1A0029),
                icono = Icons.Default.Schedule,
                onClick = onTimelineClick,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(12.dp))

            // Universos y Trajes
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MenuCardChica(
                    titulo = "Universos",
                    descripcion = "12 realidades del Spider-Verse",
                    color = SpiderBlueLight,
                    colorSurface = Color(0xFF00112B),
                    icono = Icons.Default.Place,
                    onClick = onUniversosClick,
                    modifier = Modifier.weight(1f)
                )
                MenuCardChica(
                    titulo = "Trajes",
                    descripcion = "18 trajes icónicos",
                    color = Color(0xFF00897B),
                    colorSurface = Color(0xFF00201C),
                    icono = Icons.Default.Style,
                    onClick = onTrajesClick,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(12.dp))

            // Relaciones y Descubrir
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MenuCardChica(
                    titulo = "Relaciones",
                    descripcion = "20 vínculos entre personajes",
                    color = Color(0xFFE91E63),
                    colorSurface = Color(0xFF2A0015),
                    icono = Icons.Default.Favorite,
                    onClick = onRelacionesClick,
                    modifier = Modifier.weight(1f)
                )
                MenuCardChica(
                    titulo = "Descubrir",
                    descripcion = "Elemento aleatorio del Spiderverso",
                    color = Color(0xFF9C27B0),
                    colorSurface = Color(0xFF1A0029),
                    icono = Icons.Default.Casino,
                    onClick = onDescubrirClick,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(28.dp))

            TextButton(
                onClick = { mostrarDialogoLogout = true },
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = null,
                    tint = c.textSecondary,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text("Cerrar sesión", color = c.textSecondary, fontSize = 14.sp)
            }
        }
    }

    if (mostrarDialogoLogout) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoLogout = false },
            title = { Text("Cerrar sesión", color = c.textPrimary) },
            text = { Text("¿Seguro que quieres salir de tu cuenta?", color = c.textSecondary) },
            confirmButton = {
                TextButton(onClick = {
                    mostrarDialogoLogout = false
                    onLogout()
                }) {
                    Text("Salir", color = SpiderRed, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoLogout = false }) {
                    Text("Cancelar", color = c.textSecondary)
                }
            },
            containerColor = c.surfaceCard
        )
    }
}

@Composable
private fun MenuCardGrande(
    titulo: String,
    descripcion: String,
    color: Color,
    colorSurface: Color,
    icono: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icono, contentDescription = null, tint = color, modifier = Modifier.size(30.dp))
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = titulo, color = BlancoPuro, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(text = descripcion, color = BlancoPuro.copy(alpha = 0.6f), fontSize = 13.sp)
            }
            Icon(
                painter = painterResource(android.R.drawable.ic_media_play),
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun MenuCardChica(
    titulo: String,
    descripcion: String,
    color: Color,
    colorSurface: Color,
    icono: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorSurface),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icono, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
            }
            Text(text = titulo, color = BlancoPuro, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(text = descripcion, color = BlancoPuro.copy(alpha = 0.6f), fontSize = 11.sp, lineHeight = 15.sp)
        }
    }
}
