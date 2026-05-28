package com.ventas.ropa.sistema.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ventas.ropa.sistema.modelo.DetalleVenta;
import com.ventas.ropa.sistema.servicio.DetalleVentaServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/detalles-venta")
@Tag(name = "Detalles de Venta", description = "Gestión de detalles de ventas")
public class DetalleVentaControlador {

    @Autowired
    private DetalleVentaServicio detalleVentaServicio;

    @GetMapping
    @Operation(summary = "Obtener todos los detalles de venta", description = "Retorna la lista de todos los detalles de ventas")
    @ApiResponses(value = {

    })
    public List<DetalleVenta> obtenerTodos() {
        return detalleVentaServicio.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de venta por ID", description = "Retorna un detalle de venta específico")
    @ApiResponses(value = {

    })
    public ResponseEntity<DetalleVenta> obtenerPorId(@PathVariable Long id) {
        return detalleVentaServicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear detalle de venta", description = "Crea un nuevo detalle de venta")
    @ApiResponses(value = {

    })
    public DetalleVenta crear(@RequestBody DetalleVenta detalleVenta) {
        return detalleVentaServicio.guardar(detalleVenta);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar detalle de venta", description = "Actualiza un detalle de venta existente")
    @ApiResponses(value = {

        return detalleVentaServicio.obtenerPorId(id)
                .map(detalleVenta -> {
                    detalleVenta.setVenta(detalleVentaActualizado.getVenta());
                    detalleVenta.setProducto(detalleVentaActualizado.getProducto());
                    detalleVenta.setCantidad(detalleVentaActualizado.getCantidad());
                    detalleVenta.setPrecioUnitario(detalleVentaActualizado.getPrecioUnitario());
                    detalleVenta.setSubtotal(
                            detalleVentaActualizado.getCantidad() * detalleVentaActualizado.getPrecioUnitario());
                    DetalleVenta actualizado = detalleVentaServicio.guardar(detalleVenta);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar detalle de venta", description = "Elimina un detalle de venta del sistema")
    @ApiResponses(value = {
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (detalleVentaServicio.obtenerPorId(id).isPresent()) {
            detalleVentaServicio.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
