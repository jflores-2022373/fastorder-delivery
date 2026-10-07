# FastOrder Delivery API

API REST desarrollada con Spring Boot para la gestion integral de un sistema de delivery, abarcando la administracion de pedidos, comercios, productos y control de acceso basado en roles.

## Descripcion General

El sistema esta construido bajo una arquitectura modular en capas, permitiendo la gestion segura de usuarios mediante autenticacion basada en JSON Web Tokens (JWT). Cuenta con flujos diferenciados para administradores y clientes, garantizando la integridad de las operaciones de inventario, creacion de establecimientos y procesamiento de ordenes de compra.

## Componentes del Sistema

* Autenticacion y seguridad mediante tokens JWT y control de acceso por roles (ADMIN y CLIENTE).
* Administracion de comercios y catalogos de establecimientos.
* Control de inventario, precios y asociacion de productos.
* Procesamiento de pedidos con validacion de existencia y stock.
* Automatizacion de pruebas funcionales y de estres mediante scripts en Bash.

## Estructura del Proyecto

```text
com.delivery.fastorder/
├── controller/     # Controladores REST para endpoints de autenticacion, comercios, productos y pedidos
├── service/        # Logica de negocio y validaciones operativas
├── repository/     # Interfaces de persistencia de datos con Spring Data JPA
├── model/          # Entidades relacionales de la base de datos
└── dto/            # Objetos de transferencia de datos para solicitudes y respuestas
Requisitos y Configuracion
Java 17 o superior.

Spring Boot.

Base de datos PostgreSQL.

Configuracion de la Conexion
Modificar las propiedades de conexion en el archivo src/main/resources/application.properties:

Properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nombre_base_datos
spring.datasource.username=usuario
spring.datasource.password=contraseña
Ejecucion de Pruebas
El repositorio incluye un script automatizado para la ejecucion de pruebas unitarias y validacion del flujo completo (registro, autenticacion, creacion de registros, verificacion de restricciones de seguridad HTTP 403 y pruebas de estres).

Para ejecutar el script desde la terminal en entornos compatibles:

Bash
./test-api4.sh
