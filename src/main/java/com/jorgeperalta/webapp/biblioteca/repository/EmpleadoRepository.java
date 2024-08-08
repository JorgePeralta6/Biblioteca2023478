package com.jorgeperalta.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jorgeperalta.webapp.biblioteca.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    
}