package com.ventas.ropa.sistema.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ventas.ropa.sistema.modelo.Cliente;
import com.ventas.ropa.sistema.repositorio.ClienteRepository;

@Service
public class ClienteServicio {
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }
    
    public Optional<Cliente> obtenerPorId(Long id) {
        return clienteRepository.findById(id);
    }
    
    public Optional<Cliente> obtenerPorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }
    
    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    public boolean validarCredenciales(String email, String contraseña) {
        Optional<Cliente> clienteOpt = clienteRepository.findByEmail(email);
        
        if (clienteOpt.isPresent()) {
            Cliente c = clienteOpt.get();
            return c.getActivo() && c.getContraseña() != null && 
                   passwordEncoder.matches(contraseña, c.getContraseña());
        }
        
        return false;
    }
    
    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}
