package com.jorgeperalta.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jorgeperalta.webapp.biblioteca.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

}
