package com.example.proyectofinal_icc303;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    public ImageView addCarImage() {
        ImageView newCar = new ImageView();
        newCar.setImage(new Image("file:src/main/resources/imagenes/car2.png"));
        newCar.setFitHeight(150);
        newCar.setFitWidth(150);
        newCar.setLayoutX(0);
        newCar.setLayoutY(0);

        Platform.runLater(() -> anchorPane.getChildren().add(newCar));

        return newCar;
    }

}