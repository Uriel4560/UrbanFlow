# 🛍️ FashionHub - E-commerce de Ropa Premium

## ¿Qué es FashionHub?

Una plataforma moderna e innovadora de e-commerce para ropa de moda premium dirigida a hombres y mujeres adultos. Inspirada en el diseño de Shein pero con una propuesta única y elegante.

## 🎯 Características Principales

### Para Clientes:
✅ **Catálogo Moderno** - Navegación intuitiva y visualmente atractiva
✅ **Sistema de Filtros Avanzados** - Por género, precio, talla, color
✅ **Búsqueda en Tiempo Real** - Encuentra lo que necesitas rápidamente
✅ **Carrito Inteligente** - Gestiona tu compra con facilidad
✅ **Cálculo Automático** - Envío, impuestos y total
✅ **Producto Destacados** - Lo más popular en inicio
✅ **Categorías** - Organización clara (Hombres/Mujeres/Ofertas)

### Para Administradores:
✅ **Panel de Control** - Gestiona productos, clientes y ventas
✅ **Agregar Productos** - Formulario fácil de usar
✅ **Ver Estadísticas** - Historial de ventas
✅ **Administrar Clientes** - Base de datos completa

## 📦 Stack Tecnológico

- **Backend**: Spring Boot 4.0.6 (Java 21)
- **Base de Datos**: MySQL 8.0
- **Frontend**: Vue.js 3 (CDN)
- **Estilos**: CSS3 Moderno con Responsive Design
- **API**: REST con CORS

## 🚀 Cómo Usar

### 1. Asegurate que el Backend esté corriendo:

```bash
# En la carpeta del proyecto (c:\Users\leo45\OneDrive\Escritorio\sistema)
mvn spring-boot:run
```

El backend debe estar en `http://localhost:8080`

### 2. Abre el E-commerce:

Opción A - Desde VS Code:
- Abre el archivo `frontend/ecommerce.html`
- Click derecho → "Open with Live Server"

Opción B - Directamente en navegador:
- Abre `file:///c:/Users/leo45/OneDrive/Escritorio/sistema/frontend/ecommerce.html`

### 3. Navega por FashionHub:

- **INICIO** - Página principal con categorías destacadas y productos populares
- **CATÁLOGO** - Vista completa con filtros avanzados
- **CARRITO** - Gestiona tu compra (ver productos, cambiar cantidades, checkout)
- **ADMIN** - Panel de administración para gestionar el inventario

## 🎨 Diseño & UX

### Características Innovadoras:

1. **Hero Section** - Sección principal impactante con CTA
2. **Categorías Visual** - Tarjetas con gradientes para hombres/mujeres/ofertas
3. **Productos Grid** - Layout responsivo con hover efectos
4. **Sidebar Filtros** - Sticky sidebar para mejor UX
5. **Carrito Modal** - Gestión fácil de compras
6. **Admin Panel** - Interfaz profesional para administradores

### Colores & Branding:

- **Primario**: Negro (#000) - Elegancia y profesionalismo
- **Acento**: Rojo (#ff6b6b) - Llamadas a acción y destacados
- **Fondo**: Blanco (#fff) - Claridad y limpieza
- **Gris Neutral**: Para elementos secundarios

### Responsive Design:

- ✅ Desktop (1400px+)
- ✅ Tablet (768px - 1399px)
- ✅ Mobile (480px - 767px)
- ✅ Extra pequeño (<480px)

## 📱 Secciones Detalladas

### Inicio (Home)
- Hero banner con CTA
- Categorías destacadas (Hombres, Mujeres, Ofertas)
- 4 productos destacados
- Características de la tienda (Envío gratis, devoluciones, seguridad)

### Catálogo
- **Sidebar Filtros**:
  - Género (Todos, Hombres, Mujeres)
  - Rango de precio (0-$500)
  - Tallas (XS, S, M, L, XL, XXL)
  
- **Productos Grid**:
  - Tarjetas con imagen placeholder
  - Ratings y reseñas
  - Botón de vista rápida
  - Ordenamiento (Nuevo, Precio asc/desc, Nombre)

### Carrito
- Lista de productos con cantidades
- Actualizar cantidades in-situ
- Eliminar productos
- **Resumen**:
  - Subtotal
  - Costo de envío (gratis >$50)
  - Impuesto (8%)
  - Total
  - Proceder al pago

### Admin Panel
- **Productos**: Agregar, eliminar, ver lista completa
- **Clientes**: Ver lista de clientes registrados
- **Ventas**: Historial de transacciones

## 🔄 Flujo de Datos

```
API Backend (Spring Boot)
         ↓
   Vue.js Application
         ↓
     UI/UX Moderna
         ↓
   Carrito & Checkout
```

## 📊 Entidades Utilizadas

### Productos
- Código, Nombre, Descripción
- Categoría (Hombres/Mujeres)
- Marca, Talla, Color
- Precio, Stock
- Material, URL Imagen

### Clientes
- Nombre, Apellido
- Email, Teléfono
- Cédula, Ciudad
- Dirección

### Ventas
- Número de venta
- Cliente asociado
- Total, Estado
- Método de pago
- Fecha

### Detalles Venta
- Producto vendido
- Cantidad, Precio unitario
- Subtotal

## 💡 Tips & Tricks

1. **Búsqueda Rápida** - Usa la barra de búsqueda en el header
2. **Filtros Múltiples** - Combina filtros para resultados precisos
3. **Envío Gratis** - Gasta más de $50 para envío sin costo
4. **Admin Panel** - Agrega productos con código AUTO-generado
5. **Notificaciones** - Verás confirmaciones al agregar productos

## 🐛 Troubleshooting

### "No se cargan los productos"
- ✓ Asegúrate que el backend esté corriendo (`mvn spring-boot:run`)
- ✓ Verifica que MySQL esté activo
- ✓ Abre la consola del navegador (F12) para ver errores

### "Los estilos no se aplican"
- ✓ Recarga la página (Ctrl+F5)
- ✓ Verifica que `styles-ecommerce.css` esté en la carpeta `/frontend/`

### "El carrito no se actualiza"
- ✓ Abre la consola del navegador (F12)
- ✓ Verifica que Vue.js esté cargado correctamente

## 📝 Archivos del Proyecto

```
frontend/
├── ecommerce.html          # Estructura principal HTML
├── styles-ecommerce.css    # Estilos CSS modernos
├── app-ecommerce.js        # Lógica Vue.js
└── README.md              # Este archivo
```

## 🎓 Aprendizajes Clave

Este proyecto demuestra:
- ✅ Arquitectura moderna de e-commerce
- ✅ Integración Frontend-Backend REST
- ✅ Gestión de estado en Vue.js
- ✅ Responsive Design y Mobile-First
- ✅ UX/UI Profesional
- ✅ Panel Administrativo Funcional
- ✅ Carrito de compras completo

## 🔮 Mejoras Futuras

- [ ] Autenticación de usuarios
- [ ] Sistema de reviews y ratings reales
- [ ] Imágenes de productos dinámicas
- [ ] Wishlist / Favoritos
- [ ] Métodos de pago integrados (Stripe, PayPal)
- [ ] Tracking de pedidos
- [ ] Recomendaciones personalizadas
- [ ] Newsletter/Email
- [ ] Sistema de cupones/descuentos
- [ ] Chat en vivo con soporte

## 📞 Soporte

Para reportar bugs o sugerencias, verifica:
1. La consola del navegador (F12)
2. El backend está corriendo
3. MySQL está activo

---

**¡Gracias por usar FashionHub!** 🎉

Desarrollado con ❤️ para ofrecerte la mejor experiencia de compra de moda.
