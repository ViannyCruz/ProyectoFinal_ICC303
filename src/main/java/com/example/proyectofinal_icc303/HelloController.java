package com.example.proyectofinal_icc303;

import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {


    public ImageView backgroundImage;
    @FXML
    private Button createImageButton;

    @FXML
    private Button moveImageButton;

    @FXML
    private ComboBox<String> imageSelector;

    @FXML
    private AnchorPane imageContainer;
    @FXML
    private StackPane stackContainer;

    private List<Vehicle> vehicles = new ArrayList<>();
    private int vehicleCounter = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        moveImageButton.setDisable(true);
        imageSelector.setDisable(true);

        //stackContainer.setPadding(new javafx.geometry.Insets(0));

    }

    public void handleCreateVehicleNorth() {
        //ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Carro.jpg")));
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(false, "South", "North", carImage);

        //carImage.setLayoutX(0);
        // carImage.setLayoutY(300 + (imageCounter * 110));


        vehicle.getImageView().setTranslateX(-45);
        vehicle.getImageView().setTranslateY(-300);

        vehicle.getImageView().setRotate(180);

        //imageContainer.getChildren().add(carImage);
        stackContainer.getChildren().add(vehicle.getImageView());
        vehicles.add(vehicle);

        // Update ComboBox with new image
        imageSelector.getItems().add("Vehicle " + vehicleCounter);
        vehicleCounter++;

        moveImageButton.setDisable(false);
        imageSelector.setDisable(false);
    }
    public void handleCreateVehicleSouth() {
        //ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Carro.jpg")));
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(false, "North", "South", carImage);

        //carImage.setLayoutX(0);
        // carImage.setLayoutY(300 + (imageCounter * 110));


        vehicle.getImageView().setTranslateX(45);
        vehicle.getImageView().setTranslateY(300);

        vehicle.getImageView().setRotate(0);

        //imageContainer.getChildren().add(carImage);
        stackContainer.getChildren().add(vehicle.getImageView());
        vehicles.add(vehicle);

        // Update ComboBox with new image
        imageSelector.getItems().add("Vehicle " + vehicleCounter);
        vehicleCounter++;

        moveImageButton.setDisable(false); // Enable the move button
        imageSelector.setDisable(false); // Enable the image selector
    }
    public void handleCreateVehicleEast() {
        //ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Carro.jpg")));
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(false, "West", "East", carImage);

        //carImage.setLayoutX(0);
        // carImage.setLayoutY(300 + (imageCounter * 110));


        vehicle.getImageView().setTranslateX(305);
        vehicle.getImageView().setTranslateY(-45);

        vehicle.getImageView().setRotate(-90);

        //imageContainer.getChildren().add(carImage);
        stackContainer.getChildren().add(vehicle.getImageView());
        vehicles.add(vehicle);

        // Update ComboBox with new image
        imageSelector.getItems().add("Vehicle " + vehicleCounter);
        vehicleCounter++;

        moveImageButton.setDisable(false);
        imageSelector.setDisable(false);
    }
    public void handleCreateVehicleWest() {
        //ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Carro.jpg")));
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto02.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(false, "East", "West", carImage);

        //carImage.setLayoutX(0);
        // carImage.setLayoutY(300 + (imageCounter * 110));


        vehicle.getImageView().setTranslateX(-305);
        vehicle.getImageView().setTranslateY(45);

        vehicle.getImageView().setRotate(90);

        //imageContainer.getChildren().add(carImage);
        stackContainer.getChildren().add(vehicle.getImageView());
        vehicles.add(vehicle);

        // Update ComboBox with new image
        imageSelector.getItems().add("Vehicle " + vehicleCounter);
        vehicleCounter++;

        moveImageButton.setDisable(false);
        imageSelector.setDisable(false);
    }


    // PARA CODIGO
    @FXML
    public void handleMoveImage(int index, int Xpos, int Ypos) {
        moveImage(index, Xpos, Ypos);
    }

    public void moveImage(int index, int Xpos, int Ypos) {
        if (index >= 0 && index < vehicles.size()) {
            ImageView selectedImage = vehicles.get(index).getImageView();
            animateCar(selectedImage, Xpos, Ypos);
        }
    }

    private void animateCar(ImageView carImage, int Xpos, int Ypos) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), carImage);
        translateTransition.setToX(carImage.getTranslateX() + Xpos);
        translateTransition.setToY(carImage.getTranslateY() + Ypos);
        translateTransition.play();
    }




    // BTN
    @FXML
    public void handleMoveImageBtn() {
        int selectedIndex = imageSelector.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            moveImageBtn(selectedIndex);
        }
    }

    public void moveImageBtn(int index) {
        if (index >= 0 && index < vehicles.size()) {
            ImageView selectedImage = vehicles.get(index).getImageView();
            animateCarBtn(selectedImage);
        }
    }

    private void animateCarBtn(ImageView carImage) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), carImage);
        translateTransition.setToX(carImage.getTranslateX() + 170); // Move 200 pixels to the right
        //translateTransition.setToY(carImage.getTranslateY() + 100); // Move 100 pixels down
        translateTransition.play();
    }




}
