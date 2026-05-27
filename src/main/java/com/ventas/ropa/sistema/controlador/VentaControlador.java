package com.ventas.ropa.sistema.controlador;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ventas.ropa.sistema.modelo.Cliente;
import com.ventas.ropa.sistema.modelo.DetalleVenta;
import com.ventas.ropa.sistema.modelo.Producto;
import com.ventas.ropa.sistema.modelo.Venta;
import com.ventas.ropa.sistema.servicio.ClienteServicio;
import com.ventas.ropa.sistema.servicio.DetalleVentaServicio;
import com.ventas.ropa.sistema.servicio.ProductoServicio;
import com.ventas.ropa.sistema.servicio.VentaServicio;

@RestController
@RequestMapping("/api/ventas")
public class VentaControlador {
    
    @Autowired
    private VentaServicio ventaServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    @Autowired
    private ProductoServicio productoServicio;

    @Autowired
    private DetalleVentaServicio detalleVentaServicio;

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

    @PostMapping("/checkout")
    @Transactional
    public ResponseEntity<?> checkout(@RequestBody Map<String, Object> datos) {
        Object clienteIdObj = datos.get("clienteId");
        Object totalObj = datos.get("total");
        Object metodoPagoObj = datos.get("metodoPago");
        Object itemsObj = datos.get("items");

        if (clienteIdObj == null || totalObj == null || itemsObj == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Faltan datos para procesar la compra"));
        }

        Long clienteId;
        Double total;
        try {
            clienteId = Long.valueOf(clienteIdObj.toString());
            total = Double.valueOf(totalObj.toString());
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Datos numéricos inválidos"));
        }

        Cliente cliente = clienteServicio.obtenerPorId(clienteId)
                .orElse(null);

        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Cliente no encontrado"));
        }

        if (!(itemsObj instanceof List<?> items)) {
            return ResponseEntity.badRequest().body(Map.of("error", "La lista de productos es inválida"));
        }

        if (items.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "El carrito está vacío"));
        }

        String numeroVenta = "VENTA-" + System.currentTimeMillis();
        String metodoPago = metodoPagoObj != null ? metodoPagoObj.toString() : "EFECTIVO";

        Venta venta = new Venta(numeroVenta, cliente, total, metodoPago);
        venta.setEstado("PENDIENTE");
        Venta ventaGuardada = ventaServicio.guardar(venta);

        for (Object itemObj : items) {
            if (!(itemObj instanceof Map<?, ?> itemMap)) {
                continue;
            }

            Object productoIdObj = itemMap.get("id");
            Object cantidadObj = itemMap.get("cantidad");
            Object precioObj = itemMap.get("precio");

            if (productoIdObj == null || cantidadObj == null || precioObj == null) {
                continue;
            }

            Long productoId;
            Integer cantidad;
            Double precioUnitario;

            try {
                productoId = Long.valueOf(productoIdObj.toString());
                cantidad = Integer.valueOf(cantidadObj.toString());
                precioUnitario = Double.valueOf(precioObj.toString());
            } catch (NumberFormatException e) {
                continue;
            }

            Producto producto = productoServicio.obtenerPorId(productoId)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));

            DetalleVenta detalleVenta = new DetalleVenta(ventaGuardada, producto, cantidad, precioUnitario);
            detalleVentaServicio.guardar(detalleVenta);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "success", true,
                "ventaId", ventaGuardada.getId(),
                "numeroVenta", ventaGuardada.getNumeroVenta(),
                "total", ventaGuardada.getTotal(),
                "mensaje", "Compra registrada correctamente"
        ));
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
