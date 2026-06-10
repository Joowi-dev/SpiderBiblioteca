package com.joel.spiderbiblioteca.ui.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
                .verticalScroll(rememberScrollState())
        ) {

            // ─── HEADER ──────────────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                if (isSimbionte) Color(0xFF6B0000) else Color(0xFFCC0000),
                                SpiderRed,
                                SpiderRedDark,
                                c.background
                            )
                        )
                    )
                    .statusBarsPadding()
                    .padding(top = 28.dp, bottom = 36.dp, start = 24.dp, end = 24.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .background(
                                brush = Brush.radialGradient(
                                    colors = listOf(Color.White.copy(alpha = 0.18f), Color.Transparent)
                                ),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_spider_logo),
                            contentDescription = null,
                            modifier = Modifier.size(78.dp)
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Text(
                        text = "Spider Wiki",
                        color = BlancoPuro,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = "Explora personajes, trajes, universos\ny conexiones del Spider-Verse",
                        color = BlancoPuro.copy(alpha = 0.70f),
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 19.sp
                    )

                    Spacer(Modifier.height(20.dp))

                    // Toggle de tema
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color.Black.copy(alpha = 0.28f))
                            .clickable { ThemeManager.toggle(context) }
                            .padding(horizontal = 18.dp, vertical = 9.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (isSimbionte) "🖤" else "🔴",
                            fontSize = 14.sp
                        )
                        Text(
                            text = if (isSimbionte) "Modo Simbionte" else "Modo Spider-Man",
                            color = BlancoPuro,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Icon(
                            imageVector = Icons.Default.SwapHoriz,
                            contentDescription = null,
                            tint = BlancoPuro.copy(alpha = 0.8f),
                            modifier = Modifier.size(15.dp)
                        )
                    }
                }
            }

            // ─── SALUDO ──────────────────────────────────────────────────────────
            Spacer(Modifier.height(4.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                if (avatarNombre != null) {
                    AsyncImage(
                        model = resolverImagen(context, avatarNombre, avatarUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .border(2.dp, SpiderRed, CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(52.dp)
                            .clip(CircleShape)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(SpiderRed, SpiderRedDark)
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = username.take(1).uppercase(),
                            color = BlancoPuro,
                            fontWeight = FontWeight.Black,
                            fontSize = 20.sp
                        )
                    }
                }

                Column {
                    Text(
                        text = "Bienvenido,",
                        color = c.textSecondary,
                        fontSize = 12.sp
                    )
                    Text(
                        text = username,
                        color = c.textPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        text = "Elige una sección para comenzar.",
                        color = c.textSecondary,
                        fontSize = 11.sp
                    )
                }
            }

            // Píldoras de estadísticas
            if (uiState.totalPersonajes > 0) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MenuStatPill("${uiState.totalPersonajes}", "personajes", SpiderRed, c)
                    MenuStatPill("18", "trajes", SpiderBlueLight, c)
                    MenuStatPill("12", "universos", Color(0xFF9C27B0), c)
                }
            }

            Spacer(Modifier.height(24.dp))

            // ─── SEPARADOR DE SECCIÓN ─────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "EXPLORAR",
                    color = c.textSecondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    color = c.grisMedio.copy(alpha = 0.35f),
                    thickness = 1.dp
                )
            }

            Spacer(Modifier.height(14.dp))

            // ─── GRID 2×4 DE TARJETAS ────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Fila 1
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    FeatureCard(
                        titulo = "Personajes",
                        descripcion = if (uiState.totalPersonajes > 0)
                            "${uiState.totalPersonajes} héroes, villanos y aliados"
                        else "Héroes, villanos y aliados",
                        accentColor = Color(0xFFE53935),
                        onClick = onPersonajesClick,
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )
                    FeatureCard(
                        titulo = "Quiz",
                        descripcion = "Pon a prueba tus conocimientos",
                        accentColor = Color(0xFFFF6D00),
                        onClick = onQuizClick,
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )
                }

                // Fila 2
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    FeatureCard(
                        titulo = "Mi Perfil",
                        descripcion = "Progreso, favoritos y rango",
                        accentColor = Color(0xFF1E88E5),
                        onClick = onPerfilClick,
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )
                    FeatureCard(
                        titulo = "Línea Temporal",
                        descripcion = "Eventos clave del Spider-Verse",
                        accentColor = Color(0xFF8E24AA),
                        onClick = onTimelineClick,
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )
                }

                // Fila 3
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    FeatureCard(
                        titulo = "Universos",
                        descripcion = "Realidades del multiverso",
                        accentColor = Color(0xFF039BE5),
                        onClick = onUniversosClick,
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )
                    FeatureCard(
                        titulo = "Trajes",
                        descripcion = "Los trajes de Spider-Man",
                        accentColor = Color(0xFFD81B60),
                        onClick = onTrajesClick,
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )
                }

                // Fila 4
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    FeatureCard(
                        titulo = "Relaciones",
                        descripcion = "Vínculos entre personajes",
                        accentColor = Color(0xFFE91E63),
                        onClick = onRelacionesClick,
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )
                    FeatureCard(
                        titulo = "Descubrir",
                        descripcion = "Personaje o universo aleatorio",
                        accentColor = Color(0xFF5E35B1),
                        onClick = onDescubrirClick,
                        modifier = Modifier.weight(1f).fillMaxHeight()
                    )
                }
            }

            Spacer(Modifier.height(28.dp))

            // ─── LOGOUT ──────────────────────────────────────────────────────────
            TextButton(
                onClick = { mostrarDialogoLogout = true },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = null,
                    tint = c.textSecondary,
                    modifier = Modifier.size(15.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text("Cerrar sesión", color = c.textSecondary, fontSize = 13.sp)
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
private fun MenuStatPill(
    numero: String,
    etiqueta: String,
    color: Color,
    c: AppColors
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color.copy(alpha = if (c.isSimbionte) 0.16f else 0.10f))
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = numero,
            color = color,
            fontSize = 13.sp,
            fontWeight = FontWeight.Black
        )
        Text(
            text = etiqueta,
            color = c.textSecondary,
            fontSize = 11.sp
        )
    }
}

@Composable
private fun FeatureCard(
    titulo: String,
    descripcion: String,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val c = LocalAppColors.current
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.94f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "cardScale"
    )

    Card(
        modifier = modifier
            .scale(scale)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(14.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = c.surfaceCard)
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            // Barra de acento lateral
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(accentColor)
            )
            // Texto
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp, top = 14.dp, bottom = 14.dp, end = 6.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = titulo,
                    color = c.textPrimary,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(3.dp))
                Text(
                    text = descripcion,
                    color = c.textSecondary,
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            // Flecha
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = accentColor.copy(alpha = 0.55f),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 12.dp)
                    .size(15.dp)
            )
        }
    }
}
