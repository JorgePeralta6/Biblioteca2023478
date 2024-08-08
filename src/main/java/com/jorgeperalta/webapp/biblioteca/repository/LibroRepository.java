package com.jorgeperalta.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jorgeperalta.webapp.biblioteca.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {


}
