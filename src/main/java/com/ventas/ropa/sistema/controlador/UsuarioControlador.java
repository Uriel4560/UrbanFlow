package com.ventas.ropa.sistema.controlador;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ventas.ropa.sistema.modelo.Cliente;
import com.ventas.ropa.sistema.modelo.Usuario;
import com.ventas.ropa.sistema.servicio.ClienteServicio;
import com.ventas.ropa.sistema.servicio.UsuarioServicio;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Endpoints de login y registro")
public class UsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private ClienteServicio clienteServicio;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/login")
    @Operation(summary = "Login de usuario administrativo", description = "Autentica un usuario del sistema (admin, vendedor, gerente)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso"),
            @ApiResponse(responseCode = "400", description = "Usuario o contraseña vacíos"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String usuario = credenciales.get("usuario");
        String contraseña = credenciales.get("contraseña");

        if (usuario == null || usuario.trim().isEmpty() ||
                contraseña == null || contraseña.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Usuario y contraseña son requeridos"));
        }

        if (usuarioServicio.validarCredenciales(usuario, contraseña)) {
            Usuario u = usuarioServicio.obtenerPorUsuario(usuario).get();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("id", u.getId());
            response.put("nombre", u.getNombre());
            response.put("usuario", u.getUsuario());
            response.put("rol", u.getRol());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Usuario o contraseña incorrectos"));
    }

    // ===== LOGIN CLIENTE =====
    @PostMapping("/cliente/login")
    @Operation(summary = "Login de cliente", description = "Autentica un cliente registrado en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso"),
            @ApiResponse(responseCode = "400", description = "Email o contraseña vacíos"),
            @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    })
    public ResponseEntity<?> loginCliente(@RequestBody Map<String, String> credenciales) {
        String email = credenciales.get("email");
        String contraseña = credenciales.get("contraseña");

        if (email == null || email.trim().isEmpty() ||
                contraseña == null || contraseña.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Email y contraseña son requeridos"));
        }

        if (clienteServicio.validarCredenciales(email, contraseña)) {
            Cliente c = clienteServicio.obtenerPorEmail(email).get();
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("id", c.getId());
            response.put("nombre", c.getNombre());
            response.put("email", c.getEmail());
            response.put("telefono", c.getTelefono());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Email o contraseña incorrectos"));
    }

    // ===== REGISTRO CLIENTE =====
    @PostMapping("/cliente/registro")
    @Operation(summary = "Registro de nuevo cliente", description = "Crea una nueva cuenta de cliente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente registrado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Campos incompletos o email ya registrado")
    })
    public ResponseEntity<?> registroCliente(@RequestBody Map<String, String> datos) {
        String nombre = datos.get("nombre");
        String email = datos.get("email");
        String contraseña = datos.get("contraseña");
        String telefono = datos.get("telefono");
        String ciudad = datos.get("ciudad");

        if (nombre == null || nombre.trim().isEmpty() ||
                email == null || email.trim().isEmpty() ||
                contraseña == null || contraseña.trim().isEmpty() ||
                telefono == null || telefono.trim().isEmpty() ||
                ciudad == null || ciudad.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "Todos los campos son requeridos"));
        }

        if (clienteServicio.obtenerPorEmail(email).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("error", "El email ya está registrado"));
        }

        Cliente cliente = new Cliente(nombre, "", email, telefono);
        cliente.setContraseña(passwordEncoder.encode(contraseña));
        cliente.setCiudad(ciudad);
        cliente.setActivo(true);

        Cliente clienteGuardado = clienteServicio.guardar(cliente);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("id", clienteGuardado.getId());
        response.put("nombre", clienteGuardado.getNombre());
        response.put("email", clienteGuardado.getEmail());
        response.put("mensaje", "Registro exitoso. Ahora puedes iniciar sesión");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
