package com.example.proyectofinal_icc303;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    public Button pausa;
    @FXML
    private Button createImageButtonNorth;
    @FXML
    private Button createImageButtonSouth;
    @FXML
    private Button createImageButtonEast;
    @FXML
    private Button createImageButtonWest;

    @FXML
    private AnchorPane imageContainer;
    @FXML
    private StackPane stackContainer;

    private List<Vehicle> vehicles = new ArrayList<>();
    private PriorityBlockingQueue<Vehicle> vehicleQueue = new PriorityBlockingQueue<>();
    TrafficController trafficController = new TrafficController();
    private int numVehiculos = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cantNorth = 0;
        cantSouth = 0;
        cantEast = 0;
        cantWest = 0;
    }

    static int cantNorth = 0;
    static int cantSouth = 0;
    static int cantEast = 0;
    static int cantWest = 0;

    private static PriorityBlockingQueue<Vehicle> vehiclesNorth = new PriorityBlockingQueue<>();
    private static PriorityBlockingQueue<Vehicle> vehiclesSouth = new PriorityBlockingQueue<>();
    private static PriorityBlockingQueue<Vehicle> vehiclesEast = new PriorityBlockingQueue<>();
    private static PriorityBlockingQueue<Vehicle> vehiclesWest = new PriorityBlockingQueue<>();
    private static PriorityBlockingQueue<Vehicle> AllVehicles = new PriorityBlockingQueue<>();



    public void handleCreateVehicleNorth() {
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);

        Vehicle vehicle = new Vehicle(false, "South", "North", carImage);
        vehiclesNorth.add(vehicle);
        cantNorth++;
        numVehiculos++;

        vehicle.getImageView().setTranslateX(-45);
        vehicle.getImageView().setTranslateY(-320);
        vehicle.getImageView().setRotate(180);

        stackContainer.getChildren().add(vehicle.getImageView());
        vehicles.add(vehicle);
        AllVehicles.add(vehicle);

        InicialMovementNorth(vehicle);
//        trafficController.addVehicle(vehicle);
    }

    public void InicialMovementNorth(Vehicle car) {
        int Ypos = -320;

        if (cantNorth == 1)
            Ypos = -130;
        else if (cantNorth == 2)
            Ypos = -220;
        else if (cantNorth == 3)
            Ypos = -310;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        translateTransition.setToY(Ypos);
        translateTransition.play();
    }

    public static void moveNorth(Vehicle car) {
        int Ypos = 400;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        vehiclesNorth.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToY(Ypos);
        translateTransition.play();
        cantNorth--;
        updatePositionsNorth();
    }

    private static void updatePositionsNorth() {
        int index = 0;
        for (Vehicle vehicle : vehiclesNorth) {
            int Ypos = -320;

            if (index == 0)
                Ypos = -130;
            else if (index == 1)
                Ypos = -220;
            else if (index == 2)
                Ypos = -310;

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), vehicle.getImageView());
            translateTransition.setToY(Ypos);
            translateTransition.play();
            index++;
        }
    }

    public void handleCreateVehicleSouth() {
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(false, "North", "South", carImage);
        vehiclesSouth.add(vehicle);
        cantSouth++;
        numVehiculos++;

        vehicle.getImageView().setTranslateX(45);
        vehicle.getImageView().setTranslateY(300);
        vehicle.getImageView().setRotate(0);

        stackContainer.getChildren().add(vehicle.getImageView());
        vehicles.add(vehicle);
        AllVehicles.add(vehicle);

        InicialMovementSouth(vehicle);
//        trafficController.addVehicle(vehicle);
    }

    public void InicialMovementSouth(Vehicle car) {
        int Ypos = 300;

        if (cantSouth == 1)
            Ypos = 130;
        else if (cantSouth == 2)
            Ypos = 220;
        else if (cantSouth == 3)
            Ypos = 310;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        translateTransition.setToY(Ypos);
        translateTransition.play();
    }

    public static void moveSouth(Vehicle car) {
        int Ypos = -400;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        vehiclesSouth.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToY(Ypos);
        translateTransition.play();
        cantSouth--;
        updatePositionsSouth();
    }

    private static void updatePositionsSouth() {
        int index = 0;
        for (Vehicle vehicle : vehiclesSouth) {
            int Ypos = 300;

            if (index == 0)
                Ypos = 130;
            else if (index == 1)
                Ypos = 220;
            else if (index == 2)
                Ypos = 310;

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), vehicle.getImageView());
            translateTransition.setToY(Ypos);
            translateTransition.play();
            index++;
        }
    }

    public void handleCreateVehicleEast() {
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(false, "West", "East", carImage);
        vehiclesEast.add(vehicle);
        cantEast++;
        numVehiculos++;

        vehicle.getImageView().setTranslateX(305);
        vehicle.getImageView().setTranslateY(-45);
        vehicle.getImageView().setRotate(-90);

        stackContainer.getChildren().add(vehicle.getImageView());
        vehicles.add(vehicle);
        AllVehicles.add(vehicle);

        InicialMovementEast(vehicle);
//        trafficController.addVehicle(vehicle);
    }

    public void InicialMovementEast(Vehicle car) {
        int Xpos = 305;

        if (cantEast == 1)
            Xpos = 130;
        else if (cantEast == 2)
            Xpos = 220;
        else if (cantEast == 3)
            Xpos = 310;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        translateTransition.setToX(Xpos);
        translateTransition.play();
    }

    public static void moveEast(Vehicle car) {
        int Xpos = -420;


        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        vehiclesEast.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToX(Xpos);
        translateTransition.play();
        cantEast--;
        updatePositionsEast();
    }

    private static void updatePositionsEast() {
        int index = 0;
        for (Vehicle vehicle : vehiclesEast) {
            int Xpos = 305;

            if (index == 0)
                Xpos = 130;
            else if (index == 1)
                Xpos = 220;
            else if (index == 2)
                Xpos = 310;

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), vehicle.getImageView());
            translateTransition.setToX(Xpos);
            translateTransition.play();
            index++;
        }
    }

    public void handleCreateVehicleWest() {
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto02.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(false, "East", "West", carImage);
        vehiclesWest.add(vehicle);
        cantWest++;
        numVehiculos++;

        vehicle.getImageView().setTranslateX(-305);
        vehicle.getImageView().setTranslateY(45);
        vehicle.getImageView().setRotate(90);

        stackContainer.getChildren().add(vehicle.getImageView());
        vehicles.add(vehicle);
        AllVehicles.add(vehicle);

        InicialMovementWest(vehicle);
//        trafficController.addVehicle(vehicle);
    }

    public void InicialMovementWest(Vehicle car) {
        int Xpos = -305;

        if (cantWest == 1)
            Xpos = -130;
        else if (cantWest == 2)
            Xpos = -220;
        else if (cantWest == 3)
            Xpos = -310;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        translateTransition.setToX(Xpos);
        translateTransition.play();

    }

    public static void moveWest(Vehicle car) {

        int Xpos = 420;
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        vehiclesWest.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToX(Xpos);
        translateTransition.play();
        cantWest--;
        updatePositionsWest();

    }

    private static void updatePositionsWest() {
        int index = 0;
        for (Vehicle vehicle : vehiclesWest) {
            int Xpos = -305;

            if (index == 0)
                Xpos = -130;
            else if (index == 1)
                Xpos = -220;
            else if (index == 2)
                Xpos = -310;

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), vehicle.getImageView());
            translateTransition.setToX(Xpos);
            translateTransition.play();
            index++;
        }
    }

    public void pausa() {
        for (Vehicle vehicle : AllVehicles) {
            trafficController.addVehicle(vehicle);
        }


    }

    private void moveCar(Vehicle car, int Xpos, int Ypos) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        translateTransition.setToY(Ypos);
        translateTransition.play();
    }

    @FXML
    public void handleMoveCar(int index, int Xpos, int Ypos) {
        if (index >= 0 && index < vehicles.size()) {
            vehicles.get(index).move(Xpos, Ypos);
        }
    }

    @FXML
    public void handleMoveImageBtn() {
    }

    public void moveImageBtn(int index) {
        if (index >= 0 && index < vehicles.size()) {
            ImageView selectedImage = vehicles.get(index).getImageView();
            animateCarBtn(selectedImage);
        }
    }

    private void animateCarBtn(ImageView carImage) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), carImage);
        translateTransition.setToX(carImage.getTranslateX() + 170);
        translateTransition.play();
    }
}