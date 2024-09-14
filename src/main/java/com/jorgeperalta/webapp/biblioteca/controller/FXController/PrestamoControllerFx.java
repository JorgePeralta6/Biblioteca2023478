package com.jorgeperalta.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jorgeperalta.webapp.biblioteca.model.Cliente;
import com.jorgeperalta.webapp.biblioteca.model.Empleado;
import com.jorgeperalta.webapp.biblioteca.model.Libro;
import com.jorgeperalta.webapp.biblioteca.model.Prestamo;
import com.jorgeperalta.webapp.biblioteca.service.ClienteService;
import com.jorgeperalta.webapp.biblioteca.service.EmpleadoService;
import com.jorgeperalta.webapp.biblioteca.service.LibroService;
import com.jorgeperalta.webapp.biblioteca.service.PrestamoService;
import com.jorgeperalta.webapp.biblioteca.system.Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

@Component
public class PrestamoControllerFx implements Initializable{
    
    @FXML
    TextField tfId;

    @FXML
    Button btnGuardar, btnEliminar, btnLimpiar, btnRegresar;

    @FXML
    DatePicker dtFechaPrestamo, dtFechaDevolucion;

    @FXML
    ComboBox cmbVigencia, cmbEmpleado, cmbCliente, cmbLibro;

    @FXML
    TableView tblPrestamo;

    @FXML
    TableColumn colId, colFechaP, colFechaD, colVigencia, colEmpleado, colCliente, colLibro;

    @Autowired
    PrestamoService prestamoService;

    @Autowired
    EmpleadoService empleadoService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    LibroService libroService;

    @Setter
    private Main stage;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
        cmbVigencia.setItems(listarVigencia());
        cmbEmpleado.setItems(listarEmpleados());
        cmbCliente.setItems(listarClientes());
        cmbLibro.setItems(listarLibros());
    }

     public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(tfId.getText().isBlank()){
                agregarPrestamo();
            }else{
                editarPrestamo();
            }
        }else if(event.getSource() == btnLimpiar){
            LimpiarFormEditar();
        }else if(event.getSource() == btnRegresar){
            stage.indexView();
        }else if(event.getSource() == btnEliminar){
            eliminarPrestamo();
        }
    }

    public void cargarDatos(){
        tblPrestamo.setItems(listarPrestamos());
        colId.setCellValueFactory(new PropertyValueFactory<Prestamo, Long>("id"));
        colFechaP.setCellValueFactory(new PropertyValueFactory<Prestamo, LocalDate>("fechaDePrestamo"));
        colFechaD.setCellValueFactory(new PropertyValueFactory<Prestamo, LocalDate>("fechaDeDevolucion"));
        colVigencia.setCellValueFactory(new PropertyValueFactory<Prestamo, Boolean>("vigencia"));
        colEmpleado.setCellValueFactory(new PropertyValueFactory<Prestamo, Empleado>("empleado"));
        colCliente.setCellValueFactory(new PropertyValueFactory<Prestamo, Cliente>("cliente"));
        colLibro.setCellValueFactory(new PropertyValueFactory<Prestamo, Libro>("libros"));
        
    }

    public ObservableList<Prestamo> listarPrestamos(){
        return FXCollections.observableList(prestamoService.listarPrestamos());
    }

    public ObservableList<Empleado> listarEmpleados(){
        return FXCollections.observableList(empleadoService.listarEmpleados());
    }

    public ObservableList<Cliente> listarClientes(){
        return FXCollections.observableList(clienteService.listarClientes());
    }

    public ObservableList<Libro> listarLibros(){
        return FXCollections.observableList(libroService.listarLibros());
    }

    public ObservableList<String> listarVigencia(){
        return FXCollections.observableArrayList("Vigente", "No vigente");
    }

    public void cargarFormEditar(){
        Prestamo prestamo = (Prestamo)tblPrestamo.getSelectionModel().getSelectedItem();
        if(prestamo != null){
            tfId.setText(Long.toString(prestamo.getId()));
            dtFechaPrestamo.setValue(prestamo.getFechaDePrestamo());
            dtFechaDevolucion.setValue(prestamo.getFechaDeDevolucion());
            cmbVigencia.getSelectionModel().select(prestamo.getVigencia() ? "Vigente" : "No vigente");
            cmbEmpleado.getSelectionModel().select(prestamo.getEmpleado());
            cmbCliente.getSelectionModel().select(prestamo.getCliente());
            cmbLibro.getSelectionModel().select(prestamo.getLibros());
            
        }
    }

    public void LimpiarFormEditar(){
        tfId.clear();
        dtFechaPrestamo.setValue(null);
        dtFechaDevolucion.setValue(null);
        cmbVigencia.getSelectionModel().clearSelection();
        cmbEmpleado.getSelectionModel().clearSelection();
        cmbCliente.getSelectionModel().clearSelection();
        cmbLibro.getSelectionModel().clearSelection();
    }

    public void agregarPrestamo(){
        Prestamo prestamo = new Prestamo();
        prestamo.setFechaDePrestamo(dtFechaPrestamo.getValue());
        prestamo.setFechaDeDevolucion(dtFechaDevolucion.getValue());
        prestamo.setVigencia(cmbVigencia.getSelectionModel().getSelectedItem().equals("vigente"));
        prestamo.setEmpleado((Empleado)cmbEmpleado.getSelectionModel().getSelectedItem());
        prestamo.setCliente((Cliente)cmbCliente.getSelectionModel().getSelectedItem());
        prestamo.setLibros((List<Libro>)cmbLibro.getSelectionModel().getSelectedItem());
        prestamoService.guardarPrestamo(prestamo);
        cargarDatos();
    }

    public void editarPrestamo(){
        Prestamo prestamo = prestamoService.buscarPrestamoPorId(Long.parseLong(tfId.getText()));
        prestamo.setFechaDePrestamo(dtFechaPrestamo.getValue());
        prestamo.setFechaDeDevolucion(dtFechaDevolucion.getValue());
        prestamo.setVigencia(cmbVigencia.getSelectionModel().getSelectedItem().equals("vigente"));
        prestamo.setEmpleado((Empleado)cmbEmpleado.getSelectionModel().getSelectedItem());
        prestamo.setCliente((Cliente)cmbCliente.getSelectionModel().getSelectedItem());
        prestamo.setLibros((List<Libro>)cmbLibro.getSelectionModel().getSelectedItem());
        prestamoService.guardarPrestamo(prestamo);
        cargarDatos();
    }

    public void eliminarPrestamo(){
        Prestamo prestamo = prestamoService.buscarPrestamoPorId(Long.parseLong(tfId.getText()));
        prestamoService.eliminarPrestamo(prestamo);
        cargarDatos();
    }
}