# 🛍️ Login de Clientes - UrbanFlow E-commerce

## ✅ Cambios Realizados

### 1. Backend - Autenticación de Clientes

#### Modelo Cliente Actualizado
- ✅ Agregado campo `contraseña` (nullable)
- ✅ Getter/Setter para contraseña
- ✅ Campo email único para login

#### ClienteRepository
- ✅ Nuevo método: `findByEmail(String email)`

#### ClienteServicio
- ✅ Método `obtenerPorEmail(email)`
- ✅ Método `validarCredenciales(email, contraseña)` con BCrypt

#### UsuarioControlador - Nuevos Endpoints
1. **POST /api/auth/cliente/login**
   - Request: `{ email, contraseña }`
   - Response: `{ success, id, nombre, email, telefono }`
   - Status: 200 (éxito) o 401 (error)

2. **POST /api/auth/cliente/registro**
   - Request: `{ nombre, email, telefono, contraseña }`
   - Response: `{ success, id, nombre, email, mensaje }`
   - Status: 201 (creado) o 400 (error)
   - Valida contraseñas únicas y campos requeridos

### 2. Frontend - E-commerce

#### ecommerce.html
- ✅ Login page profesional para clientes
- ✅ Formulario de registro con validación
- ✅ Toggle entre login/registro
- ✅ Mostrar nombre de cliente en header
- ✅ Botón de "Salir" (logout)
- ✅ Interfaz responsiva

#### app-ecommerce.js
- ✅ Estado `autenticado` (boolean)
- ✅ Método `login()` - autentica con email/contraseña
- ✅ Método `registro()` - registra nuevo cliente
- ✅ Método `logout()` - cierra sesión
- ✅ Método `verificarSesion()` - restaura sesión desde localStorage
- ✅ Compra requiere autenticación (validación)

#### styles-ecommerce.css
- ✅ Estilos para `.user-menu`
- ✅ Estilos para `.btn-logout`
- ✅ Estilos para `.auth-toggle`
- ✅ Interfaz moderna con gradientes

## 🔄 Flujo de Uso

### 1. Nuevo Cliente
```
1. Accede a http://localhost:8080/static/ecommerce.html
2. Ve la pantalla de login
3. Hace clic en "Regístrate aquí"
4. Completa formulario de registro
5. Sistema envía POST /api/auth/cliente/registro
6. Se redirige a login automáticamente
7. Inicia sesión con sus credenciales
```

### 2. Cliente Existente
```
1. Accede a ecommerce.html
2. Completa login con email/contraseña
3. Sistema envía POST /api/auth/cliente/login
4. Se guarda en localStorage
5. Ve la tienda normalmente
6. Puede agregar productos al carrito
7. Para comprar, debe estar autenticado
8. Botón "Salir" para logout
```

## 🔒 Seguridad

- ✅ Contraseñas encriptadas con BCrypt (10 rondas)
- ✅ Email único por cliente
- ✅ Validación en servidor (no solo cliente)
- ✅ Sesión en localStorage
- ✅ Mensajes de error genéricos
- ✅ Validación de campos requeridos

## 📊 Base de Datos

### Tabla `clientes` (actualizada)
```sql
- id (PK)
- nombre
- apellido
- email (UNIQUE)
- contraseña (NULL para clientes sin login)
- telefono
- cedula
- ciudad
- direccion
- activo
- fecha_creacion
- fecha_actualizacion
```

## 📱 Responsivo

- ✅ Login adaptado a mobile, tablet, desktop
- ✅ Header con usuario y logout
- ✅ Carrito funcional en todas las resoluciones
- ✅ Compra solo para usuarios autenticados

## 🧪 Prueba Rápida

1. Ir a http://localhost:8080/static/ecommerce.html
2. Hacer clic en "Regístrate"
3. Llenar formulario:
   - Nombre: Tu Nombre
   - Email: tu@email.com
   - Teléfono: 987654321
   - Contraseña: micontraseña123
4. Hacer clic en "Registrarse"
5. Inicia sesión con tu@email.com / micontraseña123
6. ¡Listo! Puedes comprar en la tienda

## 🚀 Próximos Pasos

1. ✅ Autenticación de clientes
2. Integración de pagos (Stripe, PayPal)
3. Órdenes/Historial de compras
4. Gestión de direcciones
5. Recuperación de contraseña
