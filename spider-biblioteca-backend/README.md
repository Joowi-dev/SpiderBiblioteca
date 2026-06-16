# Spider-Biblioteca — Backend

API REST hecha con Spring Boot para la app de Spider-Man. Gestiona personajes, usuarios, favoritos, trajes, universos y la línea temporal. La seguridad está montada con JWT.

**Tecnologías:** Java 17 · Spring Boot 3.3 · Spring Security · JWT · Hibernate/JPA · MySQL · Maven · Lombok

---

## Requisitos

- JDK 17 o superior
- Maven 3.8+ (o usar el wrapper `mvnw`)
- MySQL corriendo (recomendado con XAMPP)
- IntelliJ IDEA

---

## Cómo arrancarlo

### 1. Abrir en IntelliJ IDEA

1. File → Open → selecciona la carpeta `spider-biblioteca-backend`
2. IntelliJ detecta el `pom.xml` solo — si te pide "Load Maven Project", acepta
3. Espera a que descargue las dependencias (barra de progreso abajo)

### 2. Crear la base de datos

**Con XAMPP (lo más fácil):**
1. Abre XAMPP → Start en MySQL
2. Pulsa Admin (abre phpMyAdmin)
3. Nueva → nombre: `spider_biblioteca`, cotejamiento: `utf8mb4_unicode_ci` → Crear

**Por línea de comandos:**
```bash
mysql -u root -p
CREATE DATABASE spider_biblioteca CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EXIT;
```

> Hibernate crea las tablas automáticamente al arrancar (`ddl-auto=update`). Los datos de ejemplo (trajes, universos, timeline y personajes) los inserta `DataInitializer.java` si la tabla está vacía.

### 3. Configurar las credenciales

Abre `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/spider_biblioteca?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=       # vacío si usas XAMPP sin contraseña
```

### 4. Ejecutar

**Desde IntelliJ:**
1. Abre `SpiderBibliotecaApplication.java`
2. Pulsa el botón verde ▶ junto al `main`
3. Espera: `Started SpiderBibliotecaApplication in X seconds`

**Desde la terminal:**
```bash
mvn spring-boot:run
```

### 5. Verificar que funciona

```
http://localhost:8080/api/personajes
```
Si ves `[]` o datos JSON, está bien.

---

## Endpoints disponibles

### Personajes
| Método | URL | Qué hace |
|--------|-----|----------|
| GET | `/api/personajes` | Todos los personajes |
| GET | `/api/personajes/{id}` | Uno por ID |
| POST | `/api/personajes` | Crear |
| PUT | `/api/personajes/{id}` | Editar |
| DELETE | `/api/personajes/{id}` | Borrar |
| GET | `/api/personajes/buscar?nombre=spider` | Buscar por nombre |
| GET | `/api/personajes/tipo/heroe` | Filtrar por tipo |

### Autenticación
| Método | URL | Qué hace |
|--------|-----|----------|
| POST | `/api/auth/register` | Registrar usuario nuevo |
| POST | `/api/auth/login` | Login (devuelve token JWT) |
| GET | `/api/auth/me` | Datos del usuario autenticado |

### Favoritos (necesita token JWT en el header)
| Método | URL | Qué hace |
|--------|-----|----------|
| GET | `/api/favoritos` | Mis favoritos |
| POST | `/api/favoritos/{personajeId}` | Añadir a favoritos |
| DELETE | `/api/favoritos/{personajeId}` | Quitar de favoritos |

### Trajes
| Método | URL | Qué hace |
|--------|-----|----------|
| GET | `/api/trajes` | Todos los trajes |
| GET | `/api/trajes/{id}` | Uno por ID |

### Universos
| Método | URL | Qué hace |
|--------|-----|----------|
| GET | `/api/universos` | Todos los universos |
| GET | `/api/universos/{id}` | Uno por ID |

### Línea temporal
| Método | URL | Qué hace |
|--------|-----|----------|
| GET | `/api/timeline` | Todos los eventos |
| GET | `/api/timeline/{id}` | Uno por ID |

---

## Cómo usar la autenticación con Postman

1. Haz POST a `/api/auth/login` con este body:
```json
{
  "username": "tu_usuario",
  "password": "tu_contraseña"
}
```
2. Copia el token que te devuelve
3. En las peticiones que lo necesitan, añade en Headers:
```
Authorization: Bearer <el_token_que_copiaste>
```

---

## Ejemplos JSON

### Registrar usuario
```json
{
  "username": "joel",
  "password": "1234",
  "email": "joel@ejemplo.com"
}
```

### Crear personaje
```json
{
  "nombre": "Spider-Man",
  "nombreReal": "Peter Parker",
  "apodos": "Spidey, El Trepamuros",
  "poderes": "Fuerza sobrehumana, agilidad, sentido de araña, telarañas",
  "descripcion": "Estudiante de Queens que tras ser mordido por una araña radiactiva adquiere poderes.",
  "primeraAparicion": "Amazing Fantasy #15 (1962)",
  "tipo": "heroe",
  "imagenUrl": "spiderman.png"
}
```

**Valores válidos para `tipo`:** `heroe`, `villano`, `aliado`, `antiheroe`

---

## Estructura del proyecto

```
spider-biblioteca-backend/
├── pom.xml
├── database.sql
└── src/main/
    ├── java/com/joel/spiderbiblioteca/
    │   ├── SpiderBibliotecaApplication.java
    │   ├── controller/
    │   │   ├── PersonajeController.java
    │   │   ├── AuthController.java
    │   │   ├── FavoritoController.java
    │   │   ├── TrajeController.java
    │   │   ├── UniversoController.java
    │   │   └── EventoLineaController.java
    │   ├── service/
    │   │   ├── PersonajeService.java
    │   │   ├── AuthService.java
    │   │   ├── FavoritoService.java
    │   │   ├── TrajeService.java
    │   │   ├── UniversoService.java
    │   │   └── EventoLineaService.java
    │   ├── repository/
    │   │   ├── PersonajeRepository.java
    │   │   ├── UsuarioRepository.java
    │   │   ├── TrajeRepository.java
    │   │   ├── UniversoRepository.java
    │   │   └── EventoLineaRepository.java
    │   ├── model/
    │   │   ├── Personaje.java
    │   │   ├── Usuario.java
    │   │   ├── Traje.java
    │   │   ├── Universo.java
    │   │   └── EventoLinea.java
    │   ├── dto/
    │   │   ├── LoginRequest.java
    │   │   ├── RegisterRequest.java
    │   │   ├── AuthResponse.java
    │   │   └── UsuarioPerfilDto.java
    │   ├── security/
    │   │   ├── JwtUtil.java
    │   │   ├── JwtAuthFilter.java
    │   │   └── UserDetailsServiceImpl.java
    │   ├── config/
    │   │   ├── CorsConfig.java
    │   │   ├── SecurityConfig.java
    │   │   └── DataInitializer.java
    │   └── exception/
    │       ├── ResourceNotFoundException.java
    │       └── GlobalExceptionHandler.java
    └── resources/
        └── application.properties
```

---

## Errores típicos

### MySQL no arranca en XAMPP
Comprueba que el puerto 3306 no está ocupado. En Windows abre el Administrador de tareas, busca `mysqld.exe` y ciérralo, luego reinicia MySQL desde XAMPP.

### Puerto 8080 ocupado
```properties
server.port=8081
```
O cierra el proceso:
```cmd
netstat -ano | findstr :8080
taskkill /PID <pid> /F
```

### Access denied for user 'root'
```properties
spring.datasource.password=tu_contraseña
```

### Unknown database 'spider_biblioteca'
La base de datos no existe todavía. Créala siguiendo el paso 2.

### Tabla no creada o error en columnas
Comprueba que en `application.properties` tienes:
```properties
spring.jpa.hibernate.ddl-auto=update
```
Luego reinicia Spring Boot.

### Error CORS desde Android
Ya está configurado en `CorsConfig.java` para aceptar peticiones desde cualquier origen. Si sigue fallando, reinicia el servidor.

### OutOfMemoryError al compilar
Help → Change Memory Settings en IntelliJ y sube a 2048 MB o más.
