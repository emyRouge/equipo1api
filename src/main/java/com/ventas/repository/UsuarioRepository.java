package com.ventas.repository;

import com.ventas.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByRol_IdRol(Integer rolId);
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
}
