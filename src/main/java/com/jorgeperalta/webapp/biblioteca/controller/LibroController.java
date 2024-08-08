package com.jorgeperalta.webapp.biblioteca.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.jorgeperalta.webapp.biblioteca.model.Libro;
import com.jorgeperalta.webapp.biblioteca.service.LibroService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping(value = "")
public class LibroController {

    @Autowired
    LibroService libroService;

    @GetMapping("/libros")
    public ResponseEntity<List<Libro>> listarLibros(){
        try {
            return ResponseEntity.ok(libroService.listarLibros());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/libro")
    public ResponseEntity<Libro> buscarLibroPorId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(libroService.buscarLibroPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/libros")
    public ResponseEntity<Map<String, Boolean>> agregarLibro(@RequestBody Libro libro){
        Map<String,Boolean> response = new HashMap<>();
        try {
            libroService.guardarLibro(libro);
            response.put("Se agrego con exito", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("Se agrego con exito", Boolean.TRUE);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/libro")
    public ResponseEntity<Map<String, Boolean>> editarLibro(@RequestParam Long id, @RequestBody Libro libroNuevo){
        Map<String, Boolean> response = new HashMap<>();
        try {
            Libro libro = libroService.buscarLibroPorId(id);
            libro.setIsbn(libroNuevo.getIsbn());
            libro.setNombre(libroNuevo.getNombre());
            libro.setSinopsis(libroNuevo.getSinopsis());
            libro.setAutor(libroNuevo.getAutor());
            libro.setEditorial(libroNuevo.getEditorial());
            libro.setDisponibilidad(libroNuevo.getDisponibilidad());
            libro.setNumeroEstanteria(libroNuevo.getNumeroEstanteria());
            libro.setCluster(libroNuevo.getCluster());
            libro.setCategoria(libroNuevo.getCategoria());
            libroService.guardarLibro(libro);
            response.put("Editado con exito", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("Editado con exito", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/libro")
    public ResponseEntity<Map<String, Boolean>> eliminarLibro(@RequestParam Long id){
        Map<String, Boolean> response = new HashMap<>();
        try {
            Libro libro = libroService.buscarLibroPorId(id);
            libroService.eliminarLibro(libro);
            response.put("Libro Eliminado", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("Libro Eliminado",Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }

}
