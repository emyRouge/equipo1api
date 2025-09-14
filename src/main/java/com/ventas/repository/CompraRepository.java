package com.ventas.repository;

import com.ventas.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {
    List<Compra> findByUsuario_IdUsuario(Integer usuarioId);
    List<Compra> findByEstatus(String estatus);
    List<Compra> findByFechaCompraBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);
    
    @Query("SELECT SUM(c.total) FROM Compra c WHERE c.usuario.idUsuario = ?1")
    Double calcularTotalComprasUsuario(Integer usuarioId);
}
