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

import com.jorgeperalta.webapp.biblioteca.model.Empleado;
import com.jorgeperalta.webapp.biblioteca.service.EmpleadoService;

@Controller
@RestController
@RequestMapping(value = "")
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;

    @GetMapping("/empleados")
    public List<Empleado> listaEmpleados(){
        return empleadoService.listarEmpleados();
    }

    @GetMapping("/empleado")
    public ResponseEntity<Empleado> buscarEmpleadoPorId(@RequestParam Long id){
        try {
            return ResponseEntity.ok(empleadoService.buscarEmpleadoPorId(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/empleado")
    public ResponseEntity<Map<String, Boolean>> agregarEmpleado(@RequestBody Empleado empleado){
        Map<String, Boolean> response = new HashMap<>();
        try {
            if (empleadoService.guardarEmpleado(empleado)) {
                response.put("Se agrego con exito", Boolean.TRUE);
                return ResponseEntity.ok(response);
            }else{
                response.put("DPI duplicado", Boolean.FALSE);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("Se agrego con exito", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }



    @PutMapping("/empleado")
    public ResponseEntity<Map<String, Boolean>> editarEmpleado(@RequestParam Long id, @RequestBody Empleado empleadoNuevo){
        Map<String, Boolean> response = new HashMap<>();
        try {
            Empleado empleado = empleadoService.buscarEmpleadoPorId(id);
            empleado.setNombreEmpleado(empleadoNuevo.getNombreEmpleado());
            empleado.setApellidoEmpleado(empleadoNuevo.getApellidoEmpleado());
            empleado.setTelefonoEmpleado(empleadoNuevo.getTelefonoEmpleado());
            empleado.setDireccion(empleadoNuevo.getDireccion());
            empleado.setDpi(empleadoNuevo.getDpi());
            if (empleadoService.guardarEmpleado(empleado)) {
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

    @DeleteMapping("/empleado")
    public ResponseEntity<Map<String, Boolean>> eliminarEmpleado(@RequestParam Long id){
        Map<String, Boolean> response = new HashMap<>();
        try {
            Empleado empleado = empleadoService.buscarEmpleadoPorId(id);
            empleadoService.eliminarEmpleado(empleado);
            response.put("Empleado Eliminado", Boolean.TRUE);          
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("Empleado Eliminado",Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }
}
