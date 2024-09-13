package com.jorgeperalta.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jorgeperalta.webapp.biblioteca.model.Categoria;
import com.jorgeperalta.webapp.biblioteca.model.Libro;
import com.jorgeperalta.webapp.biblioteca.service.CategoriaService;
import com.jorgeperalta.webapp.biblioteca.service.LibroService;
import com.jorgeperalta.webapp.biblioteca.system.Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

@Component
public class LibroControllerFx implements Initializable{

    @FXML
    TextField tfId, tfIsbn, tfNombreL, tfAutor, tfEditorial, tfNumEstanteria, tfCluster, tfBuscar;

    @FXML
    TextArea taSinopsis;

    @FXML
    ComboBox cmbDisponibilidad, cmbCategoria;

    @FXML
    Button btnGuardar, btnEliminar, btnLimpiar, btnBuscar, btnRegresar;

    @FXML 
    TableView tblLibro;

    @FXML
    TableColumn colId, colIsbn, colNombreL, colSinopsis, colAutor, colEditorial, colDisponibilidad, colNumEstanteria, colCluster, colCategoria;

    @Setter
    private Main stage;

    @Autowired
    LibroService libroService;

    @Autowired
    CategoriaService categoriaService;


    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
        cmbDisponibilidad.setItems(listarDisponibilidad());
        cmbCategoria.setItems(listarCategorias());
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnGuardar) {
            if (tfId.getText().isBlank()) {
                agregarLibro();
            } else {
                editarLibro();
            }
        } else if (event.getSource() == btnLimpiar) {
            limpiarFormEditar();
        } else if (event.getSource() == btnRegresar) {
            stage.indexView();
        } else if (event.getSource() == btnEliminar) {
            eliminarLibro();
        } else if (event.getSource() == btnBuscar) {
            tblLibro.getItems().clear();
            if (tfBuscar.getText().isBlank()) {
                cargarDatos();
            } else {
                tblLibro.getItems().add(buscarLibro());
                colId.setCellValueFactory(new PropertyValueFactory<Libro, Long>("id"));
                colIsbn.setCellValueFactory(new PropertyValueFactory<Libro, String>("isbn"));
                colNombreL.setCellValueFactory(new PropertyValueFactory<Libro, String>("nombre"));
                colSinopsis.setCellValueFactory(new PropertyValueFactory<Libro, String>("sinopsis"));
                colAutor.setCellValueFactory(new PropertyValueFactory<Libro, String>("autor"));
                colEditorial.setCellValueFactory(new PropertyValueFactory<Libro, String>("editorial"));
                colDisponibilidad.setCellValueFactory(new PropertyValueFactory<Libro, Boolean>("disponibilidad"));
                colNumEstanteria.setCellValueFactory(new PropertyValueFactory<Libro, String>("numeroEstanteria"));
                colCluster.setCellValueFactory(new PropertyValueFactory<Libro, String>("cluster"));
                colCategoria.setCellValueFactory(new PropertyValueFactory<Libro, String>("categoria"));
            }

        }
    }

    public void cargarDatos(){
        tblLibro.setItems(listarLibros());
        colId.setCellValueFactory(new PropertyValueFactory<Libro, Long>("id"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<Libro, String>("isbn"));
        colNombreL.setCellValueFactory(new PropertyValueFactory<Libro, String>("nombre"));
        colSinopsis.setCellValueFactory(new PropertyValueFactory<Libro, String>("sinopsis"));
        colAutor.setCellValueFactory(new PropertyValueFactory<Libro, String>("autor"));
        colEditorial.setCellValueFactory(new PropertyValueFactory<Libro, String>("editorial"));
        colDisponibilidad.setCellValueFactory(new PropertyValueFactory<Libro, Boolean>("disponibilidad"));
        colNumEstanteria.setCellValueFactory(new PropertyValueFactory<Libro, String>("numeroEstanteria"));
        colCluster.setCellValueFactory(new PropertyValueFactory<Libro, String>("cluster"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<Libro, String>("categoria"));
    }

    public ObservableList<Libro> listarLibros(){
        return FXCollections.observableList(libroService.listarLibros());
    }

    public ObservableList<Categoria> listarCategorias(){
        return FXCollections.observableList(categoriaService.listarCategorias());
    }

    public ObservableList<Boolean> listarDisponibilidad() {
        return FXCollections.observableArrayList(true, false);
    }
    

    public void cargarFormEditar(){
        Libro libro = (Libro)tblLibro.getSelectionModel().getSelectedItem();
        if(libro != null){
            tfId.setText(Long.toString(libro.getId()));
            tfIsbn.setText(libro.getIsbn());
            tfNombreL.setText(libro.getNombre());
            taSinopsis.setText(libro.getSinopsis());
            tfAutor.setText(libro.getAutor());
            tfEditorial.setText(libro.getEditorial());
            cmbDisponibilidad.getSelectionModel().select(libro.getDisponibilidad());
            tfNumEstanteria.setText(libro.getNumeroEstanteria());
            tfCluster.setText(libro.getCluster());
            cmbCategoria.getSelectionModel().select(libro.getCategoria());
        }
    }

    public void limpiarFormEditar(){
        tfId.clear();
        tfIsbn.clear();
        tfNombreL.clear();
        taSinopsis.clear();
        tfAutor.clear();
        tfEditorial.clear();
        cmbDisponibilidad.getSelectionModel().clearSelection();
        tfNumEstanteria.clear();
        tfCluster.clear();
        cmbCategoria.getSelectionModel().clearSelection();
    }

    public void agregarLibro(){
        Libro libro = new Libro();
        libro.setIsbn(tfIsbn.getText());
        libro.setNombre(tfNombreL.getText());
        libro.setSinopsis(taSinopsis.getText());
        libro.setAutor(tfAutor.getText());
        libro.setEditorial(tfEditorial.getText());
        libro.setDisponibilidad((Boolean)cmbDisponibilidad.getSelectionModel().getSelectedItem());
        libro.setNumeroEstanteria(tfNumEstanteria.getText());
        libro.setCluster(tfCluster.getText());
        libro.setCategoria((Categoria)cmbCategoria.getSelectionModel().getSelectedItem());
        libroService.guardarLibro(libro);
        cargarDatos();
    }

    public void editarLibro(){
        Libro libro = libroService.buscarLibroPorId(Long.parseLong(tfId.getText()));
        libro.setIsbn(tfIsbn.getText());
        libro.setNombre(tfNombreL.getText());
        libro.setSinopsis(taSinopsis.getText());
        libro.setAutor(tfAutor.getText());
        libro.setEditorial(tfEditorial.getText());
        libro.setDisponibilidad((Boolean)cmbDisponibilidad.getSelectionModel().getSelectedItem());
        libro.setNumeroEstanteria(tfNumEstanteria.getText());
        libro.setCluster(tfCluster.getText());
        libro.setCategoria((Categoria)cmbCategoria.getSelectionModel().getSelectedItem());
        libroService.guardarLibro(libro);
        cargarDatos();
    }

    public void eliminarLibro(){
        Libro libro = libroService.buscarLibroPorId(Long.parseLong(tfId.getText()));
        libroService.eliminarLibro(libro);
        cargarDatos();
    }

    public Libro buscarLibro() {
        return libroService.buscarLibroPorId(Long.parseLong(tfBuscar.getText()));
    }
}