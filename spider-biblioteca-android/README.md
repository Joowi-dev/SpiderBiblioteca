# Spider-Biblioteca - Frontend Android

Aplicación Android para la biblioteca de personajes Spider-Man.
Tecnologías: Kotlin · Jetpack Compose · Material Design 3 · Retrofit · Coil · Navigation Compose

---

## IMPORTANTE: Cómo abrir el proyecto en Android Studio

Como el proyecto Android no se puede generar completamente desde la terminal de Windows
(requiere Gradle Wrapper y SDK de Android), debes hacer lo siguiente:

### Opción A - Crear proyecto nuevo y copiar los archivos (recomendado para principiantes)

1. Abre **Android Studio**
2. Pulsa **New Project**
3. Selecciona **Empty Activity**
4. Configura:
   - Name: `SpiderBiblioteca`
   - Package name: `com.joel.spiderbiblioteca`
   - Save location: elige una carpeta temporal (luego copiaremos)
   - Language: **Kotlin**
   - Minimum SDK: **API 26 (Android 8.0)**
5. Pulsa **Finish** y espera a que Gradle sincronice

### Paso 2 - Reemplazar los archivos de configuración

Una vez creado el proyecto en Android Studio, reemplaza o fusiona:

**Reemplaza `app/build.gradle.kts`** con el contenido de este proyecto.
**Reemplaza `gradle/libs.versions.toml`** con el contenido de este proyecto.

> Tras reemplazar, pulsa **Sync Now** en la barra amarilla.

### Paso 3 - Copiar los archivos de código

Copia toda la carpeta:
```
spider-biblioteca-android/app/src/main/java/com/joel/spiderbiblioteca/
```

Dentro de la carpeta equivalente de tu nuevo proyecto Android Studio.

### Paso 4 - Reemplazar AndroidManifest.xml

Reemplaza el `AndroidManifest.xml` generado por Android Studio con el de este proyecto.

### Paso 5 - Reemplazar res/values

Reemplaza `res/values/strings.xml` y añade `res/values/themes.xml`.

---

## Opción B - Abrir directamente como proyecto existente

1. Abre **Android Studio**
2. Pulsa **Open**
3. Navega a `SpiderBiblioteca/spider-biblioteca-android`
4. Android Studio detectará el proyecto
5. Si pide crear el Gradle Wrapper, acepta
6. Espera a que descargue dependencias (puede tardar varios minutos la primera vez)

> Si aparece "Gradle files have changed", pulsa "Sync Now"

---

## Estructura del código

```
app/src/main/java/com/joel/spiderbiblioteca/
├── MainActivity.kt                     ← Punto de entrada de la app
├── data/
│   ├── api/
│   │   ├── PersonajeApiService.kt      ← Interfaz Retrofit (endpoints)
│   │   └── RetrofitInstance.kt         ← Configuración de Retrofit
│   ├── model/
│   │   └── Personaje.kt                ← Modelo de datos
│   └── repository/
│       └── PersonajeRepository.kt      ← Capa de acceso a datos
├── viewmodel/
│   └── PersonajeViewModel.kt           ← Estado y lógica de negocio
├── ui/
│   ├── screens/
│   │   ├── ListaPersonajesScreen.kt    ← Pantalla principal con lista
│   │   ├── DetallePersonajeScreen.kt   ← Detalle de un personaje
│   │   └── FormularioPersonajeScreen.kt ← Añadir y editar
│   ├── components/
│   │   └── PersonajeCard.kt            ← Tarjeta de personaje
│   └── theme/
│       ├── Color.kt                    ← Paleta de colores Spider-Man
│       ├── Theme.kt                    ← Tema Material 3
│       └── Type.kt                     ← Tipografía
└── navigation/
    └── AppNavigation.kt                ← Rutas de navegación
```

---

## Configurar la URL del backend

Abre `RetrofitInstance.kt` y ajusta la URL según tu caso:

### Emulador Android (por defecto)
```kotlin
private const val BASE_URL = "http://10.0.2.2:8080/"
```
> `10.0.2.2` es la IP especial que el emulador usa para referirse al ordenador host.

### Móvil físico conectado por WiFi
```kotlin
private const val BASE_URL = "http://192.168.1.50:8080/"
```
> Sustituye `192.168.1.50` por la IP local de tu PC.
> Puedes ver tu IP en Windows con: `ipconfig` → busca "Dirección IPv4"

---

## Dependencias utilizadas

| Librería | Versión | Para qué sirve |
|----------|---------|----------------|
| Jetpack Compose BOM | 2024.12.01 | UI declarativa |
| Navigation Compose | 2.8.5 | Navegación entre pantallas |
| Retrofit | 2.11.0 | Llamadas HTTP a la API |
| Gson Converter | 2.11.0 | Convertir JSON a objetos Kotlin |
| OkHttp Logging | 4.12.0 | Ver peticiones en Logcat |
| Coil Compose | 2.7.0 | Cargar imágenes desde URL |
| Coroutines | 1.9.0 | Operaciones asíncronas |
| ViewModel Compose | 2.8.7 | Gestión de estado |

---

## Solución de errores típicos

### CLEARTEXT communication not permitted

**Problema:** La app no permite HTTP (solo HTTPS) por defecto en Android 9+.

**Solución:** Ya está aplicada en `AndroidManifest.xml`:
```xml
android:usesCleartextTraffic="true"
```

### Failed to connect to localhost/127.0.0.1:8080

**Problema:** Estás usando `localhost` desde el emulador.

**Solución:** Usa `10.0.2.2` en lugar de `localhost`. Ya está configurado en `RetrofitInstance.kt`.

### Gradle no sincroniza / Unresolved reference

**Solución:**
1. File → Invalidate Caches and Restart
2. Build → Clean Project
3. Build → Rebuild Project

### App no muestra datos aunque Spring Boot esté corriendo

Comprueba en Logcat (filtro: `OkHttp`) que las peticiones llegan al backend.
Si ves `connection refused`, el backend no está arrancado o la URL es incorrecta.

### Cannot find symbol / import errors

Asegúrate de que `package name` en Android Studio es `com.joel.spiderbiblioteca`
exactamente (sin mayúsculas, sin espacios).

### App se cierra al abrir (crash)

Revisa Logcat en Android Studio. Los errores más comunes son:
- Falta el permiso INTERNET en AndroidManifest
- URL incorrecta en RetrofitInstance

---

## Cómo añadir imágenes locales (opcional)

Si quieres imágenes locales en lugar de URLs:
1. Guarda los archivos PNG en `app/src/main/res/drawable/`
2. En el campo imagenUrl del formulario, deja vacío
3. Modifica `PersonajeCard.kt` para mostrar imagen por defecto si la URL está vacía:

```kotlin
AsyncImage(
    model = if (personaje.imagenUrl.isNotBlank()) personaje.imagenUrl
            else R.drawable.placeholder,
    ...
)
```
