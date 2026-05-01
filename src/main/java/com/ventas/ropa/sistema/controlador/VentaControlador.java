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

import com.ventas.ropa.sistema.modelo.Venta;
import com.ventas.ropa.sistema.servicio.VentaServicio;

@RestController
@RequestMapping("/api/ventas")
public class VentaControlador {
    
    @Autowired
    private VentaServicio ventaServicio;
    
    @GetMapping
    public List<Venta> obtenerTodas() {
        return ventaServicio.obtenerTodas();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtenerPorId(@PathVariable Long id) {
        return ventaServicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Venta crear(@RequestBody Venta venta) {
        return ventaServicio.guardar(venta);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizar(@PathVariable Long id, @RequestBody Venta ventaActualizada) {
        return ventaServicio.obtenerPorId(id)
                .map(venta -> {
                    venta.setNumeroVenta(ventaActualizada.getNumeroVenta());
                    venta.setCliente(ventaActualizada.getCliente());
                    venta.setTotal(ventaActualizada.getTotal());
                    venta.setEstado(ventaActualizada.getEstado());
                    venta.setMetodoPago(ventaActualizada.getMetodoPago());
                    venta.setObservaciones(ventaActualizada.getObservaciones());
                    Venta actualizada = ventaServicio.guardar(venta);
                    return ResponseEntity.ok(actualizada);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (ventaServicio.obtenerPorId(id).isPresent()) {
            ventaServicio.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
