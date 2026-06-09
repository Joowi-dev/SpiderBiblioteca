package com.joel.spiderbiblioteca.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joel.spiderbiblioteca.R
import com.joel.spiderbiblioteca.ui.components.PersonajeCard
import com.joel.spiderbiblioteca.ui.components.PersonajeCardGrid
import com.joel.spiderbiblioteca.ui.theme.*
import com.joel.spiderbiblioteca.viewmodel.OrdenLista
import com.joel.spiderbiblioteca.viewmodel.PersonajeViewModel

private val TIPOS_FILTRO = listOf("Todos", "heroe", "villano", "aliado", "antiheroe")
private val NOMBRES_FILTRO = listOf("Todos", "Héroes", "Villanos", "Aliados", "Antihéroes")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaPersonajesScreen(
    viewModel: PersonajeViewModel,
    onPersonajeClick: (Long) -> Unit,
    onAnadirClick: () -> Unit,
    onPerfilClick: () -> Unit = {},
    onMenuPrincipal: () -> Unit = {}
) {
    val c = LocalAppColors.current
    val uiState by viewModel.uiState.collectAsState()
    var busqueda by remember { mutableStateOf("") }
    var filtroSeleccionado by remember { mutableIntStateOf(0) }
    var vistaEnCuadricula by remember { mutableStateOf(false) }
    var mostrarMenuOrden by remember { mutableStateOf(false) }

    val hayFiltroCombinado = busqueda.isNotBlank() && filtroSeleccionado != 0

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(c.background)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {

            // Cabecera
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SpiderRedDark)
                    .statusBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_spider_logo),
                        contentDescription = null,
                        modifier = Modifier.size(38.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Spider-Biblioteca",
                            style = MaterialTheme.typography.headlineMedium,
                            color = BlancoPuro,
                            fontWeight = FontWeight.Black
                        )
                        Text(
                            text = "Universo Marvel de Spider-Man",
                            style = MaterialTheme.typography.bodyMedium,
                            color = BlancoPuro.copy(alpha = 0.75f)
                        )
                    }

                    // Botón menú principal
                    IconButton(onClick = onMenuPrincipal) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Menú principal",
                            tint = BlancoPuro
                        )
                    }

                    // Botón favoritos
                    IconButton(onClick = { viewModel.toggleSoloFavoritos() }) {
                        Icon(
                            imageVector = if (uiState.soloFavoritos) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favoritos",
                            tint = if (uiState.soloFavoritos) Color(0xFFFF6B6B) else BlancoPuro
                        )
                    }

                    // Botón perfil
                    IconButton(onClick = onPerfilClick) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Mi perfil",
                            tint = BlancoPuro
                        )
                    }

                    // Botón ordenación
                    Box {
                        IconButton(onClick = { mostrarMenuOrden = true }) {
                            Icon(
                                imageVector = Icons.Default.Sort,
                                contentDescription = "Ordenar",
                                tint = BlancoPuro
                            )
                        }
                        DropdownMenu(
                            expanded = mostrarMenuOrden,
                            onDismissRequest = { mostrarMenuOrden = false },
                            containerColor = c.surfaceCard
                        ) {
                            OrdenLista.entries.forEach { orden ->
                                val label = when (orden) {
                                    OrdenLista.NOMBRE_AZ -> "Nombre A-Z"
                                    OrdenLista.NOMBRE_ZA -> "Nombre Z-A"
                                    OrdenLista.TIPO -> "Por tipo"
                                }
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            label,
                                            color = if (uiState.orden == orden) SpiderRed else c.textPrimary,
                                            fontWeight = if (uiState.orden == orden) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    onClick = {
                                        viewModel.setOrden(orden)
                                        mostrarMenuOrden = false
                                    }
                                )
                            }
                        }
                    }

                    // Toggle lista/cuadrícula
                    IconButton(onClick = { vistaEnCuadricula = !vistaEnCuadricula }) {
                        Icon(
                            imageVector = if (vistaEnCuadricula) Icons.Default.ViewList else Icons.Default.GridView,
                            contentDescription = if (vistaEnCuadricula) "Vista lista" else "Vista cuadrícula",
                            tint = BlancoPuro
                        )
                    }
                }
            }

            // Banner de caché offline
            if (uiState.desdeCahe) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF3D2A00))
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "Sin conexión · Mostrando datos guardados",
                        color = Color(0xFFFFB74D),
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Barra de búsqueda
            OutlinedTextField(
                value = busqueda,
                onValueChange = { texto ->
                    busqueda = texto
                    viewModel.buscarCombinado(texto, TIPOS_FILTRO[filtroSeleccionado])
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                placeholder = { Text("Buscar por nombre, alter ego, apodo...", color = c.textSecondary) },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = "Buscar", tint = SpiderRed)
                },
                trailingIcon = {
                    if (busqueda.isNotBlank()) {
                        IconButton(onClick = {
                            busqueda = ""
                            viewModel.buscarCombinado("", TIPOS_FILTRO[filtroSeleccionado])
                        }) {
                            Icon(Icons.Default.Close, contentDescription = "Limpiar", tint = c.textSecondary)
                        }
                    }
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SpiderRed,
                    unfocusedBorderColor = c.grisMedio,
                    focusedTextColor = c.textPrimary,
                    unfocusedTextColor = c.textPrimary,
                    cursorColor = SpiderRed,
                    focusedContainerColor = c.surfaceCard,
                    unfocusedContainerColor = c.surfaceCard
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Filtros de tipo
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(TIPOS_FILTRO.size) { index ->
                    val seleccionado = filtroSeleccionado == index
                    FilterChip(
                        selected = seleccionado,
                        onClick = {
                            filtroSeleccionado = index
                            viewModel.buscarCombinado(busqueda, TIPOS_FILTRO[index])
                        },
                        label = {
                            Text(
                                text = NOMBRES_FILTRO[index],
                                fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 13.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = SpiderRed,
                            selectedLabelColor = BlancoPuro,
                            containerColor = c.surfaceCard,
                            labelColor = c.textSecondary
                        )
                    )
                }
            }

            // Indicador de filtro combinado activo
            if (hayFiltroCombinado) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Filtros combinados activos",
                        color = SpiderRed,
                        fontSize = 12.sp,
                        modifier = Modifier.weight(1f)
                    )
                    TextButton(
                        onClick = {
                            busqueda = ""
                            filtroSeleccionado = 0
                            viewModel.buscarCombinado("", "Todos")
                        },
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp)
                    ) {
                        Text("Limpiar todo", color = c.textSecondary, fontSize = 12.sp)
                    }
                }
            }

            // Contador de resultados
            if (!uiState.isLoading && uiState.error == null) {
                val count = uiState.personajes.size
                val label = when {
                    uiState.soloFavoritos -> if (count == 1) "1 favorito" else "$count favoritos"
                    filtroSeleccionado == 0 && busqueda.isBlank() -> if (count == 1) "1 personaje" else "$count personajes"
                    else -> if (count == 1) "1 resultado" else "$count resultados"
                }
                Text(
                    text = label,
                    color = c.textSecondary,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                )
            }

            // Contenido principal
            when {
                uiState.isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            CircularProgressIndicator(color = SpiderRed, strokeWidth = 3.dp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("Cargando personajes...", color = c.textSecondary)
                        }
                    }
                }

                uiState.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize().padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            colors = CardDefaults.cardColors(containerColor = c.surfaceCard),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(24.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("⚠️", fontSize = 40.sp)
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = uiState.error!!,
                                    color = SpiderRedLight,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Button(
                                    onClick = { viewModel.cargarPersonajes() },
                                    colors = ButtonDefaults.buttonColors(containerColor = SpiderRed)
                                ) {
                                    Text("Reintentar")
                                }
                            }
                        }
                    }
                }

                uiState.personajes.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(if (uiState.soloFavoritos) "⭐" else "🕸️", fontSize = 48.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                if (uiState.soloFavoritos) "No tienes favoritos aún."
                                else "No hay resultados.",
                                color = c.textSecondary
                            )
                            if (!uiState.soloFavoritos && busqueda.isBlank() && filtroSeleccionado == 0) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    "Pulsa + para añadir el primero.",
                                    color = c.textSecondary,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }

                else -> {
                    PullToRefreshBox(
                        isRefreshing = uiState.isLoading,
                        onRefresh = { viewModel.cargarPersonajes() },
                        modifier = Modifier.fillMaxSize()
                    ) {
                        if (vistaEnCuadricula) {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(uiState.personajes) { personaje ->
                                    PersonajeCardGrid(
                                        personaje = personaje,
                                        onClick = { personaje.id?.let { onPersonajeClick(it) } },
                                        esFavorito = personaje.id != null && personaje.id in uiState.favoritos,
                                        onToggleFavorito = { personaje.id?.let { viewModel.toggleFavorito(it) } }
                                    )
                                }
                                item { Spacer(modifier = Modifier.height(80.dp)) }
                                item { Spacer(modifier = Modifier.height(80.dp)) }
                            }
                        } else {
                            LazyColumn(
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(uiState.personajes) { personaje ->
                                    PersonajeCard(
                                        personaje = personaje,
                                        onClick = { personaje.id?.let { onPersonajeClick(it) } },
                                        esFavorito = personaje.id != null && personaje.id in uiState.favoritos,
                                        onToggleFavorito = { personaje.id?.let { viewModel.toggleFavorito(it) } }
                                    )
                                }
                                item { Spacer(modifier = Modifier.height(80.dp)) }
                            }
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = onAnadirClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            containerColor = SpiderRed,
            contentColor = BlancoPuro,
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Añadir personaje",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}
