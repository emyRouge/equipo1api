package com.ventas.service;

import com.ventas.model.Categoria;
import com.ventas.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    public List<Categoria> findAll() {
        return categoriaRepository.findAll();
    }
    
    public Optional<Categoria> findById(Integer id) {
        return categoriaRepository.findById(id);
    }
    
    public Optional<Categoria> findByNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre);
    }
    
    public Categoria save(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }
    
    public void deleteById(Integer id) {
        categoriaRepository.deleteById(id);
    }
    
    public boolean existsById(Integer id) {
        return categoriaRepository.existsById(id);
    }
}
