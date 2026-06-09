package com.joel.spiderbiblioteca.navigation

import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.joel.spiderbiblioteca.data.local.SessionManager
import com.joel.spiderbiblioteca.ui.screens.*
import com.joel.spiderbiblioteca.viewmodel.*

object Rutas {
    const val LOGIN = "login"
    const val MENU = "menu"
    const val LISTA = "lista"
    const val PERFIL = "perfil"
    const val QUIZ = "quiz"
    const val QUIZ_SETUP = "quiz_setup"
    const val DETALLE = "detalle/{id}"
    const val NUEVO = "formulario/nuevo"
    const val EDITAR = "formulario/editar/{id}"
    const val TIMELINE = "timeline"
    const val UNIVERSOS = "universos"
    const val UNIVERSO_DETALLE = "universo/{id}"
    const val TRAJES = "trajes"
    const val TRAJE_DETALLE = "traje/{id}"
    const val RELACIONES = "relaciones"
    const val RANDOM = "random"

    fun detalle(id: Long) = "detalle/$id"
    fun editar(id: Long) = "formulario/editar/$id"
    fun universoDetalle(id: Int) = "universo/$id"
    fun trajeDetalle(id: Int) = "traje/$id"
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: PersonajeViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()
    val quizViewModel: QuizViewModel = viewModel()
    val timelineViewModel: TimelineViewModel = viewModel()
    val universosViewModel: UniversosViewModel = viewModel()
    val trajesViewModel: TrajesViewModel = viewModel()
    val relacionesViewModel: RelacionesViewModel = viewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsState()

    val startDestination = if (SessionManager.isLoggedIn()) Rutas.MENU else Rutas.LOGIN

    val irAlMenu: () -> Unit = {
        navController.navigate(Rutas.MENU) {
            popUpTo(Rutas.MENU) { inclusive = true }
            launchSingleTop = true
        }
    }

    LaunchedEffect(uiState.snackbarMensaje) {
        uiState.snackbarMensaje?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.snackbarMostrado()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = { _ ->
            NavHost(navController = navController, startDestination = startDestination) {

                composable(Rutas.LOGIN) {
                    LoginScreen(
                        viewModel = authViewModel,
                        onLoginSuccess = {
                            viewModel.cargarPersonajes()
                            navController.navigate(Rutas.MENU) {
                                popUpTo(Rutas.LOGIN) { inclusive = true }
                            }
                        }
                    )
                }

                composable(Rutas.MENU) {
                    MenuPrincipalScreen(
                        viewModel = viewModel,
                        onPersonajesClick = { navController.navigate(Rutas.LISTA) },
                        onQuizClick = { navController.navigate(Rutas.QUIZ_SETUP) },
                        onPerfilClick = { navController.navigate(Rutas.PERFIL) },
                        onTimelineClick = { navController.navigate(Rutas.TIMELINE) },
                        onUniversosClick = { navController.navigate(Rutas.UNIVERSOS) },
                        onTrajesClick = { navController.navigate(Rutas.TRAJES) },
                        onRelacionesClick = { navController.navigate(Rutas.RELACIONES) },
                        onDescubrirClick = { navController.navigate(Rutas.RANDOM) },
                        onLogout = {
                            authViewModel.logout()
                            navController.navigate(Rutas.LOGIN) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }

                composable(Rutas.LISTA) {
                    ListaPersonajesScreen(
                        viewModel = viewModel,
                        onPersonajeClick = { id -> navController.navigate(Rutas.detalle(id)) },
                        onAnadirClick = { navController.navigate(Rutas.NUEVO) },
                        onPerfilClick = { navController.navigate(Rutas.PERFIL) },
                        onMenuPrincipal = irAlMenu
                    )
                }

                composable(Rutas.PERFIL) {
                    PerfilScreen(
                        personajeViewModel = viewModel,
                        onVolver = { navController.popBackStack() },
                        onMenuPrincipal = irAlMenu,
                        onLogout = {
                            authViewModel.logout()
                            navController.navigate(Rutas.LOGIN) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                    )
                }

                composable(Rutas.QUIZ_SETUP) {
                    QuizSetupScreen(
                        viewModel = quizViewModel,
                        onIniciarQuiz = { navController.navigate(Rutas.QUIZ) },
                        onVolver = { navController.popBackStack() },
                        onMenuPrincipal = irAlMenu
                    )
                }

                composable(Rutas.QUIZ) {
                    QuizScreen(
                        viewModel = quizViewModel,
                        onVolver = { navController.popBackStack() },
                        onMenuPrincipal = irAlMenu
                    )
                }

                composable(
                    route = Rutas.DETALLE,
                    arguments = listOf(navArgument("id") { type = NavType.LongType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getLong("id") ?: return@composable
                    DetallePersonajeScreen(
                        personajeId = id,
                        viewModel = viewModel,
                        onVolver = { navController.popBackStack() },
                        onEditar = { personajeId -> navController.navigate(Rutas.editar(personajeId)) },
                        onMenuPrincipal = irAlMenu
                    )
                }

                composable(Rutas.NUEVO) {
                    FormularioPersonajeScreen(
                        personajeId = null,
                        viewModel = viewModel,
                        onVolver = { navController.popBackStack() }
                    )
                }

                composable(
                    route = Rutas.EDITAR,
                    arguments = listOf(navArgument("id") { type = NavType.LongType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getLong("id") ?: return@composable
                    FormularioPersonajeScreen(
                        personajeId = id,
                        viewModel = viewModel,
                        onVolver = { navController.popBackStack() }
                    )
                }

                composable(Rutas.TIMELINE) {
                    TimelineScreen(
                        viewModel = timelineViewModel,
                        onVolver = { navController.popBackStack() },
                        onMenuPrincipal = irAlMenu
                    )
                }

                composable(Rutas.UNIVERSOS) {
                    UniversosScreen(
                        viewModel = universosViewModel,
                        onUniversoClick = { id -> navController.navigate(Rutas.universoDetalle(id)) },
                        onVolver = { navController.popBackStack() },
                        onMenuPrincipal = irAlMenu
                    )
                }

                composable(
                    route = Rutas.UNIVERSO_DETALLE,
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("id") ?: return@composable
                    UniversoDetalleScreen(
                        universoId = id,
                        viewModel = universosViewModel,
                        onVolver = { navController.popBackStack() },
                        onMenuPrincipal = irAlMenu
                    )
                }

                composable(Rutas.TRAJES) {
                    TrajesScreen(
                        viewModel = trajesViewModel,
                        onTrajeClick = { id -> navController.navigate(Rutas.trajeDetalle(id)) },
                        onVolver = { navController.popBackStack() },
                        onMenuPrincipal = irAlMenu
                    )
                }

                composable(
                    route = Rutas.TRAJE_DETALLE,
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getInt("id") ?: return@composable
                    TrajeDetalleScreen(
                        trajeId = id,
                        viewModel = trajesViewModel,
                        onVolver = { navController.popBackStack() },
                        onMenuPrincipal = irAlMenu
                    )
                }

                composable(Rutas.RELACIONES) {
                    RelacionesScreen(
                        viewModel = relacionesViewModel,
                        onVolver = { navController.popBackStack() },
                        onMenuPrincipal = irAlMenu
                    )
                }

                composable(Rutas.RANDOM) {
                    RandomScreen(
                        personajeViewModel = viewModel,
                        trajesViewModel = trajesViewModel,
                        universosViewModel = universosViewModel,
                        onPersonajeClick = { id -> navController.navigate(Rutas.detalle(id)) },
                        onTrajeClick = { id -> navController.navigate(Rutas.trajeDetalle(id)) },
                        onUniversoClick = { id -> navController.navigate(Rutas.universoDetalle(id)) },
                        onVolver = { navController.popBackStack() },
                        onMenuPrincipal = irAlMenu
                    )
                }
            }
        }
    )
}
