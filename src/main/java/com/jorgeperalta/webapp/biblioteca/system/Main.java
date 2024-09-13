package com.jorgeperalta.webapp.biblioteca.system;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.jorgeperalta.webapp.biblioteca.BibliotecaApplication;
import com.jorgeperalta.webapp.biblioteca.controller.FXController.CategoriaControllerFx;
import com.jorgeperalta.webapp.biblioteca.controller.FXController.ClienteControllerFx;
import com.jorgeperalta.webapp.biblioteca.controller.FXController.IndexController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

    private ConfigurableApplicationContext applicationContext;
    private Stage stage;
    private Scene scene;

    @Override
    public void init(){
        this.applicationContext = new SpringApplicationBuilder(BibliotecaApplication.class).run();
    }

    @Override
    public void start(Stage stage) throws Exception {
       this.stage = stage;
       stage.setTitle("Biblioteca Kinal");
       indexView();
       stage.show();
    }

    public Initializable switchScene(String fxmlName, int width, int height) throws IOException{
        Initializable resultado = null;
        FXMLLoader loader = new FXMLLoader();

        loader.setControllerFactory(applicationContext:: getBean);
        InputStream archivo  =  Main.class.getResourceAsStream("/templates/" + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("/templates/" + fxmlName));

        scene = new Scene((AnchorPane) loader.load(archivo),width,height);
        stage.setScene(scene);
        stage.sizeToScene();

        resultado = (Initializable)loader.getController();
        return resultado;
    }

    public void indexView(){
        try {
            IndexController indexView = (IndexController)switchScene("index.fxml", 1200, 700);
            indexView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void categoriaView(){
        try {
            CategoriaControllerFx categoriaView = (CategoriaControllerFx)switchScene("categoriaView.fxml", 1000, 700);
            categoriaView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clienteView(){
        try {
            ClienteControllerFx clienteView = (ClienteControllerFx)switchScene("clienteView.fxml", 1000, 700);
            clienteView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
