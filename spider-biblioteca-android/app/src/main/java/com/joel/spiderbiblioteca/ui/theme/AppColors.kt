package com.joel.spiderbiblioteca.ui.theme

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color

data class AppColors(
    val background: Color,
    val backgroundCard: Color,
    val surfaceCard: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val grisMedio: Color,
    val grisOscuro: Color,
    val isSimbionte: Boolean
)

val LocalAppColors = compositionLocalOf { spiderDarkColors() }

fun spiderDarkColors() = AppColors(
    background = Color(0xFF0A0A1A),
    backgroundCard = Color(0xFF12122A),
    surfaceCard = Color(0xFF1E1E3A),
    textPrimary = Color(0xFFFFFFFF),
    textSecondary = Color(0xFFB0B8CC),
    grisMedio = Color(0xFF546E7A),
    grisOscuro = Color(0xFF263238),
    isSimbionte = true
)

fun spiderLightColors() = AppColors(
    background = Color(0xFFF0F2F5),
    backgroundCard = Color(0xFFDDE1E7),
    surfaceCard = Color(0xFFFFFFFF),
    textPrimary = Color(0xFF1A1A2E),
    textSecondary = Color(0xFF555B6B),
    grisMedio = Color(0xFF9E9E9E),
    grisOscuro = Color(0xFF757575),
    isSimbionte = false
)
