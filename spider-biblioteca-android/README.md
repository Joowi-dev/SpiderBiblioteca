# Spider-Biblioteca — Android

App Android para explorar el universo Spider-Man. Está hecha con Jetpack Compose y se conecta al backend de Spring Boot mediante Retrofit.

**Tecnologías:** Kotlin · Jetpack Compose · Material Design 3 · Retrofit · Coil · Navigation Compose · Coroutines

---

## Cómo abrir el proyecto

### Opción A — Abrir directamente (lo más fácil)

1. Abre **Android Studio**
2. Pulsa **Open**
3. Navega a `SpiderBiblioteca/spider-biblioteca-android`
4. Android Studio detecta el proyecto solo
5. Si aparece "Gradle files have changed", pulsa **Sync Now**
6. Espera a que descargue dependencias (puede tardar varios minutos la primera vez)

### Opción B — Crear proyecto nuevo y copiar los archivos

Si la opción A da problemas con el Gradle Wrapper:

1. Abre Android Studio → **New Project** → **Empty Activity**
2. Configura:
   - Name: `SpiderBiblioteca`
   - Package name: `com.joel.spiderbiblioteca`
   - Language: **Kotlin**
   - Minimum SDK: **API 26**
3. Pulsa **Finish** y espera a que Gradle sincronice
4. Reemplaza `app/build.gradle.kts` y `gradle/libs.versions.toml` con los de este proyecto
5. Pulsa **Sync Now** en la barra amarilla
6. Copia la carpeta `app/src/main/java/com/joel/spiderbiblioteca/` dentro del nuevo proyecto
7. Reemplaza `AndroidManifest.xml` y `res/values/strings.xml`

---

## Configurar la URL del backend

Abre `data/api/RetrofitInstance.kt`:

```kotlin
// Emulador Android (por defecto):
private const val BASE_URL = "http://10.0.2.2:8080/"

// Móvil físico por WiFi:
private const val BASE_URL = "http://192.168.1.X:8080/"
// Sustituye la X por tu IP (mírala con ipconfig en Windows)
```

`10.0.2.2` es la IP especial que el emulador usa para referirse al PC. Si pones `localhost` desde el emulador no va a funcionar.

---

## Pantallas de la app

| Pantalla | Qué hace |
|----------|----------|
| `LoginScreen` | Registro e inicio de sesión |
| `MenuPrincipalScreen` | Menú central con acceso a todas las secciones |
| `ListaPersonajesScreen` | Lista de personajes con búsqueda y filtros |
| `DetallePersonajeScreen` | Detalle de un personaje con botón de favorito |
| `FormularioPersonajeScreen` | Añadir o editar un personaje |
| `PerfilScreen` | Perfil del usuario, favoritos y toggle de tema |
| `TrajesScreen` | Catálogo de trajes de Spider-Man |
| `TrajeDetalleScreen` | Detalle de un traje (habilidades, popularidad...) |
| `UniversosScreen` | Lista de universos del Spider-Verse |
| `UniversoDetalleScreen` | Detalle de un universo |
| `TimelineScreen` | Línea temporal de eventos de Spider-Man |
| `RelacionesScreen` | Mapa de relaciones entre personajes |
| `QuizSetupScreen` | Configuración del quiz (número de preguntas) |
| `QuizScreen` | Preguntas con resultado al final |
| `RandomScreen` | Personaje, traje o universo aleatorio |

---

## Estructura del código

```
app/src/main/java/com/joel/spiderbiblioteca/
├── MainActivity.kt
├── navigation/
│   └── AppNavigation.kt                ← Todas las rutas
├── data/
│   ├── api/
│   │   ├── RetrofitInstance.kt         ← Configuración de Retrofit
│   │   ├── PersonajeApiService.kt      ← Endpoints de personajes
│   │   ├── AuthApiService.kt           ← Endpoints de login/registro
│   │   ├── FavoritoApiService.kt       ← Endpoints de favoritos
│   │   └── AuthInterceptor.kt          ← Añade el token JWT a las peticiones
│   ├── model/
│   │   ├── Personaje.kt
│   │   ├── AuthModels.kt               ← Clases para login/registro
│   │   ├── SpiderSuit.kt               ← Modelo de traje
│   │   ├── SpiderUniverse.kt           ← Modelo de universo
│   │   ├── TimelineEvent.kt            ← Modelo de evento
│   │   ├── CharacterRelationship.kt    ← Modelo de relación
│   │   └── PreguntaQuiz.kt             ← Modelo de pregunta
│   ├── repository/
│   │   ├── PersonajeRepository.kt
│   │   ├── AuthRepository.kt
│   │   └── FavoritosRepository.kt
│   ├── local/
│   │   ├── SessionManager.kt           ← Guarda el token JWT
│   │   ├── ThemeManager.kt             ← Toggle tema simbionte
│   │   ├── PersonajeCacheManager.kt    ← Caché local de personajes
│   │   └── ProfilePreferences.kt       ← Preferencias del perfil
│   └── seed/
│       ├── PersonajesSeed.kt           ← Datos de ejemplo de personajes
│       ├── TrajesSeed.kt               ← Datos de trajes
│       ├── UniversosSeed.kt            ← Datos de universos
│       ├── TimelineSeed.kt             ← Eventos de la timeline
│       ├── RelacionesSeed.kt           ← Relaciones entre personajes
│       └── QuizSeed.kt                 ← Preguntas del quiz
├── viewmodel/
│   ├── PersonajeViewModel.kt
│   ├── AuthViewModel.kt
│   ├── QuizViewModel.kt
│   ├── TimelineViewModel.kt
│   ├── UniversosViewModel.kt
│   ├── TrajesViewModel.kt
│   └── RelacionesViewModel.kt
├── ui/
│   ├── screens/                        ← Una clase por pantalla
│   ├── components/
│   │   ├── PersonajeCard.kt
│   │   └── VisorImagen.kt
│   ├── util/
│   │   └── ImagenUtil.kt
│   └── theme/
│       ├── Color.kt
│       ├── AppColors.kt                ← Colores del tema simbionte
│       ├── Theme.kt
│       └── Type.kt
```

---

## Dependencias

| Librería | Versión | Para qué |
|----------|---------|----------|
| Jetpack Compose BOM | 2024.12.01 | UI declarativa |
| Navigation Compose | 2.8.5 | Navegación entre pantallas |
| Retrofit | 2.11.0 | Llamadas HTTP al backend |
| Gson Converter | 2.11.0 | JSON → objetos Kotlin |
| OkHttp Logging | 4.12.0 | Ver peticiones en Logcat |
| Coil Compose | 2.7.0 | Cargar imágenes desde URL |
| Coroutines | 1.9.0 | Operaciones asíncronas |
| ViewModel Compose | 2.8.7 | Gestión de estado |

---

## Errores típicos

### CLEARTEXT communication not permitted
Android 9+ no permite HTTP por defecto. Ya está solucionado con `android:usesCleartextTraffic="true"` en el `AndroidManifest.xml`.

### Failed to connect to localhost
Estás usando `localhost` desde el emulador. Cámbialo por `10.0.2.2` en `RetrofitInstance.kt`.

### Gradle no sincroniza / Unresolved reference
1. File → Invalidate Caches and Restart
2. Build → Clean Project
3. Build → Rebuild Project

### La app no muestra datos
Mira el Logcat con el filtro `OkHttp`. Si ves `connection refused`, el backend no está arrancado o la URL es incorrecta.

### La app arranca en el menú sin haber hecho login
`SessionManager` guarda el token JWT en SharedPreferences. Si hay un token guardado de una sesión anterior, salta directamente al menú. Para forzar el login, desinstala la app del emulador o borra los datos de la app.

### Cannot find symbol / import errors
Asegúrate de que el package name del proyecto es exactamente `com.joel.spiderbiblioteca` (todo en minúsculas, sin espacios).

### App se cierra al abrir
Mira el Logcat. Los errores más comunes son falta del permiso INTERNET en el Manifest o URL incorrecta en `RetrofitInstance`.
