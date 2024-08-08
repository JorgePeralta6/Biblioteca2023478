package com.jorgeperalta.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jorgeperalta.webapp.biblioteca.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
}