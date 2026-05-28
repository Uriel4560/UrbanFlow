package com.ventas.ropa.sistema.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ventas.ropa.sistema.modelo.Producto;
import com.ventas.ropa.sistema.servicio.ProductoServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/productos")
@Tag(name = "Productos", description = "Gestión de productos del catálogo")
public class ProductoControlador {

    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Retorna la lista completa de productos disponibles")
    @ApiResponses(value = {

    })
    public List<Producto> obtenerTodos() {
        return productoServicio.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener producto por ID", description = "Retorna un producto específico según su ID")
    @ApiResponses(value = {

    })
    public ResponseEntity<Producto> obtenerPorId(@PathVariable Long id) {
        return productoServicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo producto", description = "Crea un nuevo producto en el sistema con validación de campos")
    @ApiResponses(value = {

    })
    public ResponseEntity<?> crear(@RequestBody Producto producto) {
        try {
            // Validar campos requeridos
            if (producto.getCodigo() == null || producto.getCodigo().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El código es requerido");
            }
            if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El nombre es requerido");
            }
            if (producto.getCategoria() == null || producto.getCategoria().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La categoría es requerida");
            }
            if (producto.getTalla() == null || producto.getTalla().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La talla es requerida");
            }
            if (producto.getColor() == null || producto.getColor().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El color es requerido");
            }
            if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
                return ResponseEntity.badRequest().body("El precio debe ser mayor a 0");
            }
            if (producto.getStock() == null || producto.getStock() < 0) {
                return ResponseEntity.badRequest().body("El stock no puede ser negativo");
            }

            Producto nuevoProducto = productoServicio.guardar(producto);
            return ResponseEntity.ok(nuevoProducto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza los datos de un producto existente")
    @ApiResponses(value = {

    })
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        return productoServicio.obtenerPorId(id)
                .map(producto -> {
                    producto.setNombre(productoActualizado.getNombre());
                    producto.setDescripcion(productoActualizado.getDescripcion());
                    producto.setTalla(productoActualizado.getTalla());
                    producto.setColor(productoActualizado.getColor());
                    producto.setPrecio(productoActualizado.getPrecio());
                    producto.setStock(productoActualizado.getStock());
                    Producto actualizado = productoServicio.guardar(producto);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto del sistema")
    @ApiResponses(value = {

    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (productoServicio.obtenerPorId(id).isPresent()) {
            productoServicio.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
