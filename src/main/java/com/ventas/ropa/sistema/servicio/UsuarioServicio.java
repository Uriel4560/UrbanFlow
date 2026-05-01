package com.ventas.ropa.sistema.servicio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ventas.ropa.sistema.modelo.Usuario;
import com.ventas.ropa.sistema.repositorio.UsuarioRepository;

@Service
public class UsuarioServicio {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }
    
    public Optional<Usuario> obtenerPorUsuario(String usuario) {
        return usuarioRepository.findByUsuario(usuario);
    }
    
    public Optional<Usuario> obtenerPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }
    
    public Usuario guardar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
    
    public boolean validarCredenciales(String usuario, String contraseña) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsuario(usuario);
        
        if (usuarioOpt.isPresent()) {
            Usuario u = usuarioOpt.get();
            return u.isActivo() && passwordEncoder.matches(contraseña, u.getContraseña());
        }
        
        return false;
    }
    
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }
}
