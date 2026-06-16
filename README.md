# Spider-Biblioteca

Aplicación de Android para explorar el universo Spider-Man. Tiene un backend en Spring Boot que gestiona los datos y una app Android hecha con Jetpack Compose. Es un proyecto del módulo DAM.

**Backend:** Java 17 + Spring Boot + Hibernate/JPA + MySQL + JWT  
**Android:** Kotlin + Jetpack Compose + Retrofit + Coil

---

## Qué puede hacer la app

### Lo básico
- Ver todos los personajes del universo Spider-Man en una lista
- Entrar al detalle de cada personaje (poderes, descripción, primera aparición...)
- Añadir, editar y borrar personajes (solo si tienes cuenta)
- Buscar personajes por nombre o filtrar por tipo (héroe, villano, aliado, antihéroe)

### Sistema de usuarios
- Registro e inicio de sesión con usuario y contraseña
- La sesión se guarda con un token JWT para no tener que hacer login cada vez
- Perfil de usuario donde puedes ver tus favoritos y configurar la app

### Favoritos
- Puedes marcar personajes como favoritos desde su detalle
- Los favoritos se guardan en el servidor y están vinculados a tu cuenta

### Trajes de Spider-Man
- Catálogo con todos los trajes de Spider-Man (clásico, simbionte, Iron Spider...)
- Cada traje tiene descripción, habilidades, popularidad y primera aparición

### Universos (Spider-Verse)
- Lista de universos del multiverso Spider-Man (616, Ultimate, Spider-Noir...)
- Detalle de cada universo: qué Spider-Man lo protagoniza, personajes importantes, estilo

### Línea temporal
- Cronología de los eventos más importantes de la historia de Spider-Man
- Los eventos están organizados por etapas y tipos

### Quiz
- Mini juego de preguntas sobre Spider-Man
- Puedes configurar antes de empezar cuántas preguntas quieres
- Al terminar te dice cuántas has acertado

### Mapa de relaciones
- Muestra cómo se relacionan los personajes entre sí (aliados, enemigos, familia...)

### Descubrir (aleatorio)
- Pulsa un botón y te muestra un personaje, traje o universo al azar

### Tema Simbionte
- Desde el perfil puedes cambiar entre el tema rojo (Spider-Man clásico) y el tema oscuro simbionte
- La preferencia se guarda y se mantiene al cerrar la app

---

## Estructura del proyecto

```
SpiderBiblioteca/
├── README.md
├── spider-biblioteca-backend/     ← Spring Boot (IntelliJ IDEA)
│   ├── pom.xml
│   ├── database.sql
│   └── src/
│       └── main/java/com/joel/spiderbiblioteca/
│           ├── controller/        ← Endpoints REST
│           ├── service/           ← Lógica
│           ├── repository/        ← Acceso a BD
│           ├── model/             ← Entidades JPA
│           ├── dto/               ← Objetos de transferencia
│           ├── security/          ← JWT y filtros
│           └── config/            ← CORS, seguridad, datos iniciales
└── spider-biblioteca-android/     ← App Android (Android Studio)
    └── app/src/main/java/com/joel/spiderbiblioteca/
        ├── data/
        │   ├── api/               ← Retrofit (llamadas al backend)
        │   ├── model/             ← Modelos de datos
        │   ├── repository/        ← Repositorios
        │   ├── local/             ← Caché, sesión, tema, perfil
        │   └── seed/              ← Datos locales (quiz, relaciones...)
        ├── viewmodel/             ← ViewModels
        ├── ui/
        │   ├── screens/           ← Todas las pantallas
        │   ├── components/        ← Componentes reutilizables
        │   └── theme/             ← Colores y tipografía
        └── navigation/            ← Rutas de navegación
```

---

## Cómo ponerlo en marcha

### PASO 1: Iniciar MySQL con XAMPP

1. Abre **XAMPP Control Panel**
2. Pulsa **Start** en **MySQL**
3. Comprueba que se pone en verde

### PASO 2: Crear la base de datos

1. En XAMPP pulsa **Admin** en MySQL (abre phpMyAdmin)
2. Haz clic en **Nueva**
3. Nombre: `spider_biblioteca`, cotejamiento: `utf8mb4_unicode_ci`
4. Pulsa **Crear**

> Las tablas las crea Hibernate automáticamente al arrancar el backend. Los datos de ejemplo (trajes, universos, timeline...) también se cargan solos gracias a `DataInitializer.java`.

### PASO 3: Abrir el backend en IntelliJ IDEA

1. File → Open → abre la carpeta `spider-biblioteca-backend`
2. Espera a que Maven descargue las dependencias
3. Si hay errores, ve al panel Maven (derecha) → **Reload All Maven Projects**

### PASO 4: Configurar las credenciales de MySQL

Abre `src/main/resources/application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=       # vacío si XAMPP no tiene contraseña
```

### PASO 5: Arrancar Spring Boot

1. Abre `SpiderBibliotecaApplication.java`
2. Pulsa el botón verde ▶ junto al `main`
3. Espera a ver: `Started SpiderBibliotecaApplication`

O desde la terminal:
```bash
cd spider-biblioteca-backend
mvn spring-boot:run
```

### PASO 6: Verificar que el backend funciona

Abre en el navegador:
```
http://localhost:8080/api/personajes
```
Si ves `[]` o datos JSON, está bien.

### PASO 7: Abrir el Android en Android Studio

1. File → Open → abre la carpeta `spider-biblioteca-android`
2. Espera a que Gradle sincronice (puede tardar un rato la primera vez)
3. Si pide actualizar el Gradle Wrapper, acepta

### PASO 8: Comprobar la URL del backend

Abre `RetrofitInstance.kt` y verifica que la URL es correcta:

```kotlin
// Para el emulador:
private const val BASE_URL = "http://10.0.2.2:8080/"

// Para móvil físico por WiFi:
private const val BASE_URL = "http://192.168.1.X:8080/"
// (cambia la X por tu IP, mírala con ipconfig en Windows)
```

### PASO 9: Ejecutar en el emulador

1. En Android Studio crea o selecciona un emulador desde Device Manager
2. Pulsa ▶ Run
3. La app debería abrir en la pantalla de login

---

## Endpoints de la API

### Personajes
| Método | URL | Qué hace |
|--------|-----|----------|
| GET | `/api/personajes` | Todos los personajes |
| GET | `/api/personajes/{id}` | Un personaje por ID |
| POST | `/api/personajes` | Crear personaje |
| PUT | `/api/personajes/{id}` | Editar personaje |
| DELETE | `/api/personajes/{id}` | Borrar personaje |
| GET | `/api/personajes/buscar?nombre=spider` | Buscar por nombre |
| GET | `/api/personajes/tipo/heroe` | Filtrar por tipo |

### Autenticación
| Método | URL | Qué hace |
|--------|-----|----------|
| POST | `/api/auth/register` | Registrar usuario |
| POST | `/api/auth/login` | Iniciar sesión (devuelve token JWT) |
| GET | `/api/auth/me` | Ver datos del usuario autenticado |

### Favoritos (requiere token JWT)
| Método | URL | Qué hace |
|--------|-----|----------|
| GET | `/api/favoritos` | Ver mis favoritos |
| POST | `/api/favoritos/{personajeId}` | Añadir a favoritos |
| DELETE | `/api/favoritos/{personajeId}` | Quitar de favoritos |

### Trajes
| Método | URL | Qué hace |
|--------|-----|----------|
| GET | `/api/trajes` | Todos los trajes |
| GET | `/api/trajes/{id}` | Un traje por ID |

### Universos
| Método | URL | Qué hace |
|--------|-----|----------|
| GET | `/api/universos` | Todos los universos |
| GET | `/api/universos/{id}` | Un universo por ID |

### Línea temporal
| Método | URL | Qué hace |
|--------|-----|----------|
| GET | `/api/timeline` | Todos los eventos |
| GET | `/api/timeline/{id}` | Un evento por ID |

---

## Diferencia entre localhost y 10.0.2.2

| Desde dónde | URL |
|-------------|-----|
| Navegador del PC | `http://localhost:8080` |
| Emulador Android | `http://10.0.2.2:8080` |
| Móvil físico (WiFi) | `http://192.168.1.X:8080` |

El emulador es una máquina virtual, así que su "localhost" apunta a sí mismo, no al PC. La IP `10.0.2.2` es la que usa el emulador para referirse al ordenador host.

---

## Errores típicos y cómo arreglarlos

### CLEARTEXT communication not permitted
La app no permite HTTP por defecto en Android 9+. Ya está solucionado en el `AndroidManifest.xml` con:
```xml
android:usesCleartextTraffic="true"
```

### Failed to connect to localhost
Estás usando `localhost` desde el emulador. Cámbialo por `10.0.2.2` en `RetrofitInstance.kt`.

### Puerto 8080 ocupado
```properties
# En application.properties cambia el puerto:
server.port=8081
```
O cierra el proceso que lo usa:
```cmd
netstat -ano | findstr :8080
taskkill /PID <numero_pid> /F
```

### MySQL no conecta
1. Abre XAMPP y comprueba que MySQL está en verde
2. Si no arranca, busca `mysqld.exe` en el Administrador de tareas y ciérralo
3. Luego vuelve a iniciarlo desde XAMPP

### Access denied for user 'root'
```properties
spring.datasource.password=tu_contraseña
```
Si XAMPP no tiene contraseña, déjalo vacío.

### Gradle no sincroniza
1. File → Invalidate Caches and Restart
2. Build → Clean Project → Rebuild Project
3. Verifica que tienes internet (necesita descargar dependencias)

### La app no muestra datos aunque el backend esté arrancado
Mira el Logcat en Android Studio con el filtro `OkHttp`. Si ves `connection refused`, el backend no está corriendo o la URL es incorrecta.

### Error CORS
Ya está configurado en `CorsConfig.java`. Si sigue fallando, reinicia Spring Boot.

---

## Tecnologías usadas

| Tecnología | Versión |
|-----------|---------|
| Java | 17 |
| Spring Boot | 3.3.5 |
| Spring Security + JWT | incluido |
| Hibernate/JPA | incluido en Spring Boot |
| MySQL Connector | 9.x |
| Maven | 3.8+ |
| Kotlin | 2.0.21 |
| Jetpack Compose BOM | 2024.12.01 |
| Navigation Compose | 2.8.5 |
| Retrofit | 2.11.0 |
| Coil | 2.7.0 |
| Coroutines | 1.9.0 |

---

*Proyecto de fin de módulo — DAM (Desarrollo de Aplicaciones Multiplataforma)*
