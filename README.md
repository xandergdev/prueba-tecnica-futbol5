# Prueba Técnica - Equipo Titular Fútbol 5

API REST desarrollada en Spring Boot que permite registrar los entrenamientos semanales de un equipo de fútbol 5 y calcular el equipo titular en base a los resultados obtenidos.

## Requisitos

- Java 17
- Maven (incluido en el proyecto vía `mvnw` / `mvnw.cmd`)
- MySQL 8.x

## Configuración

1. Crea el schema de la base de datos en MySQL:

```sql
CREATE DATABASE futbol5;
```

2. Configura tus credenciales de conexión en `src/main/resources/application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/futbol5
    username: TU_USUARIO
    password: TU_CONTRASEÑA
  jpa:
    hibernate:
      ddl-auto: update
```

Las tablas (`jugador`, `entrenamiento`) se crean automáticamente al levantar la aplicación (Hibernate `ddl-auto: update`).

## Cómo ejecutar el proyecto

Desde la raíz del proyecto:

```bash
./mvnw spring-boot:run
```

(en Windows: `mvnw.cmd spring-boot:run`)

La aplicación queda disponible en `http://localhost:8080`.

## Arquitectura

El proyecto sigue una arquitectura por capas:

- **entity**: `Jugador` y `Entrenamiento` (relación `@ManyToOne` de Entrenamiento hacia Jugador).
- **repository**: `JugadorRepository` y `EntrenamientoRepository` (con la consulta derivada `findByJugadorAndSemana`).
- **service**: `EntrenamientoService`, contiene la lógica de negocio (cálculo del resultado, validación de entrenamientos completos, cálculo de promedios y selección de titulares).
- **dto**: `TitularesDTO`, usado para exponer el resumen de cada jugador titular sin exponer las entidades directamente.
- **controller**: `EntrenamientoController` (endpoints principales de la prueba) y `JugadorController` (endpoint auxiliar para crear jugadores de prueba).

## Endpoints

### 1. Crear un jugador (auxiliar, para poder probar los entrenamientos)

```
POST /jugadores
```

Body:
```json
{
  "nombre": "Jugador1"
}
```

Respuesta: el jugador creado, con su `id` asignado.

### 2. Registrar un entrenamiento

```
POST /entrenamientos
```

Body:
```json
{
  "jugador": { "id": 1 },
  "semana": 1,
  "potencia": 10,
  "velocidad": 5,
  "pases": 25
}
```

El `resultado` de ese entrenamiento se calcula automáticamente con la fórmula:

```
resultado = potencia * 0.20 + velocidad * 0.30 + pases * 0.50
```

Respuesta: el entrenamiento guardado, incluyendo el `resultado` calculado.

### 3. Obtener el equipo titular de una semana

```
GET /entrenamientos/titulares?semana=1
```

- Si **todos** los jugadores tienen sus 3 entrenamientos registrados en esa semana, devuelve (status 200) la lista de los 5 jugadores con mayor promedio de resultado, ordenados de mayor a menor:

```json
[
  {
    "nombre": "Jugador3",
    "potencia": 15.33,
    "pases": 29.67,
    "velocidad": 3.33,
    "promedio": 18.9
  }
]
```

- Si **algún** jugador no completó sus 3 entrenamientos de la semana, devuelve (status 400) un mensaje indicando que no hay suficiente información.

## Notas

- El sistema soporta múltiples semanas: el campo `semana` en cada entrenamiento identifica a qué semana pertenece, por lo que se puede repetir el ciclo (registrar 3 entrenamientos + consultar titulares) para cualquier número de semana.
- Actualmente el top de titulares está fijo en 5 (fútbol 5), pero el diseño (entidades, repositorios, DTO) permite escalar a un equipo de fútbol 11 ajustando esa cantidad en el service.
