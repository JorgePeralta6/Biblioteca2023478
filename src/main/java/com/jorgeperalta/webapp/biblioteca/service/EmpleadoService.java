package com.jorgeperalta.webapp.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jorgeperalta.webapp.biblioteca.model.Empleado;
import com.jorgeperalta.webapp.biblioteca.repository.EmpleadoRepository;

@Service
public class EmpleadoService implements IEmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;
 
    @Override
    public List<Empleado> listarEmpleados(){
        return empleadoRepository.findAll();
    }
 
    @Override
    public Empleado buscarEmpleadoPorId(Long id){
        return empleadoRepository.findById(id).orElse(null);
    }
 
    @Override
    public Empleado guardarEmpleado(Empleado empleado){
        return empleadoRepository.save(empleado);
    }
 
    @Override
    public void eliminarEmpleado(Empleado empleado){
        empleadoRepository.delete(empleado);
    }
}
