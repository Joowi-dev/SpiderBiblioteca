package com.joel.spiderbiblioteca.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.joel.spiderbiblioteca.data.model.Personaje
import com.joel.spiderbiblioteca.ui.theme.*
import com.joel.spiderbiblioteca.ui.util.resolverImagen

@Composable
fun PersonajeCard(
    personaje: Personaje,
    onClick: () -> Unit,
    esFavorito: Boolean = false,
    onToggleFavorito: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val c = LocalAppColors.current
    val (colorTipo, nombreTipo) = colorYNombrePorTipo(personaje.tipo)
    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = c.surfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = resolverImagen(context, personaje.nombre, personaje.imagenUrl),
                contentDescription = "Imagen de ${personaje.nombre}",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(c.backgroundCard),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = personaje.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    color = c.textPrimary,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = personaje.nombreReal,
                    style = MaterialTheme.typography.bodyMedium,
                    color = c.textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
                if (personaje.poderes.isNotBlank()) {
                    Text(
                        text = personaje.poderes,
                        style = MaterialTheme.typography.bodyMedium,
                        color = c.textSecondary,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 12.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                TipoBadge(tipo = personaje.tipo, color = colorTipo, nombre = nombreTipo)
            }

            if (onToggleFavorito != null) {
                IconButton(onClick = onToggleFavorito) {
                    Icon(
                        imageVector = if (esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = if (esFavorito) "Quitar favorito" else "Añadir favorito",
                        tint = if (esFavorito) SpiderRed else c.grisMedio
                    )
                }
            }
        }
    }
}

@Composable
fun PersonajeCardGrid(
    personaje: Personaje,
    onClick: () -> Unit,
    esFavorito: Boolean = false,
    onToggleFavorito: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val c = LocalAppColors.current
    val (colorTipo, nombreTipo) = colorYNombrePorTipo(personaje.tipo)
    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = c.surfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column {
            Box {
                AsyncImage(
                    model = resolverImagen(context, personaje.nombre, personaje.imagenUrl),
                    contentDescription = "Imagen de ${personaje.nombre}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .background(c.backgroundCard),
                    contentScale = ContentScale.Crop
                )
                if (onToggleFavorito != null) {
                    IconButton(
                        onClick = onToggleFavorito,
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(
                            imageVector = if (esFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = if (esFavorito) SpiderRed else Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = personaje.nombre,
                    style = MaterialTheme.typography.titleSmall,
                    color = c.textPrimary,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = personaje.nombreReal,
                    style = MaterialTheme.typography.bodySmall,
                    color = c.textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(6.dp))
                TipoBadge(tipo = personaje.tipo, color = colorTipo, nombre = nombreTipo)
            }
        }
    }
}

@Composable
fun TipoBadge(tipo: String, color: Color, nombre: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color.copy(alpha = 0.25f))
            .padding(horizontal = 10.dp, vertical = 3.dp)
    ) {
        Text(
            text = nombre.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.Bold
        )
    }
}

fun colorYNombrePorTipo(tipo: String): Pair<Color, String> {
    return when (tipo.lowercase()) {
        "heroe" -> Pair(ColorHeroe, "Héroe")
        "villano" -> Pair(ColorVillano, "Villano")
        "aliado" -> Pair(ColorAliado, "Aliado")
        "antiheroe" -> Pair(ColorAntiheroe, "Antihéroe")
        else -> Pair(GrisMedio, tipo)
    }
}
