package com.example.proyectofinal_icc303;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.PriorityBlockingQueue;

public class HelloController implements Initializable {

    public ImageView backgroundImage;

    @FXML
    private StackPane stackContainer;

    @FXML
    private ToggleGroup directionGroup;

    @FXML
    private ToggleButton toggleNorth, toggleSouth, toggleEast, toggleWest;

    @FXML
    private RadioButton emergencia;



    private List<Vehicle> vehicles = new ArrayList<>();
    private PriorityBlockingQueue<Vehicle> vehicleQueue = new PriorityBlockingQueue<>();
    TrafficController trafficController = new TrafficController();
    private int numVehiculos = 0;

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cantNorth = 0;
        cantSouth = 0;
        cantEast = 0;
        cantWest = 0;
        directionGroup = new ToggleGroup();
        toggleNorth.setToggleGroup(directionGroup);
        toggleSouth.setToggleGroup(directionGroup);
        toggleEast.setToggleGroup(directionGroup);
        toggleWest.setToggleGroup(directionGroup);

       // handleCreateVehicle_CenterCenterCenter();
       // handleCreateVehicle_CenterCenterCenter();
       // handleCreateVehicle_CenterCenterCenter();

    }



    //ESCENARIO 2
    TrafficControllerEscenario02 trafficControllerEscenario02 = new TrafficControllerEscenario02();

    // CenterCenterCenter
    private static int[] positions = {100, 180, 260, 440, 520, 600};
    private static int[] positionsTags = {0, 0, 0, 0, 0, 0};
    static int cant_CenterCenterCenter = 0;
    private static PriorityBlockingQueue<Vehicle> vehicles_CenterCenterCenter = new PriorityBlockingQueue<>();
    public void handleCreateVehicle_CenterCenterCenter() {
        if(vehicles_CenterCenterCenter.size() == 6) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
        carImage.setFitHeight(70);
        carImage.setFitWidth(50);

        Vehicle vehicle = new Vehicle(false, "CenterCenterCenter", "North", carImage);
        vehicles_CenterCenterCenter.add(vehicle);
        cant_CenterCenterCenter++;
        numVehiculos++;

        vehicle.getImageView().setTranslateX(600);
        vehicle.getImageView().setTranslateY(-108);
        vehicle.getImageView().setRotate(-90);

        stackContainer.getChildren().add(vehicle.getImageView());
        vehicles.add(vehicle);
        AllVehicles.add(vehicle);
        //InicialMovement_CenterCenterCenter(vehicle);
        trafficControllerEscenario02.addVehicle(vehicle);
        updatePositions_CenterCenterCenter();



    }

    public void InicialMovement_CenterCenterCenter(Vehicle car) {
        int Xpos = 700;

        if (cant_CenterCenterCenter == 1)
            Xpos = 440;
        else if (cant_CenterCenterCenter == 2)
            Xpos = 520;
        else if (cant_CenterCenterCenter == 3)
            Xpos = 600;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), car.getImageView());
        translateTransition.setToX(Xpos);
        translateTransition.play();
        translateTransition.setOnFinished(event -> {
            trafficControllerEscenario02.addVehicle(car);
        });

    }
    static void updatePositions_CenterCenterCenter() {
        int[] newPositionsTags = new int[positionsTags.length];
        System.arraycopy(positionsTags, 0, newPositionsTags, 0, positionsTags.length);

        for (Vehicle vehicle : vehicles_CenterCenterCenter) {
            int currentIndex = -1;
            for (int i = 0; i < positionsTags.length; i++) {
                if (positionsTags[i] == 1 && vehicle.getImageView().getTranslateX() == positions[i]) {
                    currentIndex = i;
                    break;
                }
            }

            if (currentIndex != -1) {
                int nextIndex = (currentIndex + 1) % positionsTags.length;
                while (newPositionsTags[nextIndex] == 1) {
                    nextIndex = (nextIndex + 1) % positionsTags.length;
                }

                TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), vehicle.getImageView());
                translateTransition.setToX(positions[nextIndex]);
                translateTransition.play();

                newPositionsTags[currentIndex] = 0;
                newPositionsTags[nextIndex] = 1;
            }
        }

        System.arraycopy(newPositionsTags, 0, positionsTags, 0, positionsTags.length);
        System.out.println(Arrays.toString(positionsTags));
    }


    public static CompletableFuture<Void> tuti(Vehicle vehicle, boolean green){
        CompletableFuture<Void> future = new CompletableFuture<>();

        /*
        int Xpos = 440;

        if (cant_CenterCenterCenter == 1)
            Xpos = 100;
        else if (cant_CenterCenterCenter == 2)
            Xpos = 180;
        else if (cant_CenterCenterCenter == 3)
            Xpos = 260;
        else if (cant_CenterCenterCenter == 4)
            Xpos = 440;
        else if (cant_CenterCenterCenter == 5)
            Xpos = 520;
        else if (cant_CenterCenterCenter == 6)
            Xpos = 600;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), vehicle.getImageView());
        translateTransition.setToX(Xpos);
        translateTransition.play();

        //positionsTags[cant_CenterCenterCenter - 1] = 1;
        //System.out.println(Arrays.toString(positionsTags));

        translateTransition.setOnFinished(event -> {
            positionsTags[cant_CenterCenterCenter - 1] = 1;
            System.out.println(Arrays.toString(positionsTags));
            future.complete(null);
        });



        /*
        int Xpos = 440;

        if (cant_CenterCenterCenter == 1)
            Xpos = 100;
        else if (cant_CenterCenterCenter == 2)
            Xpos = 180;
        else if (cant_CenterCenterCenter == 3)
            Xpos = 260;
        else if (cant_CenterCenterCenter == 4)
            Xpos = 440;
        else if (cant_CenterCenterCenter == 5)
            Xpos = 520;
        else if (cant_CenterCenterCenter == 6)
            Xpos = 600;
*/
/*
        int Xpos = 100;

        Xpos =  positions[cant_CenterCenterCenter];
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), vehicle.getImageView());
        translateTransition.setToX(Xpos);
        translateTransition.play();

        //positionsTags[cant_CenterCenterCenter - 1] = 1;
        //System.out.println(Arrays.toString(positionsTags));

        translateTransition.setOnFinished(event -> {
            positionsTags[cant_CenterCenterCenter - 1] = 1;
            System.out.println(Arrays.toString(positionsTags));
            future.complete(null);


        });

        cant_CenterCenterCenter++;

        future.complete(null);
*/
        future.complete(null);
        return future;
    }


    // TOY HARTA
    public static void movtuti(Vehicle vehicle, boolean green){

        /*
        int Xpos = 440;

        if (cant_CenterCenterCenter == 1)
            Xpos = 100;
        else if (cant_CenterCenterCenter == 2)
            Xpos = 180;
        else if (cant_CenterCenterCenter == 3)
            Xpos = 260;
        else if (cant_CenterCenterCenter == 4)
            Xpos = 440;
        else if (cant_CenterCenterCenter == 5)
            Xpos = 520;
        else if (cant_CenterCenterCenter == 6)
            Xpos = 600;
*/

        int Xpos = 100;

                Xpos =  positions[cant_CenterCenterCenter];
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), vehicle.getImageView());
        translateTransition.setToX(Xpos);
        translateTransition.play();

        //positionsTags[cant_CenterCenterCenter - 1] = 1;
        //System.out.println(Arrays.toString(positionsTags));

        translateTransition.setOnFinished(event -> {
            positionsTags[cant_CenterCenterCenter - 1] = 1;
            System.out.println(Arrays.toString(positionsTags));
        });

        cant_CenterCenterCenter++;

    }


    public static void remove(Vehicle vehicle){

        cant_CenterCenterCenter--;
    }


    // TOY MUY HARTA
    static void updatePositions() {

        int index = 0;
        for (Vehicle vehicle : vehicles_CenterCenterCenter) {
            int Xpos = -320;

            if (index == 0) {
                Xpos = 100;
            } else if (index == 1) {
                Xpos = 180;
            } else if (index == 2) {
                Xpos = 260;
            }

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), vehicle.getImageView());
            translateTransition.setToX(Xpos);

            SequentialTransition sequentialTransition = new SequentialTransition(translateTransition);
            sequentialTransition.play();
            sequentialTransition.onFinishedProperty();
            index++;
        }
    }

    public static CompletableFuture<Void> movetuma(Vehicle car) {
        CompletableFuture<Void> future = new CompletableFuture<>();

        int Xpos = 0;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        vehicles_CenterCenterCenter.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToX(Xpos);
        translateTransition.setOnFinished(event -> {
            vehicles_CenterCenterCenter.remove(car);
            updatePositions_CenterCenterCenter();
            ((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
            future.complete(null);
        });
        translateTransition.play();
        cant_CenterCenterCenter--;
        future.complete(null);
        return future;
    }








































    private String getSelectedDirection() {
        ToggleButton selectedToggle = (ToggleButton) directionGroup.getSelectedToggle();
        if (selectedToggle != null) {
            return selectedToggle.getText();
        }
        return null;
    }




    @FXML
    private void handleStraight() {
        if(emergencia.isSelected()){
            String direction = getSelectedDirection();
            if (direction != null) {
                switch (direction) {
                    case "Norte":
                        handleCreateEmergencyVehicleNorth();
                        break;
                    case "Sur":
                        handleCreateEmergencyVehicleSouth();
                        break;
                    case "Este":
                        handleCreateEmergencyVehicleEast();
                        break;
                    case "Oeste":
                        handleCreateEmergencyVehicleWest();
                        break;
                }
                }

        } else{
            String direction = getSelectedDirection();
            if (direction != null) {
                switch (direction) {
                    case "Norte":
                        handleCreateVehicleNorth();
                        break;
                    case "Sur":
                        handleCreateVehicleSouth();
                        break;
                    case "Este":
                        handleCreateVehicleEast();
                        break;
                    case "Oeste":
                        handleCreateVehicleWest();
                        break;
                }

            }
        }


    }

    @FXML
    private void handleUTurn() {
        if(emergencia.isSelected()){
            String direction = getSelectedDirection();
            if (direction != null) {
                switch (direction) {
                    case "Norte":
                        handleCreateEmergencyVehicleNorthUTurn();
                        break;
                    case "Sur":
                        handleCreateEmergencyVehicleSouthUTurn();
                        break;
                    case "Este":
                        handleCreateEmergencyVehicleEastUTurn();
                        break;
                    case "Oeste":
                        handleCreateEmergencyVehicleWestUTurn();
                        break;
                }
            }
        }else{
            String direction = getSelectedDirection();
            if (direction != null) {
                switch (direction) {
                    case "Norte":
                        handleCreateVehicleNorthUTurn();
                        break;
                    case "Sur":
                        handleCreateVehicleSouthUTurn();
                        break;
                    case "Este":
                        handleCreateVehicleEastUTurn();
                        break;
                    case "Oeste":
                        handleCreateVehicleWestUTurn();
                        break;
                }
            }
        }

    }

    @FXML
    private void handleLeftTurn() {
        if(emergencia.isSelected()){
            String direction = getSelectedDirection();
            if (direction != null) {
                switch (direction) {
                    case "Norte":
                        handleCreateEmergencyVehicleNorthLeftTurn();
                        break;
                    case "Sur":
                        handleCreateEmergencyVehicleSouthLeftTurn();
                        break;
                    case "Este":
                        handleCreateEmergencyVehicleEastLeftTurn();
                        break;
                    case "Oeste":
                        handleCreateEmergencyVehicleWestLeftTurn();
                        break;
                }
            }
        }else{
            String direction = getSelectedDirection();
            if (direction != null) {
                switch (direction) {
                    case "Norte":
                        handleCreateVehicleNorthLeftTurn();
                        break;
                    case "Sur":
                        handleCreateVehicleSouthLeftTurn();
                        break;
                    case "Este":
                        handleCreateVehicleEastLeftTurn();
                        break;
                    case "Oeste":
                        handleCreateVehicleWestLeftTurn();
                        break;
                }
            }
        }

    }

    @FXML
    private void handleRightTurn() {
        if(emergencia.isSelected()){
            String direction = getSelectedDirection();
            if (direction != null) {
                switch (direction) {
                    case "Norte":
                        handleCreateEmergencyVehicleNorthRightTurn();
                        break;
                    case "Sur":
                        handleCreateEmergencyVehicleSouthRightTurn();
                        break;
                    case "Este":
                        handleCreateEmergencyVehicleEastRightTurn();
                        break;
                    case "Oeste":
                        handleCreateEmergencyVehicleWestRightTurn();
                        break;
                }
            }
        }else{
            String direction = getSelectedDirection();
            if (direction != null) {
                switch (direction) {
                    case "Norte":
                        handleCreateVehicleNorthRightTurn();
                        break;
                    case "Sur":
                        handleCreateVehicleSouthRightTurn();
                        break;
                    case "Este":
                        handleCreateVehicleEastRightTurn();
                        break;
                    case "Oeste":
                        handleCreateVehicleWestRightTurn();
                        break;
                }
            }
        }

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
        if(vehiclesNorth.size() == 3) {
            return;
        }
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

    public void handleCreateEmergencyVehicleNorth() {
        if(vehiclesNorth.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);

        Vehicle vehicle = new Vehicle(true, "South", "North", carImage);
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

    public void handleCreateVehicleNorthUTurn() {
        if(vehiclesNorth.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);

        Vehicle vehicle = new Vehicle(false, "North", "North", carImage);
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

    public void handleCreateEmergencyVehicleNorthUTurn() {
        if(vehiclesNorth.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);

        Vehicle vehicle = new Vehicle(true, "North", "North", carImage);
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

    public void handleCreateVehicleNorthRightTurn() {
        if(vehiclesNorth.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);

        Vehicle vehicle = new Vehicle(false, "West", "North", carImage);
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

    public void handleCreateEmergencyVehicleNorthRightTurn() {
        if(vehiclesNorth.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);

        Vehicle vehicle = new Vehicle(true, "West", "North", carImage);
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

    public void handleCreateVehicleNorthLeftTurn() {
        if(vehiclesNorth.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);

        Vehicle vehicle = new Vehicle(false, "East", "North", carImage);
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

    public void handleCreateEmergencyVehicleNorthLeftTurn() {
        if(vehiclesNorth.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);

        Vehicle vehicle = new Vehicle(true, "East", "North", carImage);
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

        if (cantNorth == 1) {
            Ypos = -130;
        } else if (cantNorth == 2) {
            Ypos = -220;
        } else if (cantNorth == 3) {
            Ypos = -310;
        }

        TranslateTransition initialTransition = new TranslateTransition(Duration.seconds(1), car.getImageView());
        initialTransition.setToY(Ypos);

        initialTransition.setOnFinished(event -> {
            trafficController.addVehicle(car);
        });

        SequentialTransition sequentialTransition = new SequentialTransition(initialTransition);
        sequentialTransition.play();
    }

    public static void moveNorth(Vehicle car) {
        int Ypos = 400;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        vehiclesNorth.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToY(Ypos);
        translateTransition.setOnFinished(event -> {
            vehiclesNorth.remove(car);
            updatePositionsNorth();
            ((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
        });
        translateTransition.play();
        cantNorth--;
//        updatePositionsNorth();
    }

    public static void moveNorthUTurn(Vehicle car) {

        int Ypos = -30;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), car.getImageView());
        vehiclesNorth.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToY(Ypos);
        translateTransition.setOnFinished(event -> {
            car.getImageView().setRotate(90);
            TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(0.5), car.getImageView());
            int Xpos = 40;
            translateTransition2.setToX(Xpos);
            translateTransition2.play();

            translateTransition2.setOnFinished(event2 -> {
                car.getImageView().setRotate(360);
                TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), car.getImageView());
                translateTransition3.setToY(-400);
                translateTransition3.play();

                translateTransition3.setOnFinished(event3 -> {
                    vehiclesNorth.remove(car);
                    updatePositionsNorth();
                    ((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
                });
            });
        });
        translateTransition.play();
        cantNorth--;
        updatePositionsNorth();

    }

    public static void moveNorthRightTurn(Vehicle car) {

        int Ypos = -40;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.6), car.getImageView());
        vehiclesNorth.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToY(Ypos);
        translateTransition.setOnFinished(event -> {
            car.getImageView().setRotate(-90);
            TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(0.2), car.getImageView());

            translateTransition2.play();

            translateTransition2.setOnFinished(event2 -> {
                car.getImageView().setRotate(270);
                TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), car.getImageView());
                translateTransition3.setToX(-400);
                translateTransition3.play();

                translateTransition3.setOnFinished(event3 -> {
                    vehiclesNorth.remove(car);
                    updatePositionsNorth();
                    ((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
                });
            });
        });
        translateTransition.play();
        cantNorth--;
        updatePositionsNorth();

    }

    public static void moveNorthLeftTurn(Vehicle car) {

        int Ypos = 45;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.6), car.getImageView());
        vehiclesNorth.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToY(Ypos);
        translateTransition.setOnFinished(event -> {
            car.getImageView().setRotate(90);
            TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(0.2), car.getImageView());

            translateTransition2.play();

            translateTransition2.setOnFinished(event2 -> {
                car.getImageView().setRotate(-270);
                TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), car.getImageView());
                translateTransition3.setToX(430);
                translateTransition3.play();

                translateTransition3.setOnFinished(event3 -> {
                    vehiclesNorth.remove(car);
                    updatePositionsNorth();
                    ((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
                });
            });
        });
        translateTransition.play();
        cantNorth--;
        updatePositionsNorth();

    }

    static void updatePositionsNorth() {
        int index = 0;
        for (Vehicle vehicle : vehiclesNorth) {
            int Ypos = -320;

            if (index == 0) {
                Ypos = -130;
            } else if (index == 1) {
                Ypos = -220;
            } else if (index == 2) {
                Ypos = -310;
            }

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), vehicle.getImageView());
            translateTransition.setToY(Ypos);

            SequentialTransition sequentialTransition = new SequentialTransition(translateTransition);
            sequentialTransition.play();
            sequentialTransition.onFinishedProperty();
            index++;
        }
    }














    public void handleCreateVehicleSouth() {
        if(vehiclesSouth.size() == 3) {
            return;
        }
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

    public void handleCreateEmergencyVehicleSouth() {
        if(vehiclesSouth.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(true, "North", "South", carImage);
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

    public void handleCreateVehicleSouthUTurn() {
        if(vehiclesSouth.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(false, "South", "South", carImage);
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


    public void handleCreateEmergencyVehicleSouthUTurn() {
        if(vehiclesSouth.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(true, "South", "South", carImage);
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

    public void handleCreateVehicleSouthRightTurn() {
        if(vehiclesSouth.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(false, "East", "South", carImage);
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

    public void handleCreateEmergencyVehicleSouthRightTurn() {
        if(vehiclesSouth.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(true, "East", "South", carImage);
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

    public void handleCreateVehicleSouthLeftTurn() {
        if(vehiclesSouth.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(false, "West", "South", carImage);
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


    public void handleCreateEmergencyVehicleSouthLeftTurn() {
        if(vehiclesSouth.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(true, "West", "South", carImage);
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

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), car.getImageView());
        translateTransition.setToY(Ypos);
        translateTransition.play();
        translateTransition.setOnFinished(event -> {
            trafficController.addVehicle(car);
        });
    }

    public static void moveSouth(Vehicle car) {
        int Ypos = -400;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        vehiclesSouth.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToY(Ypos);
        translateTransition.setOnFinished(event -> {
                    vehiclesSouth.remove(car);
                    updatePositionsSouth();
            ((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
                });
        translateTransition.play();
        translateTransition.setOnFinished(event -> {
            vehiclesSouth.remove(car);
            updatePositionsSouth();
            ((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
        });
        cantSouth--;
        updatePositionsSouth();
    }


    public static void moveSouthUTurn(Vehicle car) {
        int Ypos = 30;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), car.getImageView());
        vehiclesSouth.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToY(Ypos);
        translateTransition.setOnFinished(event -> {
            car.getImageView().setRotate(-90);
            TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(0.5), car.getImageView());
            int Xpos = -40;
            translateTransition2.setToX(Xpos);
            translateTransition2.play();

            translateTransition2.setOnFinished(event2 -> {
                car.getImageView().setRotate(-180);
                TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), car.getImageView());
                translateTransition3.setToY(400);
                translateTransition3.play();

                translateTransition3.setOnFinished(event3 -> {
                    vehiclesSouth.remove(car);
                    updatePositionsSouth();
                    ((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
                });
            });
        });
        translateTransition.play();
        cantSouth--;
        updatePositionsSouth();
    }

    public static void moveSouthRightTurn(Vehicle car) {

        int Ypos = 40;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.6), car.getImageView());
        vehiclesSouth.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToY(Ypos);
        translateTransition.setOnFinished(event -> {
            car.getImageView().setRotate(90);
            TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(0.2), car.getImageView());

            translateTransition2.play();

            translateTransition2.setOnFinished(event2 -> {
                car.getImageView().setRotate(-270);
                TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), car.getImageView());
                translateTransition3.setToX(400);
                translateTransition3.play();

                translateTransition3.setOnFinished(event3 -> {
                    vehiclesSouth.remove(car);
                    updatePositionsSouth();
                    ((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
                });
            });
        });
        translateTransition.play();
        cantSouth--;
        updatePositionsSouth();

    }

    public static void moveSouthLeftTurn(Vehicle car) {

        int Ypos = -45;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.6), car.getImageView());
        vehiclesSouth.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToY(Ypos);
        translateTransition.setOnFinished(event -> {
            car.getImageView().setRotate(-90);
            TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(0.2), car.getImageView());

            translateTransition2.play();

            translateTransition2.setOnFinished(event2 -> {
                car.getImageView().setRotate(270);
                TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), car.getImageView());
                translateTransition3.setToX(-430);
                translateTransition3.play();

                translateTransition3.setOnFinished(event3 -> {
                    vehiclesEast.remove(car);
                    updatePositionsSouth();
                    ((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
                });
            });
        });
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

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), vehicle.getImageView());
            translateTransition.setToY(Ypos);
            translateTransition.play();
            index++;
        }
    }

    public void handleCreateVehicleEast() {
        if(vehiclesEast.size() == 3) {
            return;
        }
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


    public void handleCreateEmergencyVehicleEast() {
        if(vehiclesEast.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(true, "West", "East", carImage);
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


    public void handleCreateVehicleEastUTurn() {
        if(vehiclesEast.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(false, "East", "East", carImage);
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


    public void handleCreateEmergencyVehicleEastUTurn() {
        if(vehiclesEast.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(true, "East", "East", carImage);
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

    public void handleCreateVehicleEastRightTurn() {
        if(vehiclesEast.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(false, "North", "East", carImage);
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

    public void handleCreateEmergencyVehicleEastRightTurn() {
        if(vehiclesEast.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(true, "North", "East", carImage);
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


    public void handleCreateVehicleEastLeftTurn() {
        if(vehiclesEast.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(false, "South", "East", carImage);
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


    public void handleCreateEmergencyVehicleEastLeftTurn() {
        if(vehiclesEast.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(true, "South", "East", carImage);
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

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), car.getImageView());
        translateTransition.setToX(Xpos);
        translateTransition.play();
        translateTransition.setOnFinished(event -> {
            trafficController.addVehicle(car);
        });
    }



    public static void moveEast(Vehicle car) {
        int Xpos = -420;


        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        vehiclesEast.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToX(Xpos);
        translateTransition.setOnFinished(event -> {
            vehiclesEast.remove(car);
            updatePositionsEast();
            ((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
        });
        translateTransition.play();
        cantEast--;
    }

    public static void moveEastUTurn(Vehicle car) {
        int Xpos = 30;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), car.getImageView());
        vehiclesEast.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToX(Xpos);
        translateTransition.setOnFinished(event -> {
            car.getImageView().setRotate(180);
            TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(0.5), car.getImageView());
            int Ypos = 45;
            translateTransition2.setToY(Ypos);
            translateTransition2.play();

            translateTransition2.setOnFinished(event2 -> {
                car.getImageView().setRotate(90);
                TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), car.getImageView());
                translateTransition3.setToX(400);
                translateTransition3.play();

                translateTransition3.setOnFinished(event3 -> {
                    vehiclesEast.remove(car);
                    updatePositionsEast();
                    ((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
                });
            });
        });
        translateTransition.play();
        cantEast--;
        updatePositionsEast();
    }

    public static void moveEastRightTurn(Vehicle car) {

        int Xpos = 40;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.6), car.getImageView());
        vehiclesEast.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToX(Xpos);
        translateTransition.setOnFinished(event -> {
            car.getImageView().setRotate(360);
            TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(0.2), car.getImageView());

            translateTransition2.play();

            translateTransition2.setOnFinished(event2 -> {
                car.getImageView().setRotate(360);
                TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), car.getImageView());
                translateTransition3.setToY(-400);
                translateTransition3.play();

                translateTransition3.setOnFinished(event3 -> {
                    vehiclesEast.remove(car);
                    updatePositionsEast();
                    ((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
                });
            });
        });
        translateTransition.play();
        cantEast--;
        updatePositionsEast();

    }

    public static void moveEastLeftTurn(Vehicle car) {

        int Xpos = -45;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.6), car.getImageView());
        vehiclesEast.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToX(Xpos);
        translateTransition.setOnFinished(event -> {
            car.getImageView().setRotate(-90);
            TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(0.2), car.getImageView());

            translateTransition2.play();

            translateTransition2.setOnFinished(event2 -> {
                car.getImageView().setRotate(180);
                TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), car.getImageView());
                translateTransition3.setToY(430);
                translateTransition3.play();

                translateTransition3.setOnFinished(event3 -> {
                    vehiclesEast.remove(car);
                    updatePositionsEast();
                    ((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
                });
            });
        });
        translateTransition.play();
        cantEast--;
        updatePositionsEast();

    }


    static void updatePositionsEast() {
        int index = 0;
        for (Vehicle vehicle : vehiclesEast) {
            int Xpos = 305;

            if (index == 0)
                Xpos = 130;
            else if (index == 1)
                Xpos = 220;
            else if (index == 2)
                Xpos = 310;

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), vehicle.getImageView());
            translateTransition.setToX(Xpos);
            translateTransition.play();
            index++;
        }
    }

    public void handleCreateVehicleWest() {
        if(vehiclesWest.size() == 3) {
            return;
        }
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


    public void handleCreateEmergencyVehicleWest() {
        if(vehiclesWest.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(true, "East", "West", carImage);
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



    public void handleCreateVehicleWestUTurn() {
        if(vehiclesWest.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto02.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(false, "West", "West", carImage);
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

    public void handleCreateEmergencyVehicleWestUTurn() {
        if(vehiclesWest.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(true, "West", "West", carImage);
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

    public void handleCreateVehicleWestRightTurn() {
        if(vehiclesWest.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto02.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(false, "South", "West", carImage);
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

    public void handleCreateEmergencyVehicleWestRightTurn() {
        if(vehiclesWest.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(true, "South", "West", carImage);
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

    public void handleCreateVehicleWestLeftTurn() {
        if(vehiclesWest.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto02.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(false, "North", "West", carImage);
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


    public void handleCreateEmergencyVehicleWestLeftTurn() {
        if(vehiclesWest.size() == 3) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
        carImage.setFitHeight(80);
        carImage.setFitWidth(60);
        Vehicle vehicle = new Vehicle(true, "North", "West", carImage);
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

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), car.getImageView());
        translateTransition.setToX(Xpos);
        translateTransition.play();
        translateTransition.setOnFinished(event -> {
            trafficController.addVehicle(car);
        });

    }

    public static void moveWest(Vehicle car) {

        int Xpos = 420;
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
        vehiclesWest.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToX(Xpos);
        translateTransition.setOnFinished(event -> {
            vehiclesWest.remove(car);
            updatePositionsWest();
            ((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
        });
        translateTransition.play();
        cantWest--;

    }


    public static void moveWestUTurn(Vehicle car) {
        int Xpos = -30;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), car.getImageView());
        vehiclesWest.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToX(Xpos);
        translateTransition.setOnFinished(event -> {
            car.getImageView().setRotate(360);
            TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(0.5), car.getImageView());
            int Ypos = -45;
            translateTransition2.setToY(Ypos);
            translateTransition2.play();

            translateTransition2.setOnFinished(event2 -> {
                car.getImageView().setRotate(-90);
                TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), car.getImageView());
                translateTransition3.setToX(-400);
                translateTransition3.play();

                translateTransition3.setOnFinished(event3 -> {
                    vehiclesWest.remove(car);
                    updatePositionsWest();
                    ((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
                });
            });
        });
        translateTransition.play();
        cantWest--;
        updatePositionsWest();
    }



    public static void moveWestRightTurn(Vehicle car) {

        int Xpos = -40;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.6), car.getImageView());
        vehiclesWest.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToX(Xpos);
        translateTransition.setOnFinished(event -> {
            car.getImageView().setRotate(-180);
            TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(0.2), car.getImageView());

            translateTransition2.play();

            translateTransition2.setOnFinished(event2 -> {
                car.getImageView().setRotate(-180);
                TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), car.getImageView());
                translateTransition3.setToY(400);
                translateTransition3.play();

                translateTransition3.setOnFinished(event3 -> {
                    vehiclesWest.remove(car);
                    updatePositionsWest();
                    ((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
                });
            });
        });
        translateTransition.play();
        cantWest--;
        updatePositionsWest();

    }


    public static void moveWestLeftTurn(Vehicle car) {

        int Xpos = 45;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.6), car.getImageView());
        vehiclesWest.remove(car);
        AllVehicles.remove(car);
        translateTransition.setToX(Xpos);
        translateTransition.setOnFinished(event -> {
            car.getImageView().setRotate(-360);
            TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(0.2), car.getImageView());

            translateTransition2.play();

            translateTransition2.setOnFinished(event2 -> {
                car.getImageView().setRotate(360);
                TranslateTransition translateTransition3 = new TranslateTransition(Duration.seconds(1), car.getImageView());
                translateTransition3.setToY(-430);
                translateTransition3.play();

                translateTransition3.setOnFinished(event3 -> {
                    vehiclesWest.remove(car);
                    updatePositionsWest();
                    ((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
                });
            });
        });
        translateTransition.play();
        cantWest--;
        updatePositionsWest();

    }












    static void updatePositionsWest() {
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