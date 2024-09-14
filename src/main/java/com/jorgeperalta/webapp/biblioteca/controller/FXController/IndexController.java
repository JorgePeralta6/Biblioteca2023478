package com.jorgeperalta.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;


import org.springframework.stereotype.Component;

import com.jorgeperalta.webapp.biblioteca.system.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import lombok.Setter;

@Component
public class IndexController implements Initializable {
    @FXML
    MenuItem btnMenuCategoria, btnMenuCliente, btnMenuEmpleado, btnMenuLibro, btnMenuPrestamo;

    @Setter
    private Main stage;

    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        
    }

    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnMenuCategoria){
            stage.categoriaView();
        }else if(event.getSource() == btnMenuCliente){
            stage.clienteView();
        }else if(event.getSource() == btnMenuEmpleado){
            stage.empleadoView();
        }else if(event.getSource() == btnMenuLibro){
            stage.libroView();
        }else if(event.getSource() == btnMenuPrestamo){
            stage.prestamoView();
        }
    }
   

}
