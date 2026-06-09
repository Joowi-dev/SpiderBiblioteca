package com.joel.spiderbiblioteca.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.joel.spiderbiblioteca.data.model.SpiderUniverse
import com.joel.spiderbiblioteca.ui.theme.*
import com.joel.spiderbiblioteca.ui.util.resolverImagenConPrefijo
import com.joel.spiderbiblioteca.viewmodel.UniversosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UniversosScreen(
    viewModel: UniversosViewModel,
    onUniversoClick: (Int) -> Unit,
    onVolver: () -> Unit,
    onMenuPrincipal: () -> Unit
) {
    val c = LocalAppColors.current
    val universos by viewModel.universos.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(c.background)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Universos Alternativos",
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

        Text(
            text = "${universos.size} universos del Spider-Verse",
            color = c.textSecondary,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(universos) { universo ->
                UniversoCard(universo = universo, onClick = { onUniversoClick(universo.id) })
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun UniversoCard(
    universo: SpiderUniverse,
    onClick: () -> Unit
) {
    val c = LocalAppColors.current
    val estiloColor = estiloAColor(universo.estilo)
    val context = LocalContext.current
    val imagenModel = resolverImagenConPrefijo(context, "univ_", universo.nombre, universo.imagenUrl)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = c.surfaceCard),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen o badge del código del universo
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(estiloColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                if (imagenModel != null) {
                    AsyncImage(
                        model = imagenModel,
                        contentDescription = universo.nombre,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        text = universo.codigo.take(6),
                        color = estiloColor,
                        fontWeight = FontWeight.Black,
                        fontSize = 9.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = universo.nombre,
                    color = c.textPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Text(
                    text = universo.spiderManPrincipal,
                    color = SpiderRedLight,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = universo.descripcion,
                    color = c.textSecondary,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = estiloColor.copy(alpha = 0.15f)
                ) {
                    Text(
                        text = universo.estilo,
                        color = estiloColor,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }
        }
    }
}

private fun estiloAColor(estilo: String): Color = when (estilo.lowercase()) {
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
