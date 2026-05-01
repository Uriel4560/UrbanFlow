package com.ventas.ropa.sistema.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ventas.ropa.sistema.modelo.DetalleVenta;
import com.ventas.ropa.sistema.repositorio.DetalleVentaRepository;

@Service
public class DetalleVentaServicio {
    
    @Autowired
    private DetalleVentaRepository detalleVentaRepository;
    
    public List<DetalleVenta> obtenerTodos() {
        return detalleVentaRepository.findAll();
    }
    
    public Optional<DetalleVenta> obtenerPorId(Long id) {
        return detalleVentaRepository.findById(id);
    }
    
    public DetalleVenta guardar(DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }
    
    public void eliminar(Long id) {
        detalleVentaRepository.deleteById(id);
    }
}
