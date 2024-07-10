package com.example.proyectofinal_icc303;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
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
import java.util.concurrent.PriorityBlockingQueue;

public class HelloController implements Initializable {


    public ImageView backgroundImage;
    public Button moverAutoPrioridad;
    @FXML
    private Button createImageButtonNorth;
    @FXML
    private Button createImageButtonSouth;
    @FXML
    private Button createImageButtonEast;
    @FXML
    private Button createImageButtonWest;

   // @FXML
   // private Button createImageButton;

    //@FXML
    //private Button moveImageButton;

   // @FXML
   // private ComboBox<String> imageSelector;

    @FXML
    private AnchorPane imageContainer;
    @FXML
    private StackPane stackContainer;

    private List<Vehicle> vehicles = new ArrayList<>();
    private int vehicleCounter = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // moveImageButton.setDisable(true);
       // imageSelector.setDisable(true);

        //stackContainer.setPadding(new javafx.geometry.Insets(0));

    }


    // Prueba movimiento
    PriorityBlockingQueue<Vehicle> vehicleQueue = new PriorityBlockingQueue<>();


    public void handleCreateVehicleNorth() {
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(false, "South", "North", carImage);

        vehicleQueue.add(vehicle);

        vehicle.getImageView().setTranslateX(-45);
        vehicle.getImageView().setTranslateY(-300);

        vehicle.getImageView().setRotate(180);

        stackContainer.getChildren().add(vehicle.getImageView());
        vehicles.add(vehicle);

        vehicleCounter++;
        InicialMovementNort(vehicle);

    }
    public void handleCreateVehicleSouth() {
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(false, "North", "South", carImage);
        vehicleQueue.add(vehicle);

        vehicle.getImageView().setTranslateX(45);
        vehicle.getImageView().setTranslateY(300);

        vehicle.getImageView().setRotate(0);

        stackContainer.getChildren().add(vehicle.getImageView());
        vehicles.add(vehicle);

        vehicleCounter++;
        InicialMovementSouth(vehicle);
    }

    public void handleCreateVehicleEast() {
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(false, "West", "East", carImage);
        vehicleQueue.add(vehicle);


        vehicle.getImageView().setTranslateX(305);
        vehicle.getImageView().setTranslateY(-45);

        vehicle.getImageView().setRotate(-90);


        stackContainer.getChildren().add(vehicle.getImageView());
        vehicles.add(vehicle);

        vehicleCounter++;
        InicialMovementEast(vehicle);
    }
    public void handleCreateVehicleWest() {
        //ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Carro.jpg")));
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto02.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(false, "East", "West", carImage);
        vehicleQueue.add(vehicle);

        vehicle.getImageView().setTranslateX(-305);
        vehicle.getImageView().setTranslateY(45);

        vehicle.getImageView().setRotate(90);

        stackContainer.getChildren().add(vehicle.getImageView());
        vehicles.add(vehicle);


        vehicleCounter++;
        InicialMovementWest(vehicle);
    }



    public void InicialMovementNort(Vehicle car){

        int Ypos;

        // Aqui se deberia calcular estos valores para que no se solapen los autos
        Ypos = -130;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        translateTransition.setToY(Ypos);
        translateTransition.play();
    }
    public void InicialMovementSouth(Vehicle car){

        int Ypos;

        // Aqui se deberia calcular estos valores para que no se solapen los autos
        Ypos = 130;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        translateTransition.setToY(Ypos);
        translateTransition.play();
    }

    public void InicialMovementEast(Vehicle car){

        int Xpos;

        // Aqui se deberia calcular estos valores para que no se solapen los autos
        Xpos = 130;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        translateTransition.setToX(Xpos);
        translateTransition.play();
    }
    public void InicialMovementWest(Vehicle car){

        int Xpos;

        // Aqui se deberia calcular estos valores para que no se solapen los autos
        Xpos = -130;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        translateTransition.setToX(Xpos);
        translateTransition.play();
    }

    public void getCar( ) {

        Vehicle vehicle = vehicleQueue.poll();
        assert vehicle != null;
        if(vehicle.getCalle().equals("North"))
        {
            //moveCar(vehicle, 0, 200);
            moveNorth(vehicle);
        }
        else if(vehicle.getCalle().equals("South")){
            moveSouth(vehicle);
        }
        else if(vehicle.getCalle().equals("East")){
            moveEast(vehicle);
        }
        else
        {
            moveWest(vehicle);
        }


        //moveCar(vehicle, 100, 100);

    }

    private void moveCar(Vehicle car, int Xpos, int Ypos) {
       /*
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), car.getImageView());
        translateTransition.setToX(car.getImageView().getTranslateX() + Xpos);
        translateTransition.setToY(car.getImageView().getTranslateY() + Ypos);
        translateTransition.play();
        */

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        //translateTransition.setToX(Xpos);
        translateTransition.setToY(Ypos);
        translateTransition.play();








    }

    private void moveNorth(Vehicle car){
        int Ypos;

        // Aqui se deberia calcular estos valores para que no se solapen los autos
        Ypos = 400;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        translateTransition.setToY(Ypos);
        translateTransition.play();
    }

    private void moveSouth(Vehicle car){
        int Ypos;

        // Aqui se deberia calcular estos valores para que no se solapen los autos
        Ypos = -320;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        translateTransition.setToY(Ypos);
        translateTransition.play();

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), car.getImageView());
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        translateTransition.setOnFinished(event -> {
            fadeTransition.play();
        });
    }



    private void moveEast(Vehicle car){
        int Xpos;

        // Aqui se deberia calcular estos valores para que no se solapen los autos
        Xpos = -420;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        translateTransition.setToX(Xpos);
        translateTransition.play();
    }

    private void moveWest(Vehicle car){
        int Xpos;

        // Aqui se deberia calcular estos valores para que no se solapen los autos
        Xpos = 420;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        translateTransition.setToX(Xpos);
        translateTransition.play();

    }





        // PARA CODIGO
    @FXML
    public void handleMoveCar(int index, int Xpos, int Ypos) {
        if (index >= 0 && index < vehicles.size()) {
            vehicles.get(index).move(Xpos, Ypos);
        }
    }


//    private void moveCar(Vehicle car, int Xpos, int Ypos) {
//        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), carImage);
//        translateTransition.setToX(carImage.getTranslateX() + Xpos);
//        translateTransition.setToY(carImage.getTranslateY() + Ypos);
//        translateTransition.play();
//    }




    // BTN
    @FXML
    public void handleMoveImageBtn() {
       // int selectedIndex = imageSelector.getSelectionModel().getSelectedIndex();
        /*
        if (selectedIndex != -1) {
            moveImageBtn(selectedIndex);
        }*/
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
