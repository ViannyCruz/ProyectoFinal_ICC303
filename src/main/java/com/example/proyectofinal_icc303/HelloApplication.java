package com.example.proyectofinal_icc303;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HelloApplication extends Application {
    // Dentro de la clase HelloApplication, ajustar el método start

@Override
public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
    VBox root = fxmlLoader.load();
    Scene scene = new Scene(root);

    ImageView imageView = (ImageView) root.lookup("#imagenFondo");
    imageView.setFitWidth(Screen.getPrimary().getVisualBounds().getWidth());
    imageView.setFitHeight(Screen.getPrimary().getVisualBounds().getHeight());

    System.out.println("Width: " + Screen.getPrimary().getVisualBounds().getWidth());
    System.out.println("Height: " + Screen.getPrimary().getVisualBounds().getHeight());

    stage.setTitle("Hello!");
    stage.setFullScreen(true);
    stage.setScene(scene);
    stage.show();

    ImageView carro = (ImageView) root.lookup("#imagenCarro");
    carro.setX(700);
    carro.setY(550);



    Intersection intersection = new Intersection("1", true);
    Vehicle vehicle = new Vehicle(1, false, 0, 0, carro);

    HelloController controller = fxmlLoader.getController();
    ImageView carro2 = controller.addCarImage();
    carro2.setX(800);
    carro2.setY(550);
    Vehicle vehicle2 = new Vehicle(2, false, 0, 0, carro2);

    intersection.addVehicle(vehicle);
    intersection.addVehicle(vehicle2);
    TrafficController trafficController = new TrafficController(List.of(intersection), null);
    trafficController.startControl();




}

    public static void main(String[] args) {
        launch(args);
    }
}