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

@RestController
@RequestMapping("/api/clientes")
public class ClienteControlador {
    
    @Autowired
    private ClienteServicio clienteServicio;
    
    @GetMapping
    public List<Cliente> obtenerTodos() {
        return clienteServicio.obtenerTodos();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id) {
        return clienteServicio.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping
    public Cliente crear(@RequestBody Cliente cliente) {
        return clienteServicio.guardar(cliente);
    }
    
    @PutMapping("/{id}")
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
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (clienteServicio.obtenerPorId(id).isPresent()) {
            clienteServicio.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
