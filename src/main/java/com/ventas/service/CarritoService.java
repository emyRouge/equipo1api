package com.ventas.service;

import com.ventas.model.Carrito;
import com.ventas.model.Producto;
import com.ventas.model.Usuario;
import com.ventas.repository.CarritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CarritoService {
    
    @Autowired
    private CarritoRepository carritoRepository;
    
    @Autowired
    private ProductoService productoService;
    
    @Autowired
    private UsuarioService usuarioService;
    
    public List<Carrito> findAll() {
        return carritoRepository.findAll();
    }
    
    public Optional<Carrito> findById(Integer id) {
        return carritoRepository.findById(id);
    }
    
    public List<Carrito> findByUsuario(Integer usuarioId) {
        return carritoRepository.findByUsuario_IdUsuario(usuarioId);
    }
    
    public Double calcularTotalCarrito(Integer usuarioId) {
        Double total = carritoRepository.calcularTotalCarrito(usuarioId);
        return total != null ? total : 0.0;
    }
    
    @Transactional
    public Carrito agregarProducto(Integer usuarioId, Integer productoId, Integer cantidad) {
        Optional<Carrito> carritoExistente = carritoRepository
            .findByUsuario_IdUsuarioAndProducto_IdProducto(usuarioId, productoId);
        
        if (carritoExistente.isPresent()) {
            Carrito carrito = carritoExistente.get();
            carrito.setCantidad(carrito.getCantidad() + cantidad);
            return carritoRepository.save(carrito);
        } else {
            Carrito nuevoCarrito = new Carrito();
            Usuario usuario = usuarioService.findById(usuarioId).orElse(null);
            Producto producto = productoService.findById(productoId).orElse(null);
            
            if (usuario != null && producto != null) {
                nuevoCarrito.setUsuario(usuario);
                nuevoCarrito.setProducto(producto);
                nuevoCarrito.setCantidad(cantidad);
                return carritoRepository.save(nuevoCarrito);
            }
        }
        return null;
    }
    
    public Carrito save(Carrito carrito) {
        return carritoRepository.save(carrito);
    }
    
    public void deleteById(Integer id) {
        carritoRepository.deleteById(id);
    }
    
    @Transactional
    public void limpiarCarrito(Integer usuarioId) {
        carritoRepository.deleteByUsuario_IdUsuario(usuarioId);
    }
}
