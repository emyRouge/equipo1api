package com.ventas.controller;

import com.ventas.model.Carrito;
import com.ventas.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carrito")
@CrossOrigin(origins = "*")
public class CarritoController {
    
    @Autowired
    private CarritoService carritoService;
    
    @GetMapping
    public ResponseEntity<List<Carrito>> getAllCarritos() {
        List<Carrito> carritos = carritoService.findAll();
        return ResponseEntity.ok(carritos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Carrito> getCarritoById(@PathVariable Integer id) {
        Optional<Carrito> carrito = carritoService.findById(id);
        return carrito.map(ResponseEntity::ok)
                     .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Carrito>> getCarritoByUsuario(@PathVariable Integer usuarioId) {
        List<Carrito> carritos = carritoService.findByUsuario(usuarioId);
        return ResponseEntity.ok(carritos);
    }
    
    @GetMapping("/usuario/{usuarioId}/total")
    public ResponseEntity<Double> getTotalCarrito(@PathVariable Integer usuarioId) {
        Double total = carritoService.calcularTotalCarrito(usuarioId);
        return ResponseEntity.ok(total);
    }
    
    @PostMapping("/agregar")
    public ResponseEntity<Carrito> agregarProducto(
            @RequestParam Integer usuarioId,
            @RequestParam Integer productoId,
            @RequestParam Integer cantidad) {
        Carrito carrito = carritoService.agregarProducto(usuarioId, productoId, cantidad);
        if (carrito != null) {
            return ResponseEntity.ok(carrito);
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PostMapping
    public ResponseEntity<Carrito> createCarrito(@RequestBody Carrito carrito) {
        Carrito nuevoCarrito = carritoService.save(carrito);
        return ResponseEntity.ok(nuevoCarrito);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Carrito> updateCarrito(@PathVariable Integer id, @RequestBody Carrito carrito) {
        Optional<Carrito> carritoExistente = carritoService.findById(id);
        if (carritoExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        carrito.setIdCarrito(id);
        Carrito carritoActualizado = carritoService.save(carrito);
        return ResponseEntity.ok(carritoActualizado);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrito(@PathVariable Integer id) {
        Optional<Carrito> carrito = carritoService.findById(id);
        if (carrito.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        carritoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
    @DeleteMapping("/usuario/{usuarioId}/limpiar")
    public ResponseEntity<Void> limpiarCarrito(@PathVariable Integer usuarioId) {
        carritoService.limpiarCarrito(usuarioId);
        return ResponseEntity.noContent().build();
    }
}
