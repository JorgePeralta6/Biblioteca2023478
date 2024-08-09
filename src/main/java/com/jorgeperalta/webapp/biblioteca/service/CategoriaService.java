package com.jorgeperalta.webapp.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jorgeperalta.webapp.biblioteca.model.Categoria;
import com.jorgeperalta.webapp.biblioteca.repository.CategoriaRepository;

@Service
public class CategoriaService implements ICategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;
 
    @Override
    public List<Categoria> listarCategorias(){
        return categoriaRepository.findAll();
    }
 
    @Override
    public Categoria buscarCategoriaPorId(Long id){
        return categoriaRepository.findById(id).orElse(null);
    }
 
    @Override
    public Boolean guardarCategoria(Categoria categoria){
        if(!verificarCategoriaDuplicada(categoria)){ //Categoria no duplicada
            categoriaRepository.save(categoria);
            return true;
        }else{ //Categoria duplicada   
            return false;
        }
    }
 
    @Override
    public void eliminarCategoria(Categoria categoria){
        categoriaRepository.delete(categoria);
    }

    @Override
    public Boolean verificarCategoriaDuplicada(Categoria categoriaNuevo){
        List<Categoria> categorias = listarCategorias();
        Boolean flag = false;
        
        for (Categoria categoria : categorias) {
            if(categoriaNuevo.getNombreCategoria().trim().equalsIgnoreCase(categoria.getNombreCategoria().trim()) && !categoria.getId().equals(categoriaNuevo.getId())){
                flag = true; 
            }
        }
        return flag;
    }
}
