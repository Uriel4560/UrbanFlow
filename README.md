# UrbanFlow

**Sistema de Gestión de Pedidos e Inventario de Ropa**

UrbanFlow es un sistema integral de gestión de pedidos, ventas e inventario para una tienda de ropa. Automatiza el control de stock, el checkout con cálculo de totales y la autenticación de administradores y clientes, mediante una API REST construida con Spring Boot y MySQL.

🔗 **Demo en vivo:** El proyecto corre en local siguiendo los pasos de la sección [Instalación y ejecución](#instalación-y-ejecución-local).
Link -> https://urbanflow-frontend-q5xo.onrender.com/ecommerce.html

---

## Capturas de pantalla

| Login | Catálogo / Tienda |
|---|---|
| ![Login](resultados/captura_de_pantalla/login.png) | ![Catálogo](resultados/captura_de_pantalla/catalogo.jpeg) |

| Productos | Checkout |
|---|---|
| ![Productos](resultados/captura_de_pantalla/Productos.jpeg) |

| Panel de administración | Gestión de productos (admin) |
|---|---|
| ![Panel admin](resultados/captura_de_pantalla/panel_admin.png) | ![Gestión productos admin](resultados/captura_de_pantalla/productos_admin.png) |

---

## Tabla de contenidos

1. [Integrantes]
2. [Resumen y Abstract]
3. [Instalación y ejecución local]
4. [Realidad problemática y justificación]
5. [Objetivos]
6. [Marco teórico y metodología]
7. [Cronograma de actividades]
8. [Arquitectura, API y base de datos]
9. [Resultados y palabras clave]
10. [Documentación e informes]

---

## Integrantes

**Grupo 08**

- Christian Rivas Aquino (U23226966)
- Uriel Vasquez Quispe (U23227010)
- Renzo Estefano Yupanqui De la Cruz (U22229407)
- David Jair Mendoza Figueroa (U22226721)
- Aldair Ronaldo Casimiro Ramos (U23252961)

**Docente:** José Luis Milla Flores
**Universidad:** UTP

**Fecha de inicio y finalización:** 07/04/2026 – 25/05/2026

### Agradecimiento

Expresamos nuestro sincero agradecimiento al docente del curso por su orientación constante, sus valiosas recomendaciones técnicas y la retroalimentación brindada durante cada etapa del desarrollo del proyecto. Asimismo, agradecemos a la universidad por proporcionarnos el entorno académico, las salas de cómputo y los recursos necesarios para fortalecer nuestras competencias en la Ingeniería de Sistemas e Informática. Finalmente, extendemos nuestro agradecimiento a nuestras familias por su apoyo incondicional, comprensión y motivación permanente a lo largo de este proceso.

### Dedicatoria

Dedicamos este proyecto a nuestras familias, quienes han sido un pilar fundamental en nuestra formación. También lo dedicamos a nuestros compañeros y docentes, quienes contribuyeron con su conocimiento y colaboración al logro de este trabajo.

---

## Resumen y Abstract

### Resumen

UrbanFlow es un sistema integral de gestión de pedidos, ventas e inventario para una tienda de ropa, diseñado para automatizar procesos comerciales y mejorar el control de stock. El sistema se desarrolló con Spring Boot en el backend y MySQL como base de datos, usando una arquitectura en capas (Controller, Service, Repository) y una API REST para la comunicación entre cliente y servidor.

UrbanFlow permite gestionar productos, clientes, ventas y detalles de compra, además de procesar un flujo de checkout con cálculo automático de totales y validación de stock. Incluye autenticación para usuarios administrativos y clientes, integrando las operaciones de ventas con el control de inventario y ofreciendo trazabilidad en los movimientos de la tienda.

### Abstract

UrbanFlow is a complete order and inventory management system for a clothing store. It supports product, customer, sales, and checkout management with automatic total calculation and stock validation. The backend is built with Spring Boot and MySQL, following a layered architecture and providing RESTful APIs.

The application includes CRUD operations for products and customers, sales creation and checkout, and real-time inventory updates. It also implements authentication endpoints for administrative users and customers, enhancing operational efficiency, data consistency, and traceability.

---

## Instalación y ejecución local

### Requisitos previos

- Java 17+
- Maven (o usar el wrapper `mvnw` incluido)
- MySQL 8+

### Pasos

```bash
# 1. Clonar el repositorio
git clone https://github.com/Uriel4560/UrbanFlow.git
cd UrbanFlow

# 2. Crear la base de datos (ver urbanflow.sql)
mysql -u root -p < urbanflow.sql

# 3. Configurar credenciales de MySQL
# Editar src/main/resources/application.properties
# (ver también CONFIGURACION_MYSQL.md para el detalle paso a paso)

# 4. Ejecutar el backend
./mvnw spring-boot:run
```

El backend queda disponible en `http://localhost:8080`. El frontend (`/frontend`) puede abrirse directamente en el navegador o servirse con cualquier servidor estático.

También hay soporte para Docker (`Dockerfile` / `Dockerfile.multistage`) si prefieren levantar el proyecto en contenedor.

---

## Realidad Problemática y Justificación del proyecto

### Realidad Problemática

En la actualidad, muchas pequeñas y medianas empresas del sector retail, especialmente las tiendas de ropa, gestionan sus procesos mediante registros manuales o herramientas no integradas, como hojas de cálculo, cuadernos y aplicaciones separadas. Esta situación genera diversos problemas, entre ellos inconsistencias en los datos, pérdida de información, dificultad para conocer el stock real, errores en el cálculo de ventas y limitaciones al momento de generar reportes.

Asimismo, la falta de integración entre las ventas y el inventario dificulta la toma de decisiones oportunas, afectando directamente la rentabilidad del negocio. Por ello, surge la necesidad de implementar soluciones tecnológicas que permitan automatizar y centralizar la información de manera eficiente.

### Justificación

El desarrollo de UrbanFlow se justifica en la necesidad de digitalizar y optimizar los procesos internos de una tienda de ropa. Mediante el uso de herramientas modernas como Spring Boot y MySQL, se propone una solución escalable, segura y eficiente.

Este sistema permite reducir errores humanos, mejorar la precisión de los datos, automatizar el control de inventario y facilitar la gestión de pedidos. Asimismo, contribuye a la formación académica de los estudiantes al aplicar conocimientos de desarrollo de software, bases de datos y arquitectura de sistemas.

---

## Objetivo general y específicos

### Objetivo General

Desarrollar un sistema web completo de gestión de pedidos, inventario y clientes para una tienda de ropa, utilizando herramientas modernas que mejoren la eficiencia operativa, la calidad de los datos y la experiencia de usuario.

### Objetivos Específicos

- Diseñar un modelo de base de datos relacional que represente correctamente las entidades del sistema (productos, clientes, ventas y detalles de venta).
- Implementar operaciones CRUD para la gestión de productos, clientes y ventas.
- Desarrollar el módulo de checkout con cálculo automático de totales, validaciones de stock y registro de detalles de venta.
- Implementar control de inventario en tiempo real y actualización del stock tras cada venta.
- Integrar autenticación y registro de usuarios administrativos y clientes.
- Exponer funcionalidades mediante una API REST clara y consistente.
- Integrar el backend con la base de datos MySQL y con el frontend del sistema.
- Realizar pruebas funcionales, de integración y documentación técnica para validar el correcto funcionamiento del sistema.

---

## Marco teórico y Metodología

### Marco Teórico

El desarrollo del proyecto UrbanFlow se fundamenta en diversos conceptos clave de la Ingeniería de Software, los cuales permiten garantizar una solución eficiente, escalable y mantenible.

- **Spring Boot:** framework del ecosistema Java que facilita el desarrollo de aplicaciones web y servicios backend, reduciendo la configuración inicial y permitiendo la creación rápida de APIs REST robustas.
- **API REST:** estilo arquitectónico basado en el protocolo HTTP que permite la comunicación entre cliente y servidor mediante operaciones como GET, POST, PUT y DELETE, favoreciendo la interoperabilidad entre sistemas.
- **MySQL:** sistema de gestión de bases de datos relacional que permite almacenar, consultar y administrar grandes volúmenes de información estructurada de manera eficiente.
- **Arquitectura en capas:** modelo de diseño que divide la aplicación en diferentes niveles: presentación, lógica de negocio y acceso a datos, lo que mejora la organización del código, facilita el mantenimiento y permite la escalabilidad del sistema.
- **JPA/Hibernate:** tecnologías de mapeo objeto-relacional (ORM) que permiten convertir clases Java en tablas de base de datos, simplificando la persistencia y manipulación de datos.
- **Metodologías ágiles (Scrum):** marco de trabajo ágil que permite desarrollar software de forma iterativa e incremental, organizando el trabajo en periodos cortos llamados sprints. Facilita la colaboración del equipo, la adaptación a cambios y la entrega continua de funcionalidades.

### Metodología

Para el desarrollo del sistema se utilizó la metodología ágil Scrum, que permitió organizar el trabajo en iteraciones semanales hasta la semana 10 del proyecto. Se realizaron actividades de planificación, seguimiento y revisión de entregables para asegurar avance continuo y calidad en cada etapa.

El equipo estuvo conformado por cinco integrantes, quienes trabajaron de manera colaborativa, dividiendo las tareas según roles y responsabilidades para avanzar con eficiencia y control.

**Fases del desarrollo (Sprints)**

- **Sprint 1 (Semana 3):** análisis de requerimientos, definición de alcance y documentación inicial del proyecto.
- **Sprint 2 (Semana 4):** diseño de la base de datos, modelado de entidades y arquitectura del sistema.
- **Sprint 3 (Semana 5):** desarrollo del backend para productos y clientes, creación de controladores y servicios.
- **Sprint 4 (Semana 6):** implementación de ventas, detalles de venta, cálculo de totales y validación de stock.
- **Sprint 5 (Semana 7):** Extendible del proyecto general y documentación completa.

**Distribución del equipo**

- Integrante 1: Diseño y modelado de la base de datos
- Integrante 2: Desarrollo de controladores (API REST)
- Integrante 3: Implementación de la lógica de negocio (servicios)
- Integrante 4: Pruebas funcionales y validación del sistema
- Integrante 5: Documentación, apoyo general y control del proyecto

---

## Cronograma de actividades (diagrama de Gantt)

| Actividad | Semana 3 | Semana 4 | Semana 5 | Semana 6 |
|---|:---:|:---:|:---:|:---:|
| Análisis | ✔ | | | |
| Diseño BD | ✔ | ✔ | | |
| Desarrollo Backend | | ✔ | ✔ | |
| Pruebas | | | ✔ | ✔ |
| Documentación | | | ✔ | ✔ |
| Entrega | | | | ✔ |

---

## Desarrollo del proyecto (Aplicación y Base de datos)

### Arquitectura

El sistema sigue una arquitectura en capas:

- **Controller:** recibe y gestiona las solicitudes HTTP.
- **Service:** contiene la lógica de negocio.
- **Repository:** interactúa con la base de datos.

### API REST

Se implementaron endpoints para la gestión de recursos y la operativa de ventas:

```
GET    /api/productos
GET    /api/productos/{id}
POST   /api/productos
PUT    /api/productos/{id}
DELETE /api/productos/{id}

GET    /api/clientes
GET    /api/clientes/{id}
POST   /api/clientes
PUT    /api/clientes/{id}
DELETE /api/clientes/{id}

GET    /api/ventas
GET    /api/ventas/{id}
POST   /api/ventas
PUT    /api/ventas/{id}
DELETE /api/ventas/{id}
POST   /api/ventas/checkout

GET    /api/detalles-venta
GET    /api/detalles-venta/{id}
POST   /api/detalles-venta
PUT    /api/detalles-venta/{id}
DELETE /api/detalles-venta/{id}

POST   /api/auth/login
POST   /api/auth/cliente/login
POST   /api/auth/cliente/registro
```

### Base de Datos

Se diseñó una base de datos relacional con las siguientes tablas:

- **productos** (id, codigo, nombre, descripcion, categoria, marca, talla, color, precio, stock, stockMinimo, urlImagen, material, activo, created_at, updated_at)
- **clientes** (id, nombre, apellido, email, contraseña, telefono, cedula, ciudad, direccion, activo, created_at, updated_at)
- **ventas** (id, numeroVenta, cliente_id, total, estado, metodoPago, observaciones, fechaVenta, fechaEntrega)
- **detalles_venta** (id, venta_id, producto_id, cantidad, precioUnitario, subtotal)
- **usuarios** (id, nombre, email, usuario, contraseña, rol, activo, created_at)

---

## Resultados y Palabras clave

### Resultados

El sistema desarrollado cumple con los objetivos planteados, permitiendo gestionar productos, controlar el inventario y registrar pedidos de manera eficiente. Se logró reducir la complejidad de los procesos manuales y mejorar la organización de la información.

### Palabras clave

Sistema, Inventario, Pedidos, Spring Boot, MySQL, API REST, Desarrollo, Arquitectura en capas, Ropa.

---

## Documentación e informes

La documentación formal del proyecto (informes de avance, sustentos y presentaciones) está disponible en [`informes/`](./informes):

- `AP 1 - GRUPO 08.pdf` — Informe de avance 1
- `AP 1 - GRUPO 08 PPT.pdf` — Presentación de avance 1
- `AP 2 - GRUPO 08.pdf` — Informe de avance 2
- `AP 2 - GRUPO 08 PPT.pptx` — Presentación de avance 2
- `HITO3_DEVSECOPS_INFORME.md` — Informe DevSecOps (Hito 3)

Documentación técnica adicional en la raíz del repositorio: autenticación (`AUTENTICACION.md`), configuración de MySQL (`CONFIGURACION_MYSQL.md`), CI/CD (`IMPLEMENTACION_CI_PASO_A_PASO.md`).



