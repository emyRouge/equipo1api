package com.ventas.service;

import com.ventas.model.Producto;
import com.ventas.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }
    
    public Optional<Producto> findById(Integer id) {
        return productoRepository.findById(id);
    }
    
    public List<Producto> findByCategoria(Integer categoriaId) {
        return productoRepository.findByCategoria_IdCategoria(categoriaId);
    }
    
    public List<Producto> findByNombre(String nombre) {
        return productoRepository.findByNombreContainingIgnoreCase(nombre);
    }
    
    public List<Producto> findByStockDisponible() {
        return productoRepository.findByStockGreaterThan(0);
    }
    
    public List<Producto> findTopProductos() {
        return productoRepository.findTopProductosByVentas();
    }
    
    public List<Producto> findByRangoPrecio(Double precioMin, Double precioMax) {
        return productoRepository.findByPrecioBetween(precioMin, precioMax);
    }
    
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }
    
    public void deleteById(Integer id) {
        productoRepository.deleteById(id);
    }
    
    public boolean existsById(Integer id) {
        return productoRepository.existsById(id);
    }
    
    public void actualizarStock(Integer productoId, Integer cantidad) {
        Optional<Producto> producto = findById(productoId);
        if (producto.isPresent()) {
            Producto p = producto.get();
            p.setStock(p.getStock() - cantidad);
            p.setNumVentas(p.getNumVentas() + cantidad);
            save(p);
        }
    }
}
