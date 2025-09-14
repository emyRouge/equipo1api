package com.ventas.repository;

import com.ventas.model.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarritoRepository extends JpaRepository<Carrito, Integer> {
    List<Carrito> findByUsuario_IdUsuario(Integer usuarioId);
    Optional<Carrito> findByUsuario_IdUsuarioAndProducto_IdProducto(Integer usuarioId, Integer productoId);
    
    @Query("SELECT SUM(c.cantidad * c.producto.precio) FROM Carrito c WHERE c.usuario.idUsuario = ?1")
    Double calcularTotalCarrito(Integer usuarioId);
    
    void deleteByUsuario_IdUsuario(Integer usuarioId);
}
