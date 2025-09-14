package com.ventas.controller;

import com.ventas.model.Producto;
import com.ventas.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {
    
    @Autowired
    private ProductoService productoService;
    
    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        List<Producto> productos = productoService.findAll();
        return ResponseEntity.ok(productos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Integer id) {
        Optional<Producto> producto = productoService.findById(id);
        return producto.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Producto>> getProductosByCategoria(@PathVariable Integer categoriaId) {
        List<Producto> productos = productoService.findByCategoria(categoriaId);
        return ResponseEntity.ok(productos);
    }
    
    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<Producto>> buscarProductosPorNombre(@PathVariable String nombre) {
        List<Producto> productos = productoService.findByNombre(nombre);
        return ResponseEntity.ok(productos);
    }
    
    @GetMapping("/disponibles")
    public ResponseEntity<List<Producto>> getProductosDisponibles() {
        List<Producto> productos = productoService.findByStockDisponible();
        return ResponseEntity.ok(productos);
    }
    
    @GetMapping("/top-ventas")
    public ResponseEntity<List<Producto>> getTopProductos() {
        List<Producto> productos = productoService.findTopProductos();
        return ResponseEntity.ok(productos);
    }
    
    @GetMapping("/precio")
    public ResponseEntity<List<Producto>> getProductosByRangoPrecio(
            @RequestParam Double min, @RequestParam Double max) {
        List<Producto> productos = productoService.findByRangoPrecio(min, max);
        return ResponseEntity.ok(productos);
    }
    
    @PostMapping
    public ResponseEntity<Producto> createProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = productoService.save(producto);
        return ResponseEntity.ok(nuevoProducto);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Producto> updateProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        if (!productoService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        producto.setIdProducto(id);
        Producto productoActualizado = productoService.save(producto);
        return ResponseEntity.ok(productoActualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable Integer id) {
        if (!productoService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        productoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
