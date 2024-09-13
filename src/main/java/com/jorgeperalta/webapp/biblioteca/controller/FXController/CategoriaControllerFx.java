package com.jorgeperalta.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jorgeperalta.webapp.biblioteca.model.Categoria;
import com.jorgeperalta.webapp.biblioteca.model.Libro;
import com.jorgeperalta.webapp.biblioteca.service.CategoriaService;
import com.jorgeperalta.webapp.biblioteca.system.Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

@Component
public class CategoriaControllerFx implements Initializable {
    @FXML
    TextField tfId, tfNombre, tfBuscar;
    @FXML
    Button btnGuardar, btnLimpiar, btnEliminar, btnRegresar, btnBuscar;
    @FXML
    TableView tblCategoria;
    @FXML
    TableColumn colId, colNombre;

    @Setter
    private Main stage;

    @Autowired
    CategoriaService categoriaService;
    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnGuardar) {
            if (tfId.getText().isBlank()) {
                agregarCategoria();
            } else {
                editarCategoria();
            }
        } else if (event.getSource() == btnLimpiar) {
            limpiarFormEditar();
        } else if (event.getSource() == btnRegresar) {
            stage.indexView();
        } else if (event.getSource() == btnEliminar) {
            eliminarCategoria();
        } else if (event.getSource() == btnBuscar) {
            tblCategoria.getItems().clear();
            if (tfBuscar.getText().isBlank()) {
                cargarDatos();
            } else {
                tblCategoria.getItems().add(buscarCategoria());
                colId.setCellValueFactory(new PropertyValueFactory<Categoria, Long>("id"));
                colNombre.setCellValueFactory(new PropertyValueFactory<Categoria, String>("nombreCategoria"));
            }
        }
    }

    public void cargarDatos(){
        tblCategoria.getItems().clear();
        tblCategoria.setItems(listarCategorias());
        colId.setCellValueFactory(new PropertyValueFactory<Categoria,Long>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Categoria, String>("nombreCategoria"));
    }

    public void cargarFormEditar(){
        Categoria categoria = (Categoria)tblCategoria.getSelectionModel().getSelectedItem();
        if (categoria != null) {
            tfId.setText(Long.toString(categoria.getId()));
            tfNombre.setText(categoria.getNombreCategoria());
            
        }
    }

    public void limpiarFormEditar(){
        tfId.clear();
        tfNombre.clear();
    }

    public ObservableList<Categoria> listarCategorias(){
        return FXCollections.observableList(categoriaService.listarCategorias());
    }

    public void agregarCategoria(){
        Categoria categoria = new Categoria();
        categoria.setNombreCategoria(tfNombre.getText());
        categoriaService.guardarCategoria(categoria);
        cargarDatos();
    }

    public void editarCategoria(){
        Categoria categoria = categoriaService.buscarCategoriaPorId(Long.parseLong(tfId.getText()));
        categoria.setNombreCategoria(tfNombre.getText());
        categoriaService.guardarCategoria(categoria);
        cargarDatos();
    }

    public void eliminarCategoria(){
        Categoria categoria = categoriaService.buscarCategoriaPorId(Long.parseLong(tfId.getText()));
        categoriaService.eliminarCategoria(categoria);
        cargarDatos();
    }   
    
    public Categoria buscarCategoria() {
        return categoriaService.buscarCategoriaPorId(Long.parseLong(tfBuscar.getText()));
    }

}
