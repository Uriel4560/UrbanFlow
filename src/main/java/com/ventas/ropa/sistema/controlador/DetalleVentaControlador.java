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

@RestController
@RequestMapping("/api/detalles-venta")
public class DetalleVentaControlador {
    
    @Autowired
    private DetalleVentaServicio detalleVentaServicio;
    
    @GetMapping
    public List<DetalleVenta> obtenerTodos() {
        return detalleVentaServicio.obtenerTodos();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DetalleVenta> obtenerPorId(@PathVariable Long id) {
        return detalleVentaServicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public DetalleVenta crear(@RequestBody DetalleVenta detalleVenta) {
        return detalleVentaServicio.guardar(detalleVenta);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DetalleVenta> actualizar(@PathVariable Long id, @RequestBody DetalleVenta detalleVentaActualizado) {
        return detalleVentaServicio.obtenerPorId(id)
                .map(detalleVenta -> {
                    detalleVenta.setVenta(detalleVentaActualizado.getVenta());
                    detalleVenta.setProducto(detalleVentaActualizado.getProducto());
                    detalleVenta.setCantidad(detalleVentaActualizado.getCantidad());
                    detalleVenta.setPrecioUnitario(detalleVentaActualizado.getPrecioUnitario());
                    detalleVenta.setSubtotal(detalleVentaActualizado.getCantidad() * detalleVentaActualizado.getPrecioUnitario());
                    DetalleVenta actualizado = detalleVentaServicio.guardar(detalleVenta);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (detalleVentaServicio.obtenerPorId(id).isPresent()) {
            detalleVentaServicio.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
