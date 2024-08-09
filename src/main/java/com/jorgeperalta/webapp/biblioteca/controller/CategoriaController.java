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

import com.jorgeperalta.webapp.biblioteca.model.Categoria;
import com.jorgeperalta.webapp.biblioteca.service.CategoriaService;

@Controller
@RestController
@RequestMapping(value = "")
public class CategoriaController{

    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/categorias")
    public List<Categoria> listaCategorias(){
        return categoriaService.listarCategorias();
    }

    @GetMapping("/categoria")
    public ResponseEntity<Categoria> buscarCategoriaPorId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(categoriaService.buscarCategoriaPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/categoria")
    public ResponseEntity<Map<String, Boolean>> agregarCategoria(@RequestBody Categoria categoria){
        Map<String, Boolean> response = new HashMap<>();
        try {
            if (categoriaService.guardarCategoria(categoria)) {
                response.put("Se agrego con exito", Boolean.TRUE);
                return ResponseEntity.ok(response);
            } else {
                response.put("Se agrego con exito", Boolean.FALSE);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("Se agrego con exito", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/categoria")
    public ResponseEntity<Map<String, Boolean>> editarCategoria(@RequestParam Long id, @RequestBody Categoria categoriaNueva){
        Map<String, Boolean> response = new HashMap<>();
        try {
            Categoria categoria = categoriaService.buscarCategoriaPorId(id);
            categoria.setNombreCategoria(categoriaNueva.getNombreCategoria());
            if (categoriaService.guardarCategoria(categoria)) {
                response.put("Editado con exito", Boolean.TRUE);
                return ResponseEntity.ok(response);
            } else {
                response.put("Editado con exito", Boolean.FALSE);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("Editado con exito", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/categoria")
    public ResponseEntity<Map<String, Boolean>> eliminarCategoria(@RequestParam Long id){
        Map<String, Boolean> response = new HashMap<>();
        try {
            Categoria categoria = categoriaService.buscarCategoriaPorId(id);
            categoriaService.eliminarCategoria(categoria);
            response.put("Categoria Eliminada", Boolean.TRUE);          
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("Categoria Eliminada",Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }
}