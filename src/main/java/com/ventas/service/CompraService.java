package com.ventas.service;

import com.ventas.model.Compra;
import com.ventas.model.Carrito;
import com.ventas.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CompraService {
    
    @Autowired
    private CompraRepository compraRepository;
    
    @Autowired
    private CarritoService carritoService;
    
    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    public List<Compra> findAll() {
        return compraRepository.findAll();
    }
    
    public Optional<Compra> findById(Integer id) {
        return compraRepository.findById(id);
    }
    
    public List<Compra> findByUsuario(Integer usuarioId) {
        return compraRepository.findByUsuario_IdUsuario(usuarioId);
    }
    
    public List<Compra> findByEstatus(String estatus) {
        return compraRepository.findByEstatus(estatus);
    }
    
    public List<Compra> findByFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return compraRepository.findByFechaCompraBetween(fechaInicio, fechaFin);
    }
    
    public Double calcularTotalComprasUsuario(Integer usuarioId) {
        Double total = compraRepository.calcularTotalComprasUsuario(usuarioId);
        return total != null ? total : 0.0;
    }
    
    @Transactional
    public Compra procesarCompra(Integer usuarioId) {
        List<Carrito> itemsCarrito = carritoService.findByUsuario(usuarioId);
        
        if (itemsCarrito.isEmpty()) {
            return null;
        }
        
        Double total = carritoService.calcularTotalCarrito(usuarioId);
        
        // Crear la compra
        Compra compra = new Compra();
        compra.setUsuario(usuarioService.findById(usuarioId).orElse(null));
        compra.setFechaCompra(LocalDateTime.now());
        compra.setTotal(total);
        compra.setEstatus("PROCESANDO");
        
        // Actualizar stock de productos
        for (Carrito item : itemsCarrito) {
            productoService.actualizarStock(item.getProducto().getIdProducto(), item.getCantidad());
        }
        
        // Limpiar carrito
        carritoService.limpiarCarrito(usuarioId);
        
        return compraRepository.save(compra);
    }
    
    public Compra save(Compra compra) {
        return compraRepository.save(compra);
    }
    
    public void deleteById(Integer id) {
        compraRepository.deleteById(id);
    }
    
    public Compra actualizarEstatus(Integer compraId, String nuevoEstatus) {
        Optional<Compra> compra = findById(compraId);
        if (compra.isPresent()) {
            Compra c = compra.get();
            c.setEstatus(nuevoEstatus);
            return save(c);
        }
        return null;
    }
}
