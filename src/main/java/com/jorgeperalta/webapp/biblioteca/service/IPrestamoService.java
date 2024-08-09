package com.jorgeperalta.webapp.biblioteca.service;


import java.util.List;

import com.jorgeperalta.webapp.biblioteca.model.Prestamo;


public interface IPrestamoService {

    public List<Prestamo> listarPrestamos();

    public Prestamo buscarPrestamoPorId(Long id);

    public Boolean guardarPrestamo(Prestamo prestamo);

    public void eliminarPrestamo(Prestamo prestamo);

    public Boolean verificarPrestamoActivo(Prestamo prestamoNuevo);
}
