package com.ventas.repository;

import com.ventas.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    List<Producto> findByCategoria_IdCategoria(Integer categoriaId);
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByStockGreaterThan(Integer stock);
    
    @Query("SELECT p FROM Producto p ORDER BY p.numVentas DESC")
    List<Producto> findTopProductosByVentas();
    
    @Query("SELECT p FROM Producto p WHERE p.precio BETWEEN ?1 AND ?2")
    List<Producto> findByPrecioBetween(Double precioMin, Double precioMax);
}
