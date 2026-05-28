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

import com.ventas.ropa.sistema.modelo.Cliente;
import com.ventas.ropa.sistema.servicio.ClienteServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Gestión de clientes")
public class ClienteControlador {

    @Autowired
    private ClienteServicio clienteServicio;

    @GetMapping
    @Operation(summary = "Obtener todos los clientes", description = "Retorna la lista completa de clientes registrados")
    @ApiResponses(value = {

    })
    public List<Cliente> obtenerTodos() {
        return clienteServicio.obtenerTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cliente por ID", description = "Retorna un cliente específico según su ID")
    @ApiResponses(value = {

    })
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
        return clienteServicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear nuevo cliente", description = "Crea un nuevo registro de cliente en el sistema")
    @ApiResponses(value = {

    })
    public Cliente crear(@RequestBody Cliente cliente) {
        return clienteServicio.guardar(cliente);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cliente", description = "Actualiza los datos de un cliente existente")
    @ApiResponses(value = {

    })
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @RequestBody Cliente clienteActualizado) {
        return clienteServicio.obtenerPorId(id)
                .map(cliente -> {
                    cliente.setNombre(clienteActualizado.getNombre());
                    cliente.setApellido(clienteActualizado.getApellido());
                    cliente.setEmail(clienteActualizado.getEmail());
                    cliente.setTelefono(clienteActualizado.getTelefono());
                    cliente.setCedula(clienteActualizado.getCedula());
                    cliente.setCiudad(clienteActualizado.getCiudad());
                    cliente.setDireccion(clienteActualizado.getDireccion());
                    Cliente actualizado = clienteServicio.guardar(cliente);
                    return ResponseEntity.ok(actualizado);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente del sistema")
    @ApiResponses(value = {

    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (clienteServicio.obtenerPorId(id).isPresent()) {
            clienteServicio.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
