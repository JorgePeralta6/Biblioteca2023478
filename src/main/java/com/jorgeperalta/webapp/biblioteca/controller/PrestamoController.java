package com.jorgeperalta.webapp.biblioteca.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.jorgeperalta.webapp.biblioteca.model.Prestamo;
import com.jorgeperalta.webapp.biblioteca.service.PrestamoService;


@Controller
@RestController
@RequestMapping(value = "")
public class PrestamoController {

    @Autowired
    PrestamoService prestamoService;

    @GetMapping("/prestamos")
    public ResponseEntity<List<Prestamo>> listarPrestamos(){
        try {
            return ResponseEntity.ok(prestamoService.listarPrestamos());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/prestamo")
    public ResponseEntity<Prestamo> buscarPrestamoPorId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(prestamoService.buscarPrestamoPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/prestamo")
    public ResponseEntity<Map<String, Boolean>> agregarPrestamo(@RequestBody Prestamo prestamo){
        Map<String,Boolean> response = new HashMap<>();
        try {
            if (prestamoService.guardarPrestamo(prestamo)) {
                response.put("Se agrego con exito", Boolean.TRUE);
                return ResponseEntity.ok(response);
            } else {
                response.put("No se agrego por el prestomo", Boolean.FALSE);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("Se agrego con exito", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/prestamo")
        public ResponseEntity<Map<String, Boolean>> editarPrestamo(@RequestParam Long id, @RequestBody Prestamo prestamoNuevo){
        Map<String, Boolean> response = new HashMap<>();
        try {
            Prestamo prestamo = prestamoService.buscarPrestamoPorId(id);
            prestamo.setFechaDePrestamo(prestamoNuevo.getFechaDePrestamo());
            prestamo.setFechaDeDevolucion(prestamoNuevo.getFechaDeDevolucion());
            prestamo.setVigencia(prestamoNuevo.getVigencia());
            prestamo.setEmpleado(prestamoNuevo.getEmpleado());
            prestamo.setCliente(prestamoNuevo.getCliente());
            prestamo.setLibros(prestamoNuevo.getLibros());
            prestamoService.guardarPrestamo(prestamo);
            response.put("Editado con exito", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("Editado con exito", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/prestamo")
    public ResponseEntity<Map<String, Boolean>> eliminarPrestamo(@RequestParam Long id){
        Map<String, Boolean> response = new HashMap<>();
        try {
            Prestamo prestamo = prestamoService.buscarPrestamoPorId(id);
            prestamoService.eliminarPrestamo(prestamo);
            response.put("Prestamo Eliminado", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("Prestamo Eliminado",Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }
}
