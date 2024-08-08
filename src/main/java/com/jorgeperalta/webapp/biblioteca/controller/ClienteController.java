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

import com.jorgeperalta.webapp.biblioteca.model.Cliente;
import com.jorgeperalta.webapp.biblioteca.service.ClienteService;

@Controller
@RestController
@RequestMapping(value = "")
public class ClienteController{

    @Autowired
    ClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> listaClientes(){
        return clienteService.listarClientes();
    }

    @GetMapping("/cliente")
    public ResponseEntity<Cliente> buscarClientePorId(@RequestParam Long dpi){
        try {
            return ResponseEntity.ok(clienteService.buscarClientePorId(dpi));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping("/cliente")
    public ResponseEntity<Map<String, Boolean>> agregarCliente(@RequestBody Cliente cliente){
        Map<String, Boolean> response = new HashMap<>();
        try {
            clienteService.guardarCliente(cliente);
            response.put("Se agrego con exito", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("Se agrego con exito", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/cliente")
    public ResponseEntity<Map<String, Boolean>> editarCliente(@RequestParam Long dpi, @RequestBody Cliente clienteNueva){
        Map<String, Boolean> response = new HashMap<>();
        try {
            Cliente cliente = clienteService.buscarClientePorId(dpi);
            cliente.setNombreCliente(clienteNueva.getNombreCliente());
            cliente.setApellidoCliente(clienteNueva.getApellidoCliente());
            cliente.setTelefono(clienteNueva.getTelefono());
            clienteService.guardarCliente(cliente);
            response.put("Editado con exito", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("Editado con exito", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/cliente")
    public ResponseEntity<Map<String, Boolean>> eliminarCliente(@RequestParam Long dpi){
        Map<String, Boolean> response = new HashMap<>();
        try {
            Cliente cliente = clienteService.buscarClientePorId(dpi);
            clienteService.eliminarCliente(cliente);
            response.put("Cliente Eliminada", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("Cliente Eliminada",Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }
}