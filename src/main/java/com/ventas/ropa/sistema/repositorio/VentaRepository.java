package com.ventas.ropa.sistema.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ventas.ropa.sistema.modelo.Venta;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
}
