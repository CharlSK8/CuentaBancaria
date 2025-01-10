# API Cuenta Bancaria
Esta API REST proporciona un sistema completo para la gestión de cuentas bancarias, permitiendo a los usuarios realizar operaciones bancarias básicas de manera segura. El sistema implementa autenticación mediante JWT y permite:

- Gestión de usuarios: Registro de nuevos usuarios, actualización de datos y control de estado de cuentas.
- Autenticación segura: Sistema de login/logout con tokens JWT para proteger los endpoints.
- Administración de cuentas bancarias: Creación, consulta y gestión de cuentas asociadas a usuarios.
- Control de movimientos: Registro y consulta de transacciones (depósitos y retiros) con validación de saldos.
- Persistencia de datos: Almacenamiento seguro de la información en base de datos PostgreSQL.

El proyecto está construido siguiendo las mejores prácticas de desarrollo, implementando una arquitectura en capas, pruebas unitarias y documentación clara de los endpoints disponibles.

## Tecnologías Utilizadas
- Java 17
- Spring Boot 3
- Spring Security + JWT
- Spring Data JPA
- H2
- Lombok
- MapStruct
- JUnit 5 + Mockito
- Gradle

## Endpoints

### Usuarios
- POST `/api/v1/usuarios/registrar` - Registra un nuevo usuario
- PUT `/api/v1/usuarios/{id}` - Actualiza información de un usuario existente 
- PUT `/api/v1/usuarios/{id}/inactivar` - Inactiva un usuario

### Autenticación
- POST `/api/v1/auth/login` - Inicia sesión y genera token JWT
- POST `/api/v1/auth/logout` - Cierra sesión y revoca token

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


