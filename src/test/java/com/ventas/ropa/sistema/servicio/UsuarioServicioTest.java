package com.ventas.ropa.sistema.servicio;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ventas.ropa.sistema.modelo.Usuario;
import com.ventas.ropa.sistema.repositorio.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class UsuarioServicioTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioServicio usuarioServicio;

    @Test
    void validarCredenciales_debeAceptarContraseñaPlanaLegacyYGuardarHash() {
        Usuario admin = new Usuario(
                "Administrador",
                "admin@urbanflow.com",
                "admin",
                "admin123",
                "ADMIN"
        );

        when(usuarioRepository.findByUsuario("admin")).thenReturn(Optional.of(admin));

        boolean resultado = usuarioServicio.validarCredenciales("admin", "admin123");

        assertTrue(resultado);
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    void validarCredenciales_debeRechazarUsuarioInexistente() {
        when(usuarioRepository.findByUsuario("noexiste")).thenReturn(Optional.empty());

        assertFalse(usuarioServicio.validarCredenciales("noexiste", "cualquier"));
    }
}
