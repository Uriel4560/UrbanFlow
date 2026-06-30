package com.ventas.ropa.sistema;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ventas.ropa.sistema.modelo.Usuario;
import com.ventas.ropa.sistema.repositorio.ClienteRepository;
import com.ventas.ropa.sistema.repositorio.ProductoRepository;
import com.ventas.ropa.sistema.repositorio.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
class DataInitializerTest {

    @Mock
    private ProductoRepository productoRepository;

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private DataInitializer dataInitializer;

    @Test
    void run_debeCrearUsuariosFaltantesAunqueLaTablaNoEsteVacia() throws Exception {
        when(productoRepository.count()).thenReturn(1L);
        when(clienteRepository.count()).thenReturn(1L);

        Usuario adminExistente = new Usuario(
                "Administrador",
                "admin@urbanflow.com",
                "admin",
                "$2a$10$hashValido",
                "ADMIN"
        );

        when(usuarioRepository.findByUsuario("admin")).thenReturn(Optional.of(adminExistente));
        when(usuarioRepository.findByUsuario("vendedor")).thenReturn(Optional.empty());
        when(usuarioRepository.findByUsuario("gerente")).thenReturn(Optional.empty());

        dataInitializer.run();

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository, times(2)).save(captor.capture());

        assertEquals(2, captor.getAllValues().size());
        assertEquals("vendedor", captor.getAllValues().get(0).getUsuario());
        assertEquals("gerente", captor.getAllValues().get(1).getUsuario());
    }
}
