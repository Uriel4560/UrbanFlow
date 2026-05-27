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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/ventas")
@Tag(name = "Ventas", description = "Gestión de ventas y checkout")
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
    @Operation(summary = "Obtener todas las ventas", description = "Retorna la lista completa de ventas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ventas obtenida exitosamente")
    })
    public List<Venta> obtenerTodas() {
        return ventaServicio.obtenerTodas();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener venta por ID", description = "Retorna una venta específica según su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta encontrada"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    public ResponseEntity<Venta> obtenerPorId(@PathVariable Long id) {
        return ventaServicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear venta", description = "Crea una nueva venta en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta creada exitosamente")
    })
    public Venta crear(@RequestBody Venta venta) {
        return ventaServicio.guardar(venta);
    }

    @PostMapping("/checkout")
    @Transactional
    @Operation(summary = "Procesar checkout", description = "Procesa el checkout con validación de datos, stock y creación de detalles de venta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Compra procesada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o carrito vacío"),
            @ApiResponse(responseCode = "404", description = "Cliente o producto no encontrado")
    })
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
                "mensaje", "Compra registrada correctamente"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar venta", description = "Actualiza los datos de una venta existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta actualizada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
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
    @Operation(summary = "Eliminar venta", description = "Elimina una venta del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Venta eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (ventaServicio.obtenerPorId(id).isPresent()) {
            ventaServicio.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
