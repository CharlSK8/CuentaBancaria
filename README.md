# API Cuenta Bancaria

Esta API REST proporciona un sistema completo para la gestión de cuentas bancarias, permitiendo a los usuarios realizar operaciones bancarias básicas de manera segura.

## Descripción General

La aplicación proporciona servicios para la creación, consulta, registro y consulta de transacciones (depósitos y retiros) con validación de saldos, y gestión de cuentas bancarias asociadas a usuarios. Para la comunicación asincrónica y el manejo de eventos, se utiliza ActiveMQ como broker de mensajería.

## Dockerizar la Aplicación desde GHCR

Este documento proporciona los pasos para obtener, ejecutar y administrar un contenedor Docker con una imagen almacenada en GitHub Container Registry (GHCR).

## Prerrequisitos

- Tener instalado [Podman](https://podman.io/)
- Acceso a GitHub Container Registry (GHCR)
- Haber iniciado sesión en GHCR con Docker:

  ```sh
  podman login ghcr.io -u <USERNAME> -p <PASSWORD> ghcr.io 
  ```

## Descargar y ejecutar el proyecto en contenedor

- **Crear network**

    ```sh
    podman network create red-bank
    ```

- **Descargar la imagen desde GHCR**

    ```sh
    podman pull ghcr.io/charlsk8/cuentas-bancarias:v1.0.0
    ```

- **Correr el contenedor**

    ```sh
    podman run --rm --name cuenta-bancaria --network=red-bank -p 8080:8080 -d ghcr.io/charlsk8/cuentas-bancarias:v1.0.0
    ```

## Generar imagen

- **Construir imagen**

    ```sh
    podman build -t cuenta-bancaria -f Containerfile . 
    ```

  - **Correr el contenedor**

    ```sh
    podman run --rm --name cuenta-bancaria --network=red-bank -p 8080:8080 -d cuenta-bancaria
    ```

## Tecnologías Utilizadas

- **Java 17**: Lenguaje de programación principal utilizado para desarrollar la aplicación.
- **Spring Boot**: Framework utilizado para crear aplicaciones basadas en Spring de manera rápida y sencilla.
- **Lombok**: Herramienta que reduce el código boilerplate mediante anotaciones.
- **Mockito**: Framework de pruebas utilizado para crear mocks y realizar pruebas unitarias.
- **JUnit 5**: Framework de pruebas utilizado para escribir y ejecutar pruebas unitarias.
- **Swagger**: Herramienta utilizada para documentar y probar APIs RESTful.
- **Git**: Sistema de control de versiones utilizado para el control de versiones del código fuente.
- **Gradle**: Herramienta de construcción utilizada para compilar y ejecutar la aplicación.
- **Spring Security + JWT**: Implementación de autenticación y autorización segura mediante tokens JWT.
- **Spring Data JPA**: Framework para la gestión de persistencia y acceso a bases de datos de manera simplificada.
- **DBH2**: Base de datos en memoria utilizada para pruebas y desarrollo.
- **MapStruct**: Herramienta para la conversión eficiente de entidades y DTOs.
- **ActiveMQ**: Broker de mensajería utilizado para la comunicación asincrónica entre servicios.
- **Podman**: Herramienta para la gestión de contenedores sin necesidad de un demonio en segundo plano, compatible con Docker y enfocada en la seguridad.


## Endpoints

### Cuentas Bancarias

- POST `/api/v1/cuentas` - Crea una nueva cuenta bancaria
- GET `/api/v1/cuentas` - Obtiene todas las cuentas del usuario
- GET `/api/v1/cuentas/{id}` - Obtiene una cuenta específica
- PUT `/api/v1/cuentas/{id}` - Actualiza información de una cuenta
- DELETE `/api/v1/cuentas/{id}` - Elimina una cuenta

### Movimientos

- POST `/api/v1/movimientos` - Registra un nuevo movimiento (depósito/retiro)
- GET `/api/v1/movimientos` - Obtiene historial de movimientos
- GET `/api/v1/movimientos/{id}` - Obtiene un movimiento específico

## Pasos de Instalación

1. **Clonar el Repositorio**

    ```bash
    git clone https://github.com/CharlSK8/CuentaBancaria.git
    ```

2. **Compilar el Proyecto**

    ```bash
    ./gradlew build  
    ```

3. **Configurar la Base de Datos**

    El proyecto utiliza H2 (base de datos en memoria), por lo que no requiere configuración adicional de base de datos.

4. **Ejecutar la Aplicación**

    Hay dos formas de ejecutar el proyecto:

    **Opción 1: Desde Gradle/Maven**

    ```bash
    ./gradlew bootRun
    ```

    **Opción 2: Desde el IDE**

    1. Abrir el proyecto en tu IDE preferido (IntelliJ IDEA, Eclipse, etc.).
    2. Localizar la clase principal (debe tener la anotación `@SpringBootApplication`).
    3. Ejecutar como aplicación Java.

## Verificación

Una vez iniciada la aplicación:

- La API estará disponible en `http://localhost:8080`.
- Puedes probar los endpoints utilizando herramientas como Postman, Insomnia o cURL.

## Endpoints Principales

Como se detalla en el README, podrás acceder a:

- Gestión de cuentas: `GET /api/v1/cuentas`, `POST /api/v1/cuentas`
- Gestión de movimientos: `GET /api/v1/movimientos`, `POST /api/v1/movimientos`

### Documentación de Swagger

Swagger es una herramienta poderosa para documentar y probar APIs RESTful. En este proyecto, se ha utilizado Swagger para generar automáticamente la documentación de la API, lo que facilita a los desarrolladores y a otros interesados comprender y probar los endpoints disponibles.

#### Acceso a la Documentación de Swagger

La documentación de Swagger para esta aplicación está disponible en la siguiente URL:

`http://localhost:8080/webjars/swagger-ui/index.html`

Al acceder a esta URL, se puede visualizar una interfaz gráfica que muestra todos los endpoints disponibles, junto con sus métodos HTTP, parámetros requeridos, y posibles respuestas. Además, Swagger permite probar directamente los endpoints desde la interfaz, lo que facilita la verificación y el debugging de la API.

## Notas Adicionales

- Asegúrate de tener los puertos necesarios disponibles (por defecto 8080).
- La aplicación utiliza JWT para autenticación, por lo que necesitarás el token para acceder a los endpoints protegidos.
- Para pruebas, la base de datos H2 se reiniciará cada vez que se reinicie la aplicación.

## Test assessment 2
