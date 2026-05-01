package com.ventas.ropa.sistema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.ventas.ropa.sistema.modelo.Cliente;
import com.ventas.ropa.sistema.modelo.Producto;
import com.ventas.ropa.sistema.modelo.Usuario;
import com.ventas.ropa.sistema.repositorio.ClienteRepository;
import com.ventas.ropa.sistema.repositorio.ProductoRepository;
import com.ventas.ropa.sistema.repositorio.UsuarioRepository;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Autowired
    private ClienteRepository clienteRepository;
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Override
    public void run(String... args) throws Exception {
        // Cargar productos si no existen
        if (productoRepository.count() == 0) {
            productoRepository.save(new Producto("CAMI001", "Camiseta Básica", "Camiseta de algodón 100%", "Camisetas", "ropaTech", "M", "Blanco", 29.99, 50, "Algodón"));
            productoRepository.save(new Producto("PANT001", "Pantalón Jeans", "Pantalón denim clásico", "Pantalones", "fashionBrand", "L", "Azul", 79.99, 30, "Denim"));
            productoRepository.save(new Producto("SUDA001", "Sudadera Premium", "Sudadera con capucha deportiva", "Sudaderas", "sportWear", "L", "Gris", 59.99, 25, "Poliéster"));
            productoRepository.save(new Producto("SHORT001", "Shorts Verano", "Shorts cómodos para verano", "Shorts", "beachWear", "M", "Negro", 34.99, 40, "Algodón"));
            productoRepository.save(new Producto("CHAQ001", "Chaqueta de Cuero", "Chaqueta de cuero genuino", "Chaquetas", "luxuryWear", "XL", "Marrón", 199.99, 15, "Cuero"));
            productoRepository.save(new Producto("VEST001", "Vestido Casual", "Vestido de algodón para el día", "Vestidos", "formalWear", "S", "Rojo", 49.99, 20, "Algodón"));
        }
        
        // Cargar clientes si no existen
        if (clienteRepository.count() == 0) {
            Cliente cliente1 = new Cliente("Juan", "Pérez", "juan@example.com", "3001234567");
            cliente1.setCedula("1234567890");
            cliente1.setCiudad("Medellín");
            cliente1.setDireccion("Calle 10 #20-30");
            clienteRepository.save(cliente1);
            
            Cliente cliente2 = new Cliente("María", "García", "maria@example.com", "3009876543");
            cliente2.setCedula("0987654321");
            cliente2.setCiudad("Bogotá");
            cliente2.setDireccion("Carrera 7 #45-50");
            clienteRepository.save(cliente2);
            
            Cliente cliente3 = new Cliente("Carlos", "López", "carlos@example.com", "3102468135");
            cliente3.setCedula("1122334455");
            cliente3.setCiudad("Cali");
            cliente3.setDireccion("Avenida Sexta #10-80");
            clienteRepository.save(cliente3);
        }
        
        // Cargar usuarios si no existen
        if (usuarioRepository.count() == 0) {
            // Usuario Admin
            Usuario admin = new Usuario(
                "Administrador",
                "admin@urbanflow.com",
                "admin",
                passwordEncoder.encode("admin123"),
                "ADMIN"
            );
            usuarioRepository.save(admin);
            
            // Usuario Vendedor
            Usuario vendedor = new Usuario(
                "Carlos Vendedor",
                "vendedor@urbanflow.com",
                "vendedor",
                passwordEncoder.encode("vendedor123"),
                "VENDEDOR"
            );
            usuarioRepository.save(vendedor);
            
            // Usuario Gerente
            Usuario gerente = new Usuario(
                "María Gerente",
                "gerente@urbanflow.com",
                "gerente",
                passwordEncoder.encode("gerente123"),
                "GERENTE"
            );
            usuarioRepository.save(gerente);
        }
    }
}
