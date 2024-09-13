package com.jorgeperalta.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jorgeperalta.webapp.biblioteca.model.Empleado;
import com.jorgeperalta.webapp.biblioteca.service.EmpleadoService;
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
public class EmpleadoControllerFx implements Initializable {
    @FXML
    TextField tfId, tfNombreE, tfApellidoE, tfTelefonoE, tfDireccion, tfDpiE, tfBuscar;
    @FXML
    Button btnGuardar, btnLimpiar, btnEliminar,btnBuscar, btnRegresar;
    @FXML
    TableView tblEmpleado;
    @FXML
    TableColumn colId, colNombreE, colApellidoE, colTelefonoE, colDireccion, colDpiE;

    @Setter
    private Main stage;

    @Autowired
    EmpleadoService empleadoService;
    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
    }

    @FXML
    public void handleButtonAction(ActionEvent event) {
        if (event.getSource() == btnGuardar) {
            if(tfId.getText().isBlank()){
                agregarEmpleado();
            }else{
                editarEmpleado();
            }
        } else if (event.getSource() == btnLimpiar) {
            limpiarFormEditar();
        } else if (event.getSource() == btnRegresar) {
            stage.indexView();
        } else if (event.getSource() == btnEliminar) {
            eliminarEmpleado();
        } else if (event.getSource() == btnBuscar) {
            tblEmpleado.getItems().clear();
            if (tfBuscar.getText().isBlank()) {
                cargarDatos();
            } else {
                tblEmpleado.getItems().add(buscarEmpleado());
                colId.setCellValueFactory(new PropertyValueFactory<Empleado, Long>("id"));
                colNombreE.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombreEmpleado"));
                colApellidoE.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidoEmpleado"));
                colTelefonoE.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefonoEmpleado"));
                colDireccion.setCellValueFactory(new PropertyValueFactory<Empleado, String>("direccion"));
                colDpiE.setCellValueFactory(new PropertyValueFactory<Empleado, String>("dpi"));
            }
        }
    }

    public void cargarDatos(){
        tblEmpleado.getItems().clear();
        tblEmpleado.setItems(listarEmpleados());
        colId.setCellValueFactory(new PropertyValueFactory<Empleado,Long>("id"));
        colNombreE.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombreEmpleado"));
        colApellidoE.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellidoEmpleado"));
        colTelefonoE.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefonoEmpleado"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empleado, String>("direccion"));
        colDpiE.setCellValueFactory(new PropertyValueFactory<Empleado, String>("dpi"));
    }

    public void cargarFormEditar(){
        Empleado empleado = (Empleado)tblEmpleado.getSelectionModel().getSelectedItem();
        if (empleado != null) {
            tfId.setText(Long.toString(empleado.getId()));
            tfNombreE.setText(empleado.getNombreEmpleado());
            tfApellidoE.setText(empleado.getApellidoEmpleado());
            tfTelefonoE.setText(empleado.getTelefonoEmpleado());
            tfDireccion.setText(empleado.getDireccion());
            tfDpiE.setText(empleado.getDpi());
        }
    }

    public void limpiarFormEditar(){
        tfId.clear();
        tfNombreE.clear();
        tfApellidoE.clear();
        tfTelefonoE.clear();
        tfDireccion.clear();
        tfDpiE.clear();
    }

    public ObservableList<Empleado> listarEmpleados(){
        return FXCollections.observableList(empleadoService.listarEmpleados());
    }

    public void agregarEmpleado(){
        Empleado empleado = new Empleado();
        empleado.setNombreEmpleado(tfNombreE.getText());
        empleado.setApellidoEmpleado(tfApellidoE.getText());
        empleado.setTelefonoEmpleado(tfTelefonoE.getText());
        empleado.setDireccion(tfDireccion.getText());
        empleado.setDpi(tfDpiE.getText());
        empleadoService.guardarEmpleado(empleado);
        cargarDatos();
    }

    public void editarEmpleado(){
        Empleado empleado = empleadoService.buscarEmpleadoPorId(Long.parseLong(tfId.getText()));
        empleado.setNombreEmpleado(tfNombreE.getText());
        empleado.setApellidoEmpleado(tfApellidoE.getText());
        empleado.setTelefonoEmpleado(tfTelefonoE.getText());
        empleado.setDireccion(tfDireccion.getText());
        empleado.setDpi(tfDpiE.getText());


        empleadoService.guardarEmpleado(empleado);
        cargarDatos();
    }

    public void eliminarEmpleado(){
        Empleado empleado = empleadoService.buscarEmpleadoPorId(Long.parseLong(tfId.getText()));
        empleadoService.eliminarEmpleado(empleado);
        cargarDatos();
    }    

    public Empleado buscarEmpleado() {
        return empleadoService.buscarEmpleadoPorId(Long.parseLong(tfBuscar.getText()));
    }

}