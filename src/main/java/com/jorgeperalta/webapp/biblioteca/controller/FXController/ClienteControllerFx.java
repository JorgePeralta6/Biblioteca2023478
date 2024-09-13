package com.jorgeperalta.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jorgeperalta.webapp.biblioteca.model.Cliente;
import com.jorgeperalta.webapp.biblioteca.service.ClienteService;
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
public class ClienteControllerFx implements Initializable {
    @FXML
    TextField tfDpi, tfNombreCliente, tfApellido, tfTelefono;
    @FXML
    Button btnGuardar, btnLimpiar, btnEliminar;
    @FXML
    TableView tblCliente;
    @FXML
    TableColumn colDpi, colNombreCliente, colApellido, colTelefono;

    @Setter
    private Main stage;

    @Autowired
    ClienteService clienteService;
    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
    }

    public void handleButtonAction(ActionEvent event){
        if (event.getSource() == btnGuardar) {
            if (tfDpi.getText().isBlank()) {
                agregarCliente(); 
            }else{
                editarCliente();  
            }
        }else if(event.getSource() == btnLimpiar){
            limpiarFormEditar();
        }else if(event.getSource() == btnEliminar){
            eliminarCliente();
        }
    }

    public void cargarDatos(){
        tblCliente.getItems().clear();
        tblCliente.setItems(listarClientes());
        colDpi.setCellValueFactory(new PropertyValueFactory<Cliente,Long>("dpi"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombreCliente"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellidoCliente"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefono"));
    }

    public void cargarFormEditar(){
        Cliente cliente = (Cliente)tblCliente.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            tfDpi.setText(Long.toString(cliente.getDpi()));
            tfNombreCliente.setText(cliente.getNombreCliente());
            tfApellido.setText(cliente.getApellidoCliente());
            tfTelefono.setText(cliente.getTelefono());
        }
    }

    public void limpiarFormEditar(){
        tfDpi.clear();
        tfNombreCliente.clear();
        tfApellido.clear();
        tfTelefono.clear();
    }

    public ObservableList<Cliente> listarClientes(){
        return FXCollections.observableList(clienteService.listarClientes());
    }

    public void agregarCliente(){
        Cliente cliente = new Cliente();
        cliente.setDpi(Long.parseLong(tfDpi.getText()));
        cliente.setNombreCliente(tfNombreCliente.getText());
        cliente.setApellidoCliente(tfApellido.getText());
        cliente.setTelefono(tfTelefono.getText());
        clienteService.guardarCliente(cliente);
        cargarDatos();
    }

    public void editarCliente(){
        Cliente cliente = clienteService.buscarClientePorId(Long.parseLong(tfDpi.getText()));
        cliente.setDpi(Long.parseLong(tfDpi.getText()));
        cliente.setNombreCliente(tfNombreCliente.getText());
        cliente.setApellidoCliente(tfApellido.getText());
        cliente.setTelefono(tfTelefono.getText());
        clienteService.guardarCliente(cliente);
        cargarDatos();
    }

    public void eliminarCliente(){
        Cliente cliente = clienteService.buscarClientePorId(Long.parseLong(tfDpi.getText()));
        clienteService.eliminarCliente(cliente);
        cargarDatos();
    }    

}