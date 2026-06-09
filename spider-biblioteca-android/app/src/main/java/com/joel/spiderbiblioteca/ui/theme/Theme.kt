package com.joel.spiderbiblioteca.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val SpiderDarkColorScheme = darkColorScheme(
    primary = SpiderRed,
    onPrimary = BlancoPuro,
    primaryContainer = SpiderRedDark,
    onPrimaryContainer = BlancoPuro,
    secondary = SpiderBlue,
    onSecondary = BlancoPuro,
    secondaryContainer = SpiderBlueDark,
    onSecondaryContainer = BlancoPuro,
    background = BackgroundDark,
    onBackground = TextPrimary,
    surface = SurfaceCard,
    onSurface = TextPrimary,
    surfaceVariant = BackgroundCard,
    onSurfaceVariant = TextSecondary,
    error = SpiderRedLight,
    onError = BlancoPuro
)

private val SpiderLightColorScheme = lightColorScheme(
    primary = SpiderRed,
    onPrimary = BlancoPuro,
    primaryContainer = SpiderRedDark,
    onPrimaryContainer = BlancoPuro,
    secondary = SpiderBlue,
    onSecondary = BlancoPuro,
    secondaryContainer = SpiderBlueDark,
    onSecondaryContainer = BlancoPuro,
    background = Color(0xFFF0F2F5),
    onBackground = Color(0xFF1A1A2E),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF1A1A2E),
    surfaceVariant = Color(0xFFDDE1E7),
    onSurfaceVariant = Color(0xFF555B6B),
    error = SpiderRedLight,
    onError = BlancoPuro
)

@Composable
fun SpiderBibliotecaTheme(
    isSimbionte: Boolean = true,
    content: @Composable () -> Unit
) {
    val appColors = if (isSimbionte) spiderDarkColors() else spiderLightColors()
    val colorScheme = if (isSimbionte) SpiderDarkColorScheme else SpiderLightColorScheme

    CompositionLocalProvider(LocalAppColors provides appColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}
