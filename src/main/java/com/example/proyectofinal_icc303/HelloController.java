package com.example.proyectofinal_icc303;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

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

    static int cantNorth = 0;
    static int cantSouth = 0;
    static int cantEast = 0;
    static int cantWest = 0;

    private static PriorityBlockingQueue<Vehicle> vehiclesNorth = new PriorityBlockingQueue<>();
    private static PriorityBlockingQueue<Vehicle> vehiclesSouth = new PriorityBlockingQueue<>();
    private static PriorityBlockingQueue<Vehicle> vehiclesEast = new PriorityBlockingQueue<>();
    private static PriorityBlockingQueue<Vehicle> vehiclesWest = new PriorityBlockingQueue<>();
    private static PriorityBlockingQueue<Vehicle> AllVehicles = new PriorityBlockingQueue<>();

    private List<Vehicle> vehicles = new ArrayList<>();
    private PriorityBlockingQueue<Vehicle> vehicleQueue = new PriorityBlockingQueue<>();
    TrafficController trafficController = new TrafficController();
    private int numVehiculos = 0;

    private List<TrafficLight> trafficLights = new ArrayList<>();

    @Override
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cantNorth = 0;
        cantSouth = 0;
        cantEast = 0;
        cantWest = 0;
        //directionGroup = new ToggleGroup();
        //toggleNorth.setToggleGroup(directionGroup);
        //toggleSouth.setToggleGroup(directionGroup);
        //toggleEast.setToggleGroup(directionGroup);
        //toggleWest.setToggleGroup(directionGroup);

        // ESCENARIO 2
        // Semaforos
        Circle circle = new Circle(20, Color.GREEN);
        translateCircle(circle, 355, -108);
        stackContainer.getChildren().add(circle);
        trafficLights.add(new TrafficLight("01", new AtomicBoolean(false), circle));


        Circle circle02 = new Circle(20, Color.GREEN);
        translateCircle(circle02, 0, -108);
        stackContainer.getChildren().add(circle02);
        trafficLights.add(new TrafficLight("02", new AtomicBoolean(false), circle02));


        Circle circle03 = new Circle(20, Color.GREEN);
        translateCircle(circle03, -352, -108);
        stackContainer.getChildren().add(circle03);
        trafficLights.add(new TrafficLight("03", new AtomicBoolean(false), circle03));


        Circle circle04 = new Circle(20, Color.RED);
        translateCircle(circle04, 355, 82);
        stackContainer.getChildren().add(circle04);
        trafficLights.add(new TrafficLight("04", new AtomicBoolean(true), circle04));

        Circle circle05 = new Circle(20, Color.RED);
        translateCircle(circle05, 0, 82);
        stackContainer.getChildren().add(circle05);
        trafficLights.add(new TrafficLight("05", new AtomicBoolean(true), circle05));

        Circle circle06 = new Circle(20, Color.RED);
        translateCircle(circle06, -352, 82);
        stackContainer.getChildren().add(circle06);
        trafficLights.add(new TrafficLight("06", new AtomicBoolean(true), circle06));

        startTrafficLightCycle();

    }

    private void startTrafficLightCycle() {
        new AnimationTimer() {
            long lastUpdate = 0;
            //final long interval = 5000000000L; // 5 seconds in nanoseconds
            final long interval = 6000000000L; // 6 seconds in nanoseconds

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= interval) {
                    updateTrafficLights();
                    lastUpdate = now;
                }
            }
        }.start();
    }

    private void updateTrafficLights() {
        for (TrafficLight light : trafficLights) {
            light.changeLight();
        }
        trafficController_02.setCenterWest();

        System.out.println(trafficController_02.isCenterWest());
    }

    private void translateCircle(Circle circle, double targetX, double targetY) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.1), circle);
        translateTransition.setToX(targetX);
        translateTransition.setToY(targetY);
        translateTransition.play();
    }











    private static boolean CenterWest;
    static int cantCenterWest = 0;
    static int cantPositions = 9;
    static int[] positions = {600, 520, 440, 270, 190, 110, -90, -170, -250};
    static int[] positionsTags = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    TrafficController_02 trafficController_02 = new TrafficController_02();
    private static PriorityBlockingQueue<Vehicle> vehiclesCenterWest = new PriorityBlockingQueue<>();


    public void handleCreateVehicleCenterWest() {
        if(vehiclesCenterWest.size() == 6) {
            return;
        }
        ImageView carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
        carImage.setFitHeight(60);
        carImage.setFitWidth(40);
        Vehicle vehicle = new Vehicle(false, "West", "East", carImage);
        vehiclesCenterWest.add(vehicle);
        cantCenterWest++;
        numVehiculos++;

        vehicle.getImageView().setTranslateX(680);
        vehicle.getImageView().setTranslateY(-45);
        vehicle.getImageView().setRotate(-90);

        stackContainer.getChildren().add(vehicle.getImageView());
        vehicles.add(vehicle);
        AllVehicles.add(vehicle);

        InicialMovementCenterWest(vehicle);
//        trafficController.addVehicle(vehicle);
    }

    public void InicialMovementCenterWest(Vehicle car) {
        trafficController_02.addVehicle(car);

        /*
        int Xpos = 305;

        if (cantCenterWest == 1)
            Xpos = 130;
        else if (cantCenterWest == 2)
            Xpos = 220;
        else if (cantCenterWest == 3)
            Xpos = 310;

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), car.getImageView());
        translateTransition.setToX(Xpos);
        translateTransition.play();
        translateTransition.setOnFinished(event -> {
            trafficController_02.addVehicle(car);
        });*/
    }


    public static void moveCenterWest(Vehicle car) {
        int Xpos = -420;
        int centerInt = car.getCenterInt();

        if(centerInt < cantPositions){
            Xpos = positions[centerInt];
            System.out.println(Arrays.toString(positions));
            positionsTags[centerInt] = 1;
            if(centerInt > 0)
                positionsTags[centerInt - 1] = 0;
            System.out.println(Arrays.toString(positionsTags));
            System.out.println("\n");

            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(3), car.getImageView());
            //vehiclesCenterWest.remove(car);
            //AllVehicles.remove(car);
            translateTransition.setToX(Xpos);



            translateTransition.setOnFinished(event -> {
                //vehiclesCenterWest.remove(car);
                //updatePositionsCenterWest();
                //((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
            });
            translateTransition.play();
            cantCenterWest--;
        }

    }




    static void updatePositionsCenterWest() {
        int Xpos = -420;
        Iterator<Vehicle> iterator = vehiclesCenterWest.iterator();
        while (iterator.hasNext()) {
            Vehicle vehicle = iterator.next();

            // MOVER VEHICULO
            int centerInt = vehicle.getCenterInt();
            int pos = 0;

            if(centerInt < cantPositions){
             //   Xpos = positions[centerInt]; //todo: mejorar posicion


                if(centerInt == 0 && positionsTags[centerInt + 2] == 0)
                {
                    pos = 2;
                }
                else if(centerInt == 0 && positionsTags[centerInt + 1] == 0)
                {
                    pos = 1;
                }
                else
                {
                    pos = 0;

                }


                System.out.println("LUZ VERDE " );
                if(positionsTags[centerInt] == 0 ) {
                    System.out.println(Arrays.toString(positions));
                    positionsTags[centerInt] = 1;
                    if(centerInt > 0)
                        positionsTags[centerInt - 1] = 0;
                    System.out.println(Arrays.toString(positionsTags));
                    System.out.println("\n");

                    TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), vehicle.getImageView());
                    //vehiclesCenterWest.remove(car);
                    //AllVehicles.remove(car);
                    translateTransition.setToX(Xpos);
                    translateTransition.setOnFinished(event -> {
                        //vehiclesCenterWest.remove(car);
                        //updatePositionsCenterWest();
                        //((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
                    });
                    translateTransition.play();

                    cantCenterWest--;
                    //vehicle.setCenterInt(vehicle.getCenterInt() + 1);
                }
            }
        }
    }




    static void updatePositionsCenterWestRed() {
        int Xpos = -420;
        Iterator<Vehicle> iterator = vehiclesCenterWest.iterator();
        while (iterator.hasNext()) {
            Vehicle vehicle = iterator.next();

            // MOVER VEHICULO
            int centerInt = vehicle.getCenterInt();

            if(centerInt < cantPositions){
                if(centerInt != 3 ){
                    Xpos = positions[centerInt];
                    System.out.println("LUZ VERDE " );
                    if(positionsTags[centerInt] == 0 ) {
                        System.out.println(Arrays.toString(positions));
                        positionsTags[centerInt] = 1;
                        if(centerInt > 0)
                            positionsTags[centerInt - 1] = 0;
                        System.out.println(Arrays.toString(positionsTags));
                        System.out.println("\n");

                        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), vehicle.getImageView());
                        //vehiclesCenterWest.remove(car);
                        //AllVehicles.remove(car);
                        translateTransition.setToX(Xpos);
                        translateTransition.setOnFinished(event -> {
                            //vehiclesCenterWest.remove(car);
                            //updatePositionsCenterWest();
                            //((StackPane) car.getImageView().getParent()).getChildren().remove(car.getImageView());
                        });
                        translateTransition.play();

                        cantCenterWest--;
                        vehicle.setCenterInt(vehicle.getCenterInt() + 1);
                    }
                }


            }
        }
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