-- ============================================
-- URBANFLOW - Base de Datos MySQL
-- ============================================

-- Crear base de datos
CREATE DATABASE IF NOT EXISTS urbanflow;
USE urbanflow;

-- Tabla: Usuarios (para admin login)
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    usuario VARCHAR(100) UNIQUE NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    rol VARCHAR(50) DEFAULT 'ADMIN',
    activo BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: Productos
CREATE TABLE IF NOT EXISTS productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(100) UNIQUE NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    categoria VARCHAR(100),
    marca VARCHAR(100),
    talla VARCHAR(50),
    color VARCHAR(50),
    precio DECIMAL(10, 2) NOT NULL,
    stock INT DEFAULT 0,
    material VARCHAR(100),
    url_imagen VARCHAR(500),
    activo BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: Clientes
CREATE TABLE IF NOT EXISTS clientes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    cedula VARCHAR(50) UNIQUE,
    ciudad VARCHAR(100),
    direccion VARCHAR(255),
    activo BOOLEAN DEFAULT true,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: Ventas
CREATE TABLE IF NOT EXISTS ventas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_venta VARCHAR(100) UNIQUE NOT NULL,
    cliente_id BIGINT NOT NULL,
    total DECIMAL(15, 2) NOT NULL,
    estado VARCHAR(50) DEFAULT 'PENDIENTE',
    metodo_pago VARCHAR(50),
    observaciones TEXT,
    fecha_venta TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (cliente_id) REFERENCES clientes(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: Detalles de Venta
CREATE TABLE IF NOT EXISTS detalles_venta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    venta_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario DECIMAL(10, 2) NOT NULL,
    subtotal DECIMAL(15, 2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (venta_id) REFERENCES ventas(id) ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Índices para mejorar performance
CREATE INDEX idx_productos_categoria ON productos(categoria);
CREATE INDEX idx_productos_codigo ON productos(codigo);
CREATE INDEX idx_clientes_email ON clientes(email);
CREATE INDEX idx_ventas_numero ON ventas(numero_venta);
CREATE INDEX idx_ventas_cliente ON ventas(cliente_id);
CREATE INDEX idx_detalles_venta ON detalles_venta(venta_id);

-- ============================================
-- DATOS DE PRUEBA
-- ============================================

-- Insertar usuario admin de ejemplo (usuario: admin, contraseña: admin123)
INSERT INTO usuarios (nombre, email, usuario, contraseña, rol, activo) VALUES
('Administrador UrbanFlow', 'admin@urbanflow.com', 'admin', 'admin123', 'ADMIN', true);

-- Insertar productos de ejemplo (Precios en Soles)
INSERT INTO productos (codigo, nombre, descripcion, categoria, marca, talla, color, precio, stock, material, activo) VALUES
('URB001', 'Camiseta Básica Premium', 'Camiseta 100% algodón, cómoda y versátil', 'Hombres', 'UrbanFlow', 'M', 'Negro', 65.00, 50, 'Algodón', true),
('URB002', 'Pantalón Skinny Fit', 'Pantalón moderno con fit ceñido', 'Hombres', 'UrbanFlow', 'L', 'Azul', 150.00, 30, 'Algodón/Poliéster', true),
('URB003', 'Sudadera Hoodie', 'Sudadera cómoda para uso diario', 'Hombres', 'UrbanFlow', 'M', 'Gris', 120.00, 25, 'Algodón/Poliéster', true),
('URB004', 'Vestido Elegante', 'Vestido casual elegante para todo tipo de ocasión', 'Mujeres', 'UrbanFlow', 'S', 'Rojo', 180.00, 15, 'Poliéster', true),
('URB005', 'Blusa de Seda', 'Blusa fina y elegante de seda natural', 'Mujeres', 'UrbanFlow', 'M', 'Blanco', 200.00, 20, 'Seda', true),
('URB006', 'Shorts Deportivos', 'Shorts cómodos para entrenar', 'Mujeres', 'UrbanFlow', 'M', 'Negro', 95.00, 40, 'Poliéster', true);

-- Insertar clientes de ejemplo
INSERT INTO clientes (nombre, apellido, email, telefono, cedula, ciudad, direccion, activo) VALUES
('Juan', 'Pérez', 'juan.perez@email.com', '+51 912 345 678', '12345678', 'Lima', 'Avenida Principal 123, San Isidro', true),
('María', 'García', 'maria.garcia@email.com', '+51 913 456 789', '87654321', 'Cusco', 'Calle Central 456, Centro', true),
('Carlos', 'López', 'carlos.lopez@email.com', '+51 914 567 890', '11223344', 'Arequipa', 'Plaza Mayor 789, Cercado', true),
('Ana', 'Martínez', 'ana.martinez@email.com', '+51 915 678 901', '55667788', 'Trujillo', 'Calle del Comercio 321', true),
('Luis', 'Rodríguez', 'luis.rodriguez@email.com', '+51 916 789 012', '99887766', 'Iquitos', 'Paseo del Río 654', true);

-- Insertar ventas de ejemplo
INSERT INTO ventas (numero_venta, cliente_id, total, estado, metodo_pago, observaciones) VALUES
('VENTA-2024-001', 1, 399.00, 'COMPLETADO', 'Tarjeta Crédito', 'Primera compra'),
('VENTA-2024-002', 2, 799.50, 'COMPLETADO', 'Transferencia', 'Cliente VIP'),
('VENTA-2024-003', 3, 245.00, 'PENDIENTE', 'Tarjeta Débito', 'Pendiente de envío');

-- Insertar detalles de venta
INSERT INTO detalles_venta (venta_id, producto_id, cantidad, precio_unitario, subtotal) VALUES
(1, 1, 2, 65.00, 130.00),
(1, 2, 1, 150.00, 150.00),
(1, 3, 1, 120.00, 120.00),
(2, 4, 2, 180.00, 360.00),
(2, 5, 1, 200.00, 200.00),
(2, 6, 2, 95.00, 190.00),
(3, 1, 3, 65.00, 195.00);

-- ============================================
-- Fin del script de UrbanFlow
-- ============================================
SELECT * FROM productos;
SELECT * FROM clientes;