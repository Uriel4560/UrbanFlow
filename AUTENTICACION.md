# 🔐 Autenticación de Admin - UrbanFlow

## ✅ Cambios Realizados

### 1. Seguridad de Contraseñas (BCrypt)
- ✅ Agregada dependencia `spring-security-crypto` en `pom.xml`
- ✅ Implementado `BCryptPasswordEncoder` en `UsuarioServicio`
- ✅ Las contraseñas se encriptan automáticamente
- ✅ Validación segura de credenciales

### 2. Usuarios de Prueba
En `DataInitializer.java`, se cargan automáticamente 3 usuarios:

| Usuario | Contraseña | Rol |
|---------|-----------|-----|
| `admin` | `admin123` | ADMIN |
| `vendedor` | `vendedor123` | VENDEDOR |
| `gerente` | `gerente123` | GERENTE |

### 3. Panel de Login
- ✅ Interfaz profesional y responsiva
- ✅ Animaciones suaves (fade in/shake en errores)
- ✅ Gradiente moderno (morado/azul)
- ✅ Información de demostración para usuarios

### 4. Sesión de Usuario
- ✅ Token guardado en `localStorage`
- ✅ Recuperación automática de sesión al recargar
- ✅ Botón de "Cerrar Sesión"
- ✅ Información del usuario visible en header

### 5. Estilos Nuevos
- Archivo: `frontend/styles-admin.css`
- Login page con diseño moderno
- Panel admin con navegación por tabs
- Notificaciones (éxito, error, info)
- Completamente responsivo

## 🚀 Cómo Usar

### En Desarrollo
1. Inicia la aplicación: `./mvnw.cmd spring-boot:run`
2. Accede al admin: http://localhost:8080/static/admin.html
3. Usa cualquiera de las credenciales de prueba

### En Producción
1. Cambiar credenciales de prueba en `DataInitializer.java`
2. Usar BCrypt para encriptar nuevas contraseñas
3. Implementar session tokens/JWT adicionales

## 📝 Estructura

### Backend
```
UsuarioControlador.java    → /api/auth/login
UsuarioServicio.java       → Validación segura
Usuario.java               → Modelo con campos
UsuarioRepository.java     → Acceso a BD
DataInitializer.java       → Datos de inicio
```

### Frontend
```
admin.html                 → Interfaz con Vue.js
app-admin.js               → Lógica de login y admin
styles-admin.css           → Estilos profesionales
```

## 🔒 Seguridad

- ✅ Contraseñas encriptadas con BCrypt (10 rondas)
- ✅ Validación en servidor (no solo cliente)
- ✅ Usuario activo verificado antes de login
- ✅ Mensajes de error genéricos (sin revelar usuarios)

## 📱 Responsivo

- ✅ Desktop (1200px+)
- ✅ Tablet (768px - 1199px)
- ✅ Mobile (< 768px)

## 🔄 Próximos Pasos

1. Integración de pagos (Stripe, PayPal)
2. Roles y permisos avanzados
3. Autenticación con JWT/Token
4. Recuperación de contraseña
5. Autenticación de dos factores (2FA)
