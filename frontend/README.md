# Frontend - Sistema de Ventas de Ropa

## Descripción
Frontend interactivo con Vue.js 3 para gestionar el sistema de ventas de ropa.

## Características
✅ **Dashboard** - Estadísticas rápidas
✅ **Gestión de Productos** - CRUD completo
✅ **Gestión de Clientes** - Agregar y eliminar clientes
✅ **Historial de Ventas** - Ver todas las ventas registradas
✅ **Interfaz Responsive** - Funciona en dispositivos móviles
✅ **Sin dependencias externas** - Solo Vue.js CDN

## Requisitos Previos
1. Backend Spring Boot ejecutándose en `http://localhost:8080`
2. API REST disponible:
   - GET/POST/DELETE `/api/productos`
   - GET/POST/DELETE `/api/clientes`
   - GET/POST/DELETE `/api/ventas`

## Instalación

### Opción 1: Abrir archivo HTML directamente
```bash
# Abre el archivo index.html en tu navegador
# El archivo está ubicado en: frontend/index.html
```

### Opción 2: Usar un servidor local (Recomendado)
```bash
# Si tienes Python 3
cd frontend
python -m http.server 8000

# O si tienes Node.js
npx http-server -p 8000
```

Luego accede a: `http://localhost:8000`

## Uso

### Dashboard
- Muestra estadísticas rápidas
- Productos totales
- Clientes registrados
- Ventas realizadas

### Productos
1. Completa el formulario con:
   - Código único
   - Nombre
   - Categoría
   - Precio
   - Stock
   - Talla (S, M, L, XL)
   - Color
2. Haz clic en "Agregar"
3. Ver tabla con todos los productos
4. Eliminar productos individuales

### Clientes
1. Completa el formulario con:
   - Nombre (requerido)
   - Apellido (opcional)
   - Email (requerido)
   - Teléfono (requerido)
   - Cédula (opcional)
   - Ciudad (opcional)
2. Haz clic en "Agregar Cliente"
3. Ver tabla con todos los clientes
4. Eliminar clientes individuales

### Ventas
- Muestra todas las ventas registradas
- Información de cliente
- Total de venta
- Estado de la venta
- Método de pago
- Fecha y hora

## Estructura de Archivos
```
frontend/
├── index.html       # Página principal con estructura HTML
├── styles.css       # Estilos CSS
├── app.js          # Lógica de Vue.js
└── README.md       # Este archivo
```

## CORS
El frontend se comunica con el backend. Si ves errores de CORS, agrega esta anotación en tu controlador Spring Boot:

```java
@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "http://localhost:8000", allowedHeaders = "*")
public class ProductoControlador {
    // ...
}
```

## Notas
- El frontend se conecta a `http://localhost:8080/api`
- Los datos se cargan automáticamente al cambiar de sección
- Los formularios se limpian después de agregar un registro
- Se piden confirmaciones antes de eliminar registros

## Tecnologías Usadas
- Vue.js 3 (CDN)
- CSS3 con Grid y Flexbox
- Fetch API para HTTP
- JavaScript ES6+

## Próximas Mejoras
- [ ] Editar productos/clientes
- [ ] Crear nuevas ventas
- [ ] Agregar detalles de venta
- [ ] Reportes y gráficos
- [ ] Búsqueda y filtros
- [ ] Validación avanzada de formularios
