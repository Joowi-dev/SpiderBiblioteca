package com.joel.spiderbiblioteca.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joel.spiderbiblioteca.R
import com.joel.spiderbiblioteca.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onFinished: () -> Unit) {
    val context = LocalContext.current
    var visible by remember { mutableStateOf(false) }

    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "splash_alpha"
    )

    LaunchedEffect(Unit) {
        visible = true
        delay(2500)
        onFinished()
    }

    val splashBgId = remember {
        context.resources.getIdentifier("splash_background", "drawable", context.packageName)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (splashBgId != 0) {
            Image(
                painter = painterResource(id = splashBgId),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.45f))
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BackgroundDark)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .graphicsLayer { this.alpha = alpha },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_spider_logo),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = "Spider-Biblioteca",
                color = Color.White,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Universo Marvel de Spider-Man",
                color = Color.White.copy(alpha = 0.75f),
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Text(
            text = "© Marvel Comics",
            color = Color.White.copy(alpha = 0.4f),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        )
    }
}
