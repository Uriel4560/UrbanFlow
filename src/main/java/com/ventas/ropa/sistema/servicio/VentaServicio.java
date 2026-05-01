package com.ventas.ropa.sistema.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ventas.ropa.sistema.modelo.Venta;
import com.ventas.ropa.sistema.repositorio.VentaRepository;

@Service
public class VentaServicio {
    
    @Autowired
    private VentaRepository ventaRepository;
    
    public List<Venta> obtenerTodas() {
        return ventaRepository.findAll();
    }
    
    public Optional<Venta> obtenerPorId(Long id) {
        return ventaRepository.findById(id);
    }
    
    public Venta guardar(Venta venta) {
        return ventaRepository.save(venta);
    }
    
    public void eliminar(Long id) {
        ventaRepository.deleteById(id);
    }
}
