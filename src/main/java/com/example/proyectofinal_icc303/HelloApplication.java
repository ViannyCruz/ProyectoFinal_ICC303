package com.example.proyectofinal_icc303;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.PriorityBlockingQueue;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 750, 800);

        stage.setTitle("Avance Proyecto");
        stage.setScene(scene);
        stage.show();

        // Obtener el controlador
        HelloController controller = fxmlLoader.getController();


    }

    public static void main(String[] args) {
        launch();
    }
}
