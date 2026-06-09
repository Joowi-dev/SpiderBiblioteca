package com.joel.spiderbiblioteca

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.joel.spiderbiblioteca.data.local.SessionManager
import com.joel.spiderbiblioteca.data.local.ThemeManager
import com.joel.spiderbiblioteca.navigation.AppNavigation
import com.joel.spiderbiblioteca.ui.theme.SpiderBibliotecaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        SessionManager.init(this)
        ThemeManager.init(this)
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val isSimbionte by ThemeManager.isSimbionte.collectAsState()
            SpiderBibliotecaTheme(isSimbionte = isSimbionte) {
                AppNavigation()
            }
        }
    }
}
