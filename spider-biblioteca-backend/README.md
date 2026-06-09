# Spider-Biblioteca - Backend Spring Boot

Backend REST API para la aplicación Spider-Biblioteca.
Tecnologías: Java 17 · Spring Boot 3.3 · Hibernate/JPA · MySQL · Maven · Lombok

---

## Requisitos previos

- JDK 17 o superior instalado
- Maven 3.8+ instalado (o usar el wrapper mvnw)
- MySQL corriendo (recomendado: XAMPP)
- IntelliJ IDEA (Community o Ultimate)

---

## 1. Abrir el proyecto en IntelliJ IDEA

1. Abre IntelliJ IDEA
2. Pulsa **File → Open**
3. Navega a `SpiderBiblioteca/spider-biblioteca-backend`
4. Selecciona la carpeta y pulsa **Open**
5. IntelliJ detectará el `pom.xml` → pulsa **Load Maven Project** si te lo pide
6. Espera a que descargue las dependencias (barra de progreso abajo)

---

## 2. Crear la base de datos en MySQL

### Con XAMPP (recomendado)

1. Abre **XAMPP Control Panel**
2. Pulsa **Start** en el módulo **MySQL**
3. Pulsa **Admin** en MySQL (abre phpMyAdmin en el navegador)
4. En phpMyAdmin, haz clic en **Nueva** (panel izquierdo)
5. Escribe el nombre: `spider_biblioteca`
6. Cotejamiento: `utf8mb4_unicode_ci`
7. Pulsa **Crear**

### Con línea de comandos

```bash
mysql -u root -p
CREATE DATABASE spider_biblioteca CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EXIT;
```

> La tabla `personajes` la crea Hibernate automáticamente al arrancar Spring Boot
> (gracias a `ddl-auto=update` en application.properties).

---

## 3. Configurar credenciales de MySQL

Abre `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/spider_biblioteca?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=         # <-- pon tu contraseña aquí si tienes
```

Por defecto XAMPP no pone contraseña al root, así que déjalo vacío.

---

## 4. Ejecutar Spring Boot

### Desde IntelliJ IDEA

1. Abre `SpiderBibliotecaApplication.java`
2. Pulsa el botón verde ▶ junto al método `main`
3. Verás en la consola: `Started SpiderBibliotecaApplication in X seconds`

### Desde la terminal (CMD o PowerShell)

```bash
cd SpiderBiblioteca/spider-biblioteca-backend
mvn spring-boot:run
```

---

## 5. Probar los endpoints

Una vez arrancado, abre el navegador en:

```
http://localhost:8080/api/personajes
```

Deberías ver `[]` (lista vacía si no has insertado datos).

---

## 6. Endpoints disponibles

| Método | URL | Descripción |
|--------|-----|-------------|
| GET | `/api/personajes` | Obtener todos los personajes |
| GET | `/api/personajes/{id}` | Obtener personaje por ID |
| POST | `/api/personajes` | Crear nuevo personaje |
| PUT | `/api/personajes/{id}` | Actualizar personaje |
| DELETE | `/api/personajes/{id}` | Eliminar personaje |
| GET | `/api/personajes/buscar?nombre=spider` | Buscar por nombre |
| GET | `/api/personajes/tipo/heroe` | Filtrar por tipo |

---

## 7. Ejemplos JSON para Postman

### POST /api/personajes - Crear Spider-Man

```json
{
  "nombre": "Spider-Man",
  "nombreReal": "Peter Parker",
  "apodos": "Spidey, El Trepamuros",
  "poderes": "Fuerza sobrehumana, agilidad, sentido de araña, lanzar telarañas",
  "descripcion": "Estudiante de Queens que tras ser mordido por una araña radiactiva adquiere poderes increíbles.",
  "primeraAparicion": "Amazing Fantasy #15 (1962)",
  "tipo": "heroe",
  "imagenUrl": "spiderman.png"
}
```

### PUT /api/personajes/1 - Actualizar

```json
{
  "nombre": "Spider-Man",
  "nombreReal": "Peter Parker",
  "apodos": "Spidey, El Trepamuros, El Arácnido",
  "poderes": "Fuerza sobrehumana, agilidad, sentido de araña, telarañas",
  "descripcion": "Descripción actualizada.",
  "primeraAparicion": "Amazing Fantasy #15 (1962)",
  "tipo": "heroe",
  "imagenUrl": "spiderman.png"
}
```

### Valores válidos para "tipo"

- `heroe`
- `villano`
- `aliado`
- `antiheroe`

---

## 8. Insertar datos de ejemplo

Usa el archivo `personajes-ejemplo.json`. Puedes importarlo con Postman:
1. Abre Postman
2. Crea una petición POST a `http://localhost:8080/api/personajes`
3. Body → raw → JSON
4. Copia cada objeto del JSON y envíalo uno a uno

O desde MySQL/phpMyAdmin, descomenta el INSERT en `database.sql` y ejecútalo.

---

## 9. Solución de problemas típicos

### MySQL no arranca en XAMPP

- Comprueba que el puerto 3306 no esté ocupado por otro proceso
- En Windows: abre el Administrador de tareas → busca `mysqld.exe` y ciérralo
- Luego vuelve a iniciar MySQL desde XAMPP

### Puerto 8080 ocupado

```properties
# En application.properties, cambia el puerto:
server.port=8081
```

O cierra el proceso que usa el 8080:
```bash
# En Windows CMD:
netstat -ano | findstr :8080
taskkill /PID <el_pid_que_aparece> /F
```

### Error de driver MySQL: No suitable driver found

- Verifica que `mysql-connector-j` está en el pom.xml (ya está incluido)
- Haz Maven → Reload en IntelliJ (botón elefante → Reload All Maven Projects)

### Error: Access denied for user 'root'@'localhost'

```properties
# En application.properties, ajusta la contraseña:
spring.datasource.password=tu_contraseña_aqui
```

Si XAMPP no tiene contraseña, déjalo vacío.

### Error: Unknown database 'spider_biblioteca'

La base de datos no existe. Sigue el paso 2 de este README para crearla.

### Tabla no creada / Error en columnas

- Comprueba que `spring.jpa.hibernate.ddl-auto=update` está en application.properties
- Reinicia Spring Boot para que Hibernate regenere las tablas

### Error CORS desde Android

La configuración CORS ya está incluida en `CorsConfig.java`.
Permite peticiones desde cualquier origen, incluyendo el emulador Android (10.0.2.2).
Si sigues teniendo problemas, asegúrate de que no hay otro filtro de seguridad activo.

### OutOfMemoryError al compilar

En IntelliJ, ve a: Help → Change Memory Settings → aumenta a 2048 MB o más

---

## Estructura del proyecto

```
spider-biblioteca-backend/
├── pom.xml
├── database.sql
├── personajes-ejemplo.json
└── src/
    └── main/
        ├── java/com/joel/spiderbiblioteca/
        │   ├── SpiderBibliotecaApplication.java   <- Clase principal
        │   ├── model/
        │   │   └── Personaje.java                 <- Entidad JPA
        │   ├── repository/
        │   │   └── PersonajeRepository.java       <- Acceso a datos
        │   ├── service/
        │   │   └── PersonajeService.java          <- Lógica de negocio
        │   ├── controller/
        │   │   └── PersonajeController.java       <- Endpoints REST
        │   ├── exception/
        │   │   ├── ResourceNotFoundException.java
        │   │   └── GlobalExceptionHandler.java
        │   └── config/
        │       └── CorsConfig.java
        └── resources/
            └── application.properties
```
