package com.jorgeperalta.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jorgeperalta.webapp.biblioteca.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    
}