# Configuración de MySQL para Sistema de Ventas de Ropa

## Requisitos Previos
- MySQL Server 8.0 o superior instalado
- Cliente MySQL o herramienta similar (MySQL Workbench, DBeaver, etc.)

## Paso 1: Crear la Base de Datos

Ejecuta el siguiente comando en MySQL:

```sql
CREATE DATABASE sistema_ventas_ropa 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE sistema_ventas_ropa;
```

## Paso 2: Crear un Usuario (Opcional)

Si deseas usar un usuario específico en lugar de root:

```sql
CREATE USER 'ropa_user'@'localhost' IDENTIFIED BY 'tu_contraseña';
GRANT ALL PRIVILEGES ON sistema_ventas_ropa.* TO 'ropa_user'@'localhost';
FLUSH PRIVILEGES;
```

## Paso 3: Configurar en application.properties

La configuración ya está lista en el archivo `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sistema_ventas_ropa
spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver
spring.datasource.username=root          # Cambiar si usas otro usuario
spring.datasource.password=              # Agregar contraseña si la tienes
```

## Paso 4: Ejecutar la Aplicación

```bash
mvn spring-boot:run
```

La aplicación creará automáticamente las tablas y cargará datos de ejemplo.

## Paso 5: Acceder a los Endpoints

La API estará disponible en `http://localhost:8080`

### Productos
- GET    `/api/productos`           - Listar todos
- GET    `/api/productos/{id}`      - Obtener por ID
- POST   `/api/productos`           - Crear
- PUT    `/api/productos/{id}`      - Actualizar
- DELETE `/api/productos/{id}`      - Eliminar

### Clientes
- GET    `/api/clientes`            - Listar todos
- GET    `/api/clientes/{id}`       - Obtener por ID
- POST   `/api/clientes`            - Crear
- PUT    `/api/clientes/{id}`       - Actualizar
- DELETE `/api/clientes/{id}`       - Eliminar

### Ventas
- GET    `/api/ventas`              - Listar todas
- GET    `/api/ventas/{id}`         - Obtener por ID
- POST   `/api/ventas`              - Crear
- PUT    `/api/ventas/{id}`         - Actualizar
- DELETE `/api/ventas/{id}`         - Eliminar

### Detalles de Venta
- GET    `/api/detalles-venta`      - Listar todos
- GET    `/api/detalles-venta/{id}` - Obtener por ID
- POST   `/api/detalles-venta`      - Crear
- PUT    `/api/detalles-venta/{id}` - Actualizar
- DELETE `/api/detalles-venta/{id}` - Eliminar

## Tablas Creadas Automáticamente

1. **productos** - Inventario de prendas
   - Campos: id, codigo, nombre, descripcion, categoria, marca, talla, color, precio, stock, stock_minimo, url_imagen, material, activo, fecha_creacion, fecha_actualizacion

2. **clientes** - Información de clientes
   - Campos: id, nombre, apellido, email, telefono, cedula, ciudad, direccion, activo, fecha_creacion, fecha_actualizacion

3. **ventas** - Registro de ventas
   - Campos: id, numero_venta, cliente_id, total, estado, metodo_pago, observaciones, fecha_venta, fecha_entrega

4. **detalles_venta** - Detalle de cada venta
   - Campos: id, venta_id, producto_id, cantidad, precio_unitario, subtotal

## Ejemplo JSON para Crear Productos

```json
{
  "codigo": "CAMI002",
  "nombre": "Camiseta Deportiva",
  "descripcion": "Camiseta de material transpirable",
  "categoria": "Camisetas",
  "marca": "SportBrand",
  "talla": "L",
  "color": "Rojo",
  "precio": 39.99,
  "stock": 25,
  "material": "Poliéster"
}
```

## Ejemplo JSON para Crear Clientes

```json
{
  "nombre": "Pedro",
  "apellido": "Rodríguez",
  "email": "pedro@example.com",
  "telefono": "3105555555",
  "cedula": "5555666777",
  "ciudad": "Barranquilla",
  "direccion": "Calle 50 #80-90"
}
```

## Resolución de Problemas

### Error: "Access denied for user 'root'@'localhost'"
- Verifica que MySQL esté ejecutándose
- Comprueba la contraseña en application.properties

### Error: "Unknown database 'sistema_ventas_ropa'"
- Asegúrate de haber ejecutado el comando CREATE DATABASE

### Error: "Communications link failure"
- Verifica que MySQL esté activo en tu servidor local
- Comprueba la URL de conexión
