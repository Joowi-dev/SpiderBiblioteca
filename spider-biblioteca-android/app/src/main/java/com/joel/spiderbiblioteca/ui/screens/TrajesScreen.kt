package com.joel.spiderbiblioteca.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.joel.spiderbiblioteca.data.model.SpiderSuit
import com.joel.spiderbiblioteca.ui.theme.*
import com.joel.spiderbiblioteca.ui.util.resolverImagenConPrefijo
import com.joel.spiderbiblioteca.viewmodel.TrajesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrajesScreen(
    viewModel: TrajesViewModel,
    onTrajeClick: (Int) -> Unit,
    onVolver: () -> Unit,
    onMenuPrincipal: () -> Unit
) {
    val c = LocalAppColors.current
    val trajes by viewModel.trajes.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(c.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Galería de Trajes",
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

        Text(
            text = "${trajes.size} trajes en la galería",
            color = c.textSecondary,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(trajes) { traje ->
                TrajeCard(traje = traje, onClick = { onTrajeClick(traje.id) })
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun TrajeCard(
    traje: SpiderSuit,
    onClick: () -> Unit
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

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = c.surfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagen o placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(popularidadColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                if (imagenModel != null) {
                    AsyncImage(
                        model = imagenModel,
                        contentDescription = traje.nombre,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(text = "🕷", fontSize = 48.sp)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = traje.nombre,
                color = c.textPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                maxLines = 2,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = traje.usuario,
                color = c.textSecondary,
                fontSize = 10.sp,
                textAlign = TextAlign.Center,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Popularidad
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null,
                    tint = popularidadColor,
                    modifier = Modifier.size(14.dp)
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = "${traje.popularidad}/10",
                    color = popularidadColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
