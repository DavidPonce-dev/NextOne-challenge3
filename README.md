# Challenge3 - Foro con Spring Boot

Aplicación de foro construida con **Spring Boot 3**, **Spring Security**, **JWT**, **Spring Data JPA** y **Flyway**.  
Permite a los usuarios registrarse, autenticarse y crear, actualizar o eliminar tópicos.

---

## 🚀 Tecnologías

- **Java 21**
- **Spring Boot 3**
- **Spring Data JPA / Hibernate**
- **Spring Security + JWT**
- **Flyway** (migraciones de base de datos)
- **MySQL 8**
- **Lombok**


---

## 🔐 Autenticación

La seguridad se implementa con **JWT**.

- **POST /login** → recibe `username` y `password`, valida credenciales y devuelve un token JWT.
- El token debe enviarse en cada request con el header:
  ```
  Authorization: Bearer <token>
  ```

---

## 📌 Endpoints principales

### Topics
- **GET /topics** → lista paginada de tópicos activos.
- **GET /topics/{id}** → detalle de un tópico.
- **POST /topics** → crear un tópico (requiere login).
- **PUT /topics/{id}** → actualizar un tópico (requiere login y ser autor).
- **DELETE /topics/{id}** → desactivar un tópico (requiere login y ser autor).

### Auth
- **POST /login** → autenticación de usuarios y generación de token JWT.

---

## ⚠️ Manejo de errores

El proyecto incluye un `@RestControllerAdvice` que captura excepciones comunes:

- `404 Not Found` → entidad no encontrada.
- `400 Bad Request` → errores de validación (con lista de campos y mensajes).
- `409 Conflict` → claves duplicadas u otras violaciones de integridad.
- `401 Unauthorized` → credenciales inválidas.

---

## ▶️ Cómo ejecutar

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tuusuario/challenge3.git
   cd challenge3
   ```

2. Configurar base de datos MySQL en `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/forum_ap
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_password
   spring.jpa.hibernate.ddl-auto=validate
   ```

3. Ejecutar migraciones con Flyway al iniciar la app.

4. Levantar la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

---

## ✅ Próximos pasos

- Documentación con **Springdoc OpenAPI/Swagger UI**.
- Tests unitarios e integración.

