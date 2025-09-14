package com.ventas.controller;

import com.ventas.model.Compra;
import com.ventas.service.CompraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/compras")
@CrossOrigin(origins = "*")
public class CompraController {
    
    @Autowired
    private CompraService compraService;
    
    @GetMapping
    public ResponseEntity<List<Compra>> getAllCompras() {
        List<Compra> compras = compraService.findAll();
        return ResponseEntity.ok(compras);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Compra> getCompraById(@PathVariable Integer id) {
        Optional<Compra> compra = compraService.findById(id);
        return compra.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Compra>> getComprasByUsuario(@PathVariable Integer usuarioId) {
        List<Compra> compras = compraService.findByUsuario(usuarioId);
        return ResponseEntity.ok(compras);
    }
    
    @GetMapping("/estatus/{estatus}")
    public ResponseEntity<List<Compra>> getComprasByEstatus(@PathVariable String estatus) {
        List<Compra> compras = compraService.findByEstatus(estatus);
        return ResponseEntity.ok(compras);
    }
    
    @GetMapping("/fechas")
    public ResponseEntity<List<Compra>> getComprasByFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        List<Compra> compras = compraService.findByFechas(fechaInicio, fechaFin);
        return ResponseEntity.ok(compras);
    }
    
    @GetMapping("/usuario/{usuarioId}/total")
    public ResponseEntity<Double> getTotalComprasUsuario(@PathVariable Integer usuarioId) {
        Double total = compraService.calcularTotalComprasUsuario(usuarioId);
        return ResponseEntity.ok(total);
    }
    
    @PostMapping("/procesar/{usuarioId}")
    public ResponseEntity<Compra> procesarCompra(@PathVariable Integer usuarioId) {
        Compra compra = compraService.procesarCompra(usuarioId);
        if (compra != null) {
            return ResponseEntity.ok(compra);
        }
        return ResponseEntity.badRequest().build();
    }
    
    @PostMapping
    public ResponseEntity<Compra> createCompra(@RequestBody Compra compra) {
        Compra nuevaCompra = compraService.save(compra);
        return ResponseEntity.ok(nuevaCompra);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Compra> updateCompra(@PathVariable Integer id, @RequestBody Compra compra) {
        Optional<Compra> compraExistente = compraService.findById(id);
        if (compraExistente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        compra.setIdCompra(id);
        Compra compraActualizada = compraService.save(compra);
        return ResponseEntity.ok(compraActualizada);
    }
    
    @PutMapping("/{id}/estatus")
    public ResponseEntity<Compra> actualizarEstatus(@PathVariable Integer id, @RequestParam String estatus) {
        Compra compra = compraService.actualizarEstatus(id, estatus);
        if (compra != null) {
            return ResponseEntity.ok(compra);
        }
        return ResponseEntity.notFound().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompra(@PathVariable Integer id) {
        Optional<Compra> compra = compraService.findById(id);
        if (compra.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        compraService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
