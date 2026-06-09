# Spider-Biblioteca

Aplicación completa de biblioteca visual de personajes del universo Spider-Man.

- **Backend:** Java 17 + Spring Boot 3.3 + Hibernate/JPA + MySQL
- **Frontend:** Android Kotlin + Jetpack Compose + Material Design 3
- **Conexión:** Retrofit 2 + REST API

---

## Estructura del proyecto

```
SpiderBiblioteca/
├── README.md                        ← Este archivo
├── spider-biblioteca-backend/       ← Proyecto Spring Boot (IntelliJ IDEA)
│   ├── pom.xml
│   ├── database.sql
│   ├── personajes-ejemplo.json
│   ├── README.md
│   └── src/...
└── spider-biblioteca-android/       ← Proyecto Android (Android Studio)
    ├── app/build.gradle.kts
    ├── gradle/libs.versions.toml
    ├── settings.gradle.kts
    ├── README.md
    └── app/src/...
```

---

## Guía completa de puesta en marcha

### PASO 1: Iniciar MySQL con XAMPP

1. Abre **XAMPP Control Panel**
2. Pulsa **Start** en el módulo **MySQL**
3. Verifica que el indicador se pone en verde

### PASO 2: Crear la base de datos

1. En XAMPP, pulsa **Admin** en MySQL (abre phpMyAdmin)
2. Haz clic en **Nueva** en el panel izquierdo
3. Nombre: `spider_biblioteca`
4. Cotejamiento: `utf8mb4_unicode_ci`
5. Pulsa **Crear**

> La tabla `personajes` se crea automáticamente cuando arrancas Spring Boot.

### PASO 3: Abrir el backend en IntelliJ IDEA

1. Abre **IntelliJ IDEA**
2. File → Open → navega a `SpiderBiblioteca/spider-biblioteca-backend`
3. Espera a que Maven descargue las dependencias (barra de progreso)
4. Si hay errores, ve a Maven (panel derecho) → pulsa **Reload All Maven Projects**

### PASO 4: Configurar credenciales de MySQL

Abre `src/main/resources/application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=       # vacío si XAMPP no tiene contraseña
```

### PASO 5: Ejecutar Spring Boot

1. Abre `SpiderBibliotecaApplication.java`
2. Pulsa el botón verde ▶ junto al método `main`
3. Espera a ver en consola: `Started SpiderBibliotecaApplication`

O desde terminal:
```bash
cd SpiderBiblioteca/spider-biblioteca-backend
mvn spring-boot:run
```

### PASO 6: Verificar que el backend funciona

Abre el navegador en:
```
http://localhost:8080/api/personajes
```
Deberías ver `[]` (lista vacía si no has insertado datos).

### PASO 7: Insertar datos de ejemplo (opcional)

Con Postman o cualquier cliente REST:
- POST a `http://localhost:8080/api/personajes`
- Body: copia un objeto del archivo `personajes-ejemplo.json`

### PASO 8: Abrir el frontend en Android Studio

1. Abre **Android Studio**
2. File → Open → navega a `SpiderBiblioteca/spider-biblioteca-android`
3. Espera a que Gradle sincronice las dependencias

> Si es la primera vez, puede tardar varios minutos mientras descarga dependencias.

### PASO 9: Verificar la URL del backend en Android

Abre `RetrofitInstance.kt`:

```kotlin
private const val BASE_URL = "http://10.0.2.2:8080/"
```

- `10.0.2.2` = la IP del ordenador host **desde el emulador Android**
- Si usas **móvil físico**, usa tu IP local: `http://192.168.1.X:8080/`
  (ejecuta `ipconfig` en Windows para ver tu IP)

### PASO 10: Ejecutar en emulador

1. En Android Studio, crea o selecciona un emulador (Device Manager)
2. Pulsa ▶ Run
3. La app se instalará y abrirá en el emulador

---

## Diferencia entre localhost y 10.0.2.2

| Contexto | URL correcta |
|----------|-------------|
| Navegador del PC | `http://localhost:8080` |
| Emulador Android | `http://10.0.2.2:8080` |
| Móvil físico (WiFi) | `http://192.168.1.X:8080` |

**¿Por qué?** El emulador Android es una máquina virtual separada. Cuando el emulador dice "localhost", se refiere a sí mismo (la VM), no al PC. Para referirse al PC desde el emulador, se usa la IP especial `10.0.2.2`.

---

## Endpoints de la API

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | `http://localhost:8080/api/personajes` | Todos los personajes |
| GET | `http://localhost:8080/api/personajes/1` | Personaje con id=1 |
| POST | `http://localhost:8080/api/personajes` | Crear personaje |
| PUT | `http://localhost:8080/api/personajes/1` | Actualizar personaje |
| DELETE | `http://localhost:8080/api/personajes/1` | Eliminar personaje |
| GET | `http://localhost:8080/api/personajes/buscar?nombre=spider` | Buscar por nombre |
| GET | `http://localhost:8080/api/personajes/tipo/heroe` | Filtrar por tipo |

---

## Solución de errores típicos

### 1. CLEARTEXT communication not permitted

```
CLEARTEXT communication to 10.0.2.2 not permitted by network security policy
```

**Solución:** Añade en `AndroidManifest.xml` dentro de `<application>`:
```xml
android:usesCleartextTraffic="true"
```
(Ya está incluido en el proyecto.)

---

### 2. Failed to connect to localhost

```
Failed to connect to localhost/127.0.0.1:8080
```

**Solución:** Cambia la URL en `RetrofitInstance.kt`:
```kotlin
// MAL (dentro del emulador):
private const val BASE_URL = "http://localhost:8080/"

// BIEN (IP del host desde el emulador):
private const val BASE_URL = "http://10.0.2.2:8080/"
```

---

### 3. Puerto 8080 ocupado

```
Port 8080 is already in use
```

**Solución A:** Cambia el puerto en `application.properties`:
```properties
server.port=8081
```

**Solución B:** Cierra el proceso que lo usa:
```cmd
netstat -ano | findstr :8080
taskkill /PID <número_pid> /F
```

---

### 4. MySQL no conectado

```
Communications link failure / Unable to connect to MySQL
```

**Solución:**
1. Abre XAMPP → verifica que MySQL está en verde
2. Si no arranca, comprueba que el puerto 3306 no está ocupado
3. Reinicia el servicio MySQL desde XAMPP

---

### 5. Access denied for user 'root'

```
Access denied for user 'root'@'localhost'
```

**Solución:** Edita `application.properties`:
```properties
spring.datasource.password=tu_contraseña
```
Si XAMPP sin contraseña, déjalo vacío: `spring.datasource.password=`

---

### 6. Gradle no sincroniza en Android Studio

**Solución:**
1. File → Invalidate Caches and Restart
2. Build → Clean Project → Rebuild Project
3. Comprueba que tienes conexión a Internet (necesita descargar dependencias)
4. Verifica que el JDK está configurado: File → Project Structure → SDK Location

---

### 7. App sin Internet / INTERNET permission denied

**Solución:** Verifica que `AndroidManifest.xml` contiene:
```xml
<uses-permission android:name="android.permission.INTERNET" />
```
(Ya está incluido en el proyecto.)

---

### 8. Error CORS desde el navegador o Android

```
CORS policy: No 'Access-Control-Allow-Origin' header
```

**Solución:** Ya está configurado en `CorsConfig.java` del backend.
Si persiste, reinicia Spring Boot.

---

## Comandos rápidos

### Arrancar backend
```bash
cd SpiderBiblioteca/spider-biblioteca-backend
mvn spring-boot:run
```

### Compilar sin ejecutar
```bash
mvn clean package
```

### Ver logs de MySQL (XAMPP)
Abre XAMPP → MySQL → Logs

---

## Tecnologías y versiones

| Tecnología | Versión |
|-----------|---------|
| Java | 17+ |
| Spring Boot | 3.3.5 |
| Hibernate | Incluido en Spring Boot |
| MySQL Connector | 9.x (gestionado por Spring Boot) |
| Maven | 3.8+ |
| Kotlin | 2.0.21 |
| Android Compose BOM | 2024.12.01 |
| Retrofit | 2.11.0 |
| Coil | 2.7.0 |
| Navigation Compose | 2.8.5 |

---

*Proyecto creado para el módulo DAM - Desarrollo de Aplicaciones Multiplataforma*
