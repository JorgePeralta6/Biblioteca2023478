package com.jorgeperalta.webapp.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jorgeperalta.webapp.biblioteca.model.Prestamo;
import com.jorgeperalta.webapp.biblioteca.repository.PrestamoRepository;
import com.jorgeperalta.webapp.biblioteca.util.MethodType;

@Service
public class PrestamoService implements IPrestamoService {

    @Autowired
    PrestamoRepository prestamoRepository;

    @Override
    public List<Prestamo> listarPrestamos(){
        return prestamoRepository.findAll();
    }

    @Override
    public Prestamo buscarPrestamoPorId(Long id){
        return prestamoRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean guardarPrestamo(Prestamo prestamo){
        if(!verificarPrestamoActivo(prestamo)){ //Categoria no duplicada
            prestamoRepository.save(prestamo);
            return true;
        }else{ //Categoria duplicada   
            return false;
        }
    }

    @Override
    public void eliminarPrestamo(Prestamo prestamo){
        prestamoRepository.delete(prestamo);
    }

    @Override
    public Boolean verificarPrestamoActivo(Prestamo prestamoNuevo){
        List<Prestamo> prestamos = listarPrestamos();
        Boolean flag = false;
        
        for (Prestamo prestamo : prestamos) {
            if(prestamoNuevo.getCliente().getDpi().equals(prestamo.getCliente().getDpi()) && prestamo.getVigencia() == true){
                flag = true; 
            }
        }
        return flag;
    }

}
