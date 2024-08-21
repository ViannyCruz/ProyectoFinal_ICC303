package com.example.proyectofinal_icc303;

import javafx.animation.*;
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
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.URL;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class HelloController implements Initializable {

    public ImageView backgroundImage;

    @FXML
    public Button btnCenterWest;

    @FXML
    public Button btnCenterEast;
    public Button btnRightTurnWest;

    private boolean isClickable = true;
    private boolean isOnCooldown = false;
    private boolean isOnCooldownCenterEast = false;
    private boolean isOnCooldownRightWest = false;
  //  private final int COOLDOWN_DURATION = 5000; // Cooldown duration in milliseconds
    private final int COOLDOWN_DURATION = 2000; // Cooldown duration in milliseconds

    @FXML
    private StackPane stackContainer;

    @FXML
    private ToggleGroup directionGroup;

    @FXML
    private ToggleButton toggleNorth, toggleSouth, toggleEast, toggleWest;

    @FXML
    private RadioButton emergencia;

    @FXML
    private RadioButton exit01;

    @FXML
    private RadioButton exit02;

    @FXML
    private RadioButton exit03;

    private ToggleGroup toggleGroup = new ToggleGroup();


    private static boolean emergency_bool =  false;
    private static boolean emergency_bool_CenterEast =  false;
    private static boolean emergency_bool_RightWest =  false;

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

    private static List<TrafficLight> trafficLights = new ArrayList<>();

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

        /*
        exit01.setToggleGroup(toggleGroup);
        exit02.setToggleGroup(toggleGroup);
        exit03.setToggleGroup(toggleGroup);
*/

        toggleGroup = new ToggleGroup();
        exit01.setToggleGroup(toggleGroup);
        exit02.setToggleGroup(toggleGroup);
        exit03.setToggleGroup(toggleGroup);



        btnCenterWest.setOnAction(event -> handleButtonClick());
        btnCenterEast.setOnAction(event -> handleButtonClickCenterEast());
        btnRightTurnWest.setOnAction(event -> handleButtonClickRightEast());

        emergency_bool =  false;
        emergency_bool_CenterEast =  false;
        emergency_bool_RightWest =  false;



        exit01.setSelected(true);

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




    // SISTEMA DE CAMBIO DE LUCES
    private static int currentSecond = 0;
    private void startTrafficLightCycle() {
        new AnimationTimer() {
            long lastUpdate = 0;
            final long interval = 6000000000L; // 6 seconds in nanoseconds

            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 1000000000L) { // 1 second in nanoseconds
                    currentSecond++;
                    lastUpdate = now;

                    if (currentSecond == 6) {
                        updateTrafficLights();
                        currentSecond = 0;
                    }
                }
            }
        }.start();
    }

    public int getCurrentSecond() {
        return currentSecond;
    }

    public static boolean isCurrentSecondLessThanFive() {
        return currentSecond < 5;
    }

    private void updateTrafficLights() {
        for (TrafficLight light : trafficLights) {
            light.changeLight();
        }
        trafficController_02.setCenterWest();

    }

    private void translateCircle(Circle circle, double targetX, double targetY) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.1), circle);
        translateTransition.setToX(targetX);
        translateTransition.setToY(targetY);
        translateTransition.play();
    }


    private void handleButtonClick() {
        if (isOnCooldown) {
            // Button is on cooldown, do nothing or show a message
            System.out.println("Button is on cooldown. Please wait.");
            return;
        }

        // Perform the button action
        handleCreateVehicleCenterWest();

        // Set the cooldown flag
        isOnCooldown = true;

        // Schedule a task to reset the cooldown flag after the specified duration
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(COOLDOWN_DURATION),
                ae -> isOnCooldown = false
        ));
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void handleButtonClickCenterEast() {
        if (isOnCooldownCenterEast) {
            // Button is on cooldown, do nothing or show a message
            System.out.println("Button is on cooldown. Please wait.");
            return;
        }

        // Perform the button action
        handleCreateVehicleCenterEast();

        // Set the cooldown flag
        isOnCooldownCenterEast = true;

        // Schedule a task to reset the cooldown flag after the specified duration
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(COOLDOWN_DURATION),
                ae -> isOnCooldownCenterEast = false
        ));
        timeline.setCycleCount(1);
        timeline.play();
    }



    public String getSelectedExit() {
        if (exit01.isSelected()) {
            return "Exit01";
        } else if (exit02.isSelected()) {
            return "Exit02";
        } else if (exit03.isSelected()) {
            return "Exit03";
        }
        return null; // Return null if none are selected
    }

    @FXML
    private void handleButtonClickRightEast() {
        if (isOnCooldownRightWest) {
            // Button is on cooldown, do nothing or show a message
            System.out.println("Button is on cooldown. Please wait.");
            return;
        }

        // Perform the button action
        handleCreateVehicleWestRight();

        // Set the cooldown flag
        isOnCooldownRightWest = true;

        // Schedule a task to reset the cooldown flag after the specified duration
        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(COOLDOWN_DURATION),
                ae -> isOnCooldownRightWest  = false
        ));
        timeline.setCycleCount(1);
        timeline.play();
    }





    static int cantCenterWest = 0;
    static int cantPositions = 9;
    static int[] positions = {600, 520, 440, 270, 190, 110, -90, -170, -250, -815};
    static int[] positionsTags = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    
    static Vehicle vehicle_emergency;



    static int cantCenterCenterEast = 0;
    static int cantPositionsCenterEast = 9;
    //static int[] positionsCenterEast = {-815, -250, -170, -90, 110, 190, 270, 440, 520, 600};
    static int[] positionsCenterEast = {-600, -520, -440, -270, -190, -110, 90, 170, 250, 815};

    static int[] positionsTagsCenterEast = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    static Vehicle vehicle_emergency_CenterEast;
    
    TrafficController_02 trafficController_02 = new TrafficController_02();
    private static PriorityBlockingQueue<Vehicle> vehiclesCenterWest = new PriorityBlockingQueue<>();
    private static PriorityBlockingQueue<Vehicle> vehiclesCenterEast = new PriorityBlockingQueue<>();





    private static PriorityBlockingQueue<Vehicle> vehiclesWestRight = new PriorityBlockingQueue<>();
    static int cantWestRight = 0;
    static int cantPositionsWestRight = 9;
    static int[] positionsWestRight = {600, 520, 440, 270, 190, 110, -90, -170, -250, -815};
    static int[] positionsTagsWestRight = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    static Vehicle vehicle_emergency_WestRight;





    public void handleCreateVehicleCenterWest() {
        if(vehiclesCenterWest.size() == 9) {
            return;
        }
        ImageView carImage = null;
        Vehicle vehicle = null;
        if(!emergencia.isSelected()) {
            carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
            carImage.setFitHeight(60);
            carImage.setFitWidth(40);
            vehicle = new Vehicle(false, "West", "East", carImage);

            vehiclesCenterWest.add(vehicle);
            cantCenterWest++;
            numVehiculos++;

            vehicle.getImageView().setTranslateX(680);
            vehicle.getImageView().setTranslateY(-108);
            vehicle.getImageView().setRotate(-90);

            stackContainer.getChildren().add(vehicle.getImageView());
            vehicles.add(vehicle);
            AllVehicles.add(vehicle);

            //InicialMovementCenterWest(vehicle);
            trafficController_02.addVehicle(vehicle);

        }else {
            if(!emergency_bool){
                carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
                carImage.setFitHeight(60);
                carImage.setFitWidth(40);
                vehicle = new Vehicle(true, "West", "East", carImage);
                emergency_bool = true;
                vehicle_emergency = vehicle;


                vehiclesCenterWest.add(vehicle);
                cantCenterWest++;
                numVehiculos++;

                vehicle.getImageView().setTranslateX(680);
                vehicle.getImageView().setTranslateY(-108);
                vehicle.getImageView().setRotate(-90);

                stackContainer.getChildren().add(vehicle.getImageView());
                vehicles.add(vehicle);
                AllVehicles.add(vehicle);

                //InicialMovementCenterWest(vehicle);
                trafficController_02.addVehicle(vehicle);
            }
        }






    }

    public void handleCreateVehicleCenterEast() {

        if(vehiclesCenterEast.size() == 9) {
            return;
        }

        ImageView carImage = null;
        Vehicle vehicle = null;
        if(!emergencia.isSelected()) {
            carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
            carImage.setFitHeight(60);
            carImage.setFitWidth(40);
            vehicle = new Vehicle(false, "East", "West", carImage);

            vehiclesCenterEast.add(vehicle);
            cantCenterCenterEast++;
            numVehiculos++;

            vehicle.getImageView().setTranslateX(-680);
            vehicle.getImageView().setTranslateY(83);
            vehicle.getImageView().setRotate(90);

            stackContainer.getChildren().add(vehicle.getImageView());
            vehicles.add(vehicle);
            AllVehicles.add(vehicle);

            //InicialMovementCenterWest(vehicle);
            trafficController_02.addVehicle(vehicle);

        }else{
            if(!emergency_bool_CenterEast){
                carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
                carImage.setFitHeight(60);
                carImage.setFitWidth(40);
                vehicle = new Vehicle(true, "East", "West", carImage);
                emergency_bool = true;
                vehicle_emergency = vehicle;

                vehiclesCenterEast.add(vehicle);
                cantCenterCenterEast++;
                numVehiculos++;

                vehicle.getImageView().setTranslateX(-680);
                vehicle.getImageView().setTranslateY(83);
                vehicle.getImageView().setRotate(90);

                stackContainer.getChildren().add(vehicle.getImageView());
                vehicles.add(vehicle);
                AllVehicles.add(vehicle);

                //InicialMovementCenterWest(vehicle);
                trafficController_02.addVehicle(vehicle);
            }

        }



    }






    public void handleCreateVehicleWestRight() {
        if (vehiclesWestRight.size() == 9) {
            return;
        }

        ImageView carImage = null;
        Vehicle vehicle = null;



        if (!emergencia.isSelected()) {
            carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
            carImage.setFitHeight(60);
            carImage.setFitWidth(40);
            vehicle = new Vehicle(false, "West",  getSelectedExit(), carImage);

            vehiclesWestRight.add(vehicle);
            cantWestRight++;
            numVehiculos++;

            vehicle.getImageView().setTranslateX(680);
            vehicle.getImageView().setTranslateY(-164);
            vehicle.getImageView().setRotate(-90);

            stackContainer.getChildren().add(vehicle.getImageView());
            vehicles.add(vehicle);
            AllVehicles.add(vehicle);

            trafficController_02.addVehicle(vehicle);
        } else {
            if(emergency_bool_RightWest){
                carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
                carImage.setFitHeight(60);
                carImage.setFitWidth(40);
                vehicle = new Vehicle(true, "West",  getSelectedExit(), carImage);
                // System.out.println("CALLE: " + calle);
                emergency_bool_RightWest = true;
                vehicle_emergency_WestRight = vehicle;


                vehiclesWestRight.add(vehicle);
                cantWestRight++;
                numVehiculos++;

                vehicle.getImageView().setTranslateX(680);
                vehicle.getImageView().setTranslateY(-164);
                vehicle.getImageView().setRotate(-90);

                stackContainer.getChildren().add(vehicle.getImageView());
                vehicles.add(vehicle);
                AllVehicles.add(vehicle);

                trafficController_02.addVehicle(vehicle);
            }
            /*
            carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
            carImage.setFitHeight(60);
            carImage.setFitWidth(40);
            vehicle = new Vehicle(true, "West",  getSelectedExit(), carImage);
*/



        }

    }




    private static CountDownLatch latch;


    public static void update(){
        updatePositionsRightWest();

        updatePositionsCenterWest();
        updatePositionsCenterEast();







        if (latch != null) {
            latch.countDown();

        }
    }

    public static void updatePositionsCenterWest() {

        Iterator<Vehicle> iterator = vehiclesCenterWest.iterator();
        while (iterator.hasNext()) {
            System.out.println("EMERGENCY: " + emergency_bool);
            Vehicle vehicle = iterator.next();
            int centerInt = vehicle.getCenterInt();
            int nextPos =0;
            if (centerInt < cantPositions) {


                if(vehicle == vehicle_emergency)
                    nextPos = getNextPositionEmergency(centerInt, cantPositions, positionsTags, emergency_bool, trafficLights.getFirst());
                else
                    nextPos = getNextPosition(centerInt, cantPositions, positionsTags, emergency_bool, vehicle_emergency, trafficLights.getFirst() );

                if (nextPos != -1) {
                    //moveVehicle(vehicle, nextPos);
                    //moveVehicle(vehicle, nextPos, positionsTags, positions);
                    moveVehicle(vehicle, nextPos, positionsTags, positions, vehiclesCenterWest, cantCenterWest, 0);

                }
            }
        }
        /*
        if (latch != null) {
            latch.countDown();

        }
*/
    }

    public static void updatePositionsCenterEast() {

        Iterator<Vehicle> iterator = vehiclesCenterEast.iterator();
        while (iterator.hasNext()) {
            System.out.println("EMERGENCY: " + emergency_bool_CenterEast);
            Vehicle vehicle = iterator.next();
            int centerInt = vehicle.getCenterInt();
            int nextPos =0;
            if (centerInt < cantPositionsCenterEast) {


                //    private static int getNextPositionEmergency(int currentPos, int cantPositions, int [] positionsTags, boolean emergency_bool, TrafficLight trafficLight) {
                if(vehicle == vehicle_emergency_CenterEast)
                    nextPos = getNextPositionEmergency(centerInt, cantPositionsCenterEast, positionsTagsCenterEast, emergency_bool_CenterEast, trafficLights.get(3));
                else
                    nextPos = getNextPosition(centerInt, cantPositionsCenterEast, positionsTagsCenterEast, emergency_bool_CenterEast, vehicle_emergency_CenterEast, trafficLights.get(3));

                if (nextPos != -1) {
                    //moveVehicle(vehicle, nextPos);
                    //private static void moveVehicle(Vehicle vehicle, int nextPos, int []positionsTags, int [] positions, PriorityBlockingQueue vehicles, int cant, int EmergencyChoose) {
                    moveVehicle(vehicle, nextPos, positionsTagsCenterEast, positionsCenterEast, vehiclesCenterEast, cantCenterCenterEast, 1);
                }
            }
        }
        /*
        if (latch != null) {
            latch.countDown();

        }
*/
    }

    public static void updatePositionsRightWest() {

        Iterator<Vehicle> iterator = vehiclesWestRight.iterator();
        while (iterator.hasNext()) {
            System.out.println("EMERGENCY: " + emergency_bool_RightWest);
            Vehicle vehicle = iterator.next();
            int centerInt = vehicle.getCenterInt();
            int nextPos =0;
            if (centerInt < cantPositionsWestRight) {

                //    private static int getNextPosition(int currentPos, int cantPositions, int [] positionsTags, boolean emergency_bool, Vehicle vehicle_emergency, TrafficLight trafficLight) {

                if(vehicle.isEmergency()) {
                    nextPos = getNextPositionEmergency(centerInt, cantPositionsWestRight, positionsTagsWestRight, emergency_bool_RightWest, trafficLights.getFirst());
                    //System.out.println("Center Int: " + centerInt);
                }                    //nextPos = getNextPosition(centerInt, cantPositionsWestRight, positionsTagsWestRight, emergency_bool_RightWest, vehicle_emergency_WestRight, trafficLights.getFirst() );

                else
                    nextPos = getNextPosition(centerInt, cantPositionsWestRight, positionsTagsWestRight, emergency_bool_RightWest, vehicle_emergency_WestRight, trafficLights.getFirst() );

                System.out.println("Next Post: " + nextPos);

                if (nextPos != -1) {
                    //moveVehicle(vehicle, nextPos);
                    //moveVehicle(vehicle, nextPos, positionsTags, positions);
                    //moveVehicle(vehicle, nextPos, positionsTags, positions, vehiclesCenterWest, cantCenterWest, 0);
                    //moveVehicleRightWest(vehicle, nextPos, positionsTagsWestRight, positionsWestRight, vehiclesWestRight, cantWestRight, 0);
                    moveVehicleTuma(vehicle, nextPos, positionsTagsWestRight, positionsWestRight, vehiclesWestRight, cantWestRight, 0);

                }
            }
        }
        /*
        if (latch != null) {
            latch.countDown();

        }
*/
    }


















    public static void setLatch(CountDownLatch latch) {
        HelloController.latch = latch;
    }


/*
    private static int getNextPosition(int currentPos) {
        if (currentPos == 0) {
            if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                return 2;
            if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                return 1;
            return -1;
        } else if (currentPos == 2) {
            if(!emergency_bool)
            {
                if (trafficLights.getFirst().getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()) {
                    if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                        return 3;
                    if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                        return 2;
                    if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                        return 1;
                }

            }else if(isCurrentSecondLessThanFive()){ // Emergencia

                if(vehicle_emergency.getCenterInt() > currentPos)
                {
                    if (trafficLights.getFirst().getCircle().getFill() == Color.GREEN) {
                        if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                            return 3;
                        if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                            return 2;
                        if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                            return 1;
                    }
                }else{
                    if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                        return 3;
                    if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                        return 2;
                    if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                        return 1;
                }
            }

            return -1;
        }  else if (currentPos == 5) {


            if(!emergency_bool)
            {
                if (trafficLights.getFirst().getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()) {
                    if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                        return 3;
                    if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                        return 2;
                    if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                        return 1;
                }
            }else if(isCurrentSecondLessThanFive() ){

                if(vehicle_emergency.getCenterInt() > currentPos)
                {
                    if (trafficLights.getFirst().getCircle().getFill() == Color.GREEN) {
                        if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                            return 3;
                        if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                            return 2;
                        if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                            return 1;
                    }
                }else{
                    if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                        return 3;
                    if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                        return 2;
                    if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                        return 1;
                }

            }

            return -1;

        } else if (currentPos == 8) {
            if(!emergency_bool){
                if (trafficLights.getFirst().getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()) {
                    return 8;
                }
            }
            else if (isCurrentSecondLessThanFive()) {
                if(vehicle_emergency.getCenterInt() > currentPos){
                    if (trafficLights.getFirst().getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()) {
                        return 8;
                    }
                }
                else
                    return 8;
            }


            return -1;
        } else if (currentPos > 0 && currentPos < cantPositions - 1) {
            if (positionsTags[currentPos + 1] == 0)
                return 1;
        }
        return -1;
    }
*/

    private static int getNextPosition(int currentPos, int cantPositions, int [] positionsTags, boolean emergency_bool, Vehicle vehicle_emergency, TrafficLight trafficLight) {
      System.out.println("getNextPosition");
        if (currentPos == 0) {
            if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                return 2;
            if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                return 1;
            return -1;
        } else if (currentPos == 2) {
            if(!emergency_bool)
            {
                // trafficLights.getFirst().getCircle().getFill() == Color.GREEN
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()) {
                    if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                        return 3;
                    if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                        return 2;
                    if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                        return 1;
                }

            }else if(isCurrentSecondLessThanFive()){ // Emergencia

                if(vehicle_emergency.getCenterInt() > currentPos)
                {
                    if (trafficLight.getCircle().getFill() == Color.GREEN) {
                        if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                            return 3;
                        if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                            return 2;
                        if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                            return 1;
                    }
                }else{
                    if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                        return 3;
                    if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                        return 2;
                    if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                        return 1;
                }
            }

            return -1;
        }  else if (currentPos == 5) {


            if(!emergency_bool)
            {
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()) {
                    if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                        return 3;
                    if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                        return 2;
                    if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                        return 1;
                }
            }else if(isCurrentSecondLessThanFive() ){

                if(vehicle_emergency.getCenterInt() > currentPos)
                {
                    if (trafficLight.getCircle().getFill() == Color.GREEN) {
                        if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                            return 3;
                        if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                            return 2;
                        if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                            return 1;
                    }
                }else{
                    if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                        return 3;
                    if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                        return 2;
                    if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                        return 1;
                }

            }

            return -1;

        } else if (currentPos == 8) {
            if(!emergency_bool){
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()) {
                    return 8;
                }
            }
            else if (isCurrentSecondLessThanFive()) {
                if(vehicle_emergency.getCenterInt() > currentPos){
                    if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()) {
                        return 8;
                    }
                }
                else
                    return 8;
            }


            return -1;
        } else if (currentPos > 0 && currentPos < cantPositions - 1) {
            if (positionsTags[currentPos + 1] == 0)
                return 1;
        }
        return -1;
    }







    private static int getNextPositionEmergency(int currentPos, int cantPositions, int [] positionsTags, boolean emergency_bool, TrafficLight trafficLight) {
        System.out.println("getNextPositionEmergency");

        if (currentPos == 0) {
            if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                return 2;
            if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                return 1;
            return -1;
        } else if (currentPos == 2) {
            if(!emergency_bool)
            {
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()) {
                    if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                        return 3;
                    if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                        return 2;
                    if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                        return 1;
                }

            }else if(isCurrentSecondLessThanFive()){ // Emergencia


                    if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                        return 3;
                    if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                        return 2;
                    if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                        return 1;

            }

            return -1;
        }  else if (currentPos == 5) {


            if(!emergency_bool)
            {
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()) {
                    if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                        return 3;
                    if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                        return 2;
                    if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                        return 1;
                }
            }else if(isCurrentSecondLessThanFive() ){
                    if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                        return 3;
                    if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                        return 2;
                    if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                        return 1;
            }

            return -1;

        } else if (currentPos == 8) {
            if(!emergency_bool){
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()) {
                    return 8;
                }
            }
            else if (isCurrentSecondLessThanFive()) {
                return 8;
            }


            return -1;
        } else if (currentPos > 0 && currentPos < cantPositions - 1) {
            if (positionsTags[currentPos + 1] == 0)
                return 1;
        }
        return -1;
    }



    /*
    private static void moveVehicle(Vehicle vehicle, int nextPos) {

        int newCenterInt;
        if (nextPos == 8) {
            newCenterInt = 9;
            positionsTags[newCenterInt - 1] = 0;
        } else {
            newCenterInt = vehicle.getCenterInt() + nextPos;
            positionsTags[newCenterInt] = 1;

            // Ensure that the tag for the current position is set to 0 before the vehicle moves.
            if (vehicle.getCenterInt() >= 0) {
                positionsTags[vehicle.getCenterInt()] = 0;
            }
        }

        int Xpos = positions[newCenterInt];
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.8), vehicle.getImageView());
        translateTransition.setToX(Xpos);
        translateTransition.setOnFinished(event -> {
            vehicle.setCenterInt(newCenterInt);

            if (newCenterInt == 9) {
                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());

                if(vehicle.isEmergency()) {
                    emergency_bool = false;
                    vehiclesCenterWest.remove(vehicle);

                }else{
                    vehiclesCenterWest.remove(vehicle);

                }


            }
        });
        translateTransition.play();

        if (newCenterInt == 9) {
            cantCenterWest--;
            TrafficController_02.removeVehicle(vehicle);
            System.out.println("Car has reached the final position!");
        }

        System.out.println(Arrays.toString(positions));
        System.out.println(Arrays.toString(positionsTags));
        System.out.println("Pos " + nextPos);
        System.out.println("\n");



    }
*/


    /*
    private static int getNextPositionEmergency(int currentPos, int cantPositions, int [] positionsTags, boolean emergency_bool,  TrafficLight trafficLight) {
        System.out.println("getNextPositionEmergency");
        System.out.println("Current Pos: " + currentPos);
        System.out.println("Cant Positions: " + cantPositions);
        System.out.println("Positions Tags: " + Arrays.toString(positionsTags));
        System.out.println("Emergency Bool: " + emergency_bool);

        if (currentPos == 0) {
            if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                return 2;
            if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                return 1;
            return -1;
        } else if (currentPos == 2) {
            if(!emergency_bool)
            {
                // trafficLights.getFirst().getCircle().getFill() == Color.GREEN
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()) {
                    if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                        return 3;
                    if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                        return 2;
                    if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                        return 1;
                }

            }else { // Emergencia

                if(vehicle_emergency.getCenterInt() > currentPos)
                {
                    if (trafficLight.getCircle().getFill() == Color.GREEN) {
                        if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                            return 3;
                        if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                            return 2;
                        if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                            return 1;
                    }
                }else{
                    if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                        return 3;
                    if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                        return 2;
                    if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                        return 1;
                }
            }

            return -1;
        }  else if (currentPos == 5) {


            if(!emergency_bool)
            {
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()) {
                    if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                        return 3;
                    if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                        return 2;
                    if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                        return 1;
                }
            }else {

                if(vehicle_emergency.getCenterInt() > currentPos)
                {
                    if (trafficLight.getCircle().getFill() == Color.GREEN) {
                        if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                            return 3;
                        if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                            return 2;
                        if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                            return 1;
                    }
                }else{
                    if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                        return 3;
                    if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                        return 2;
                    if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                        return 1;
                }

            }

            return -1;

        } else if (currentPos == 8) {
            if(!emergency_bool){
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()) {
                    return 8;
                }
            }
            else{
                if(vehicle_emergency.getCenterInt() > currentPos){
                    if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()) {
                        return 8;
                    }
                }
                else
                    return 8;
            }


            return -1;
        } else if (currentPos > 0 && currentPos < cantPositions - 1) {
            if (positionsTags[currentPos + 1] == 0)
                return 1;
        }
        return -1;
    }

*/




    private static void moveVehicle(Vehicle vehicle, int nextPos, int []positionsTags, int [] positions, PriorityBlockingQueue vehicles, int cant, int cantChoose) {

        System.out.println("moveVehicle");
        int newCenterInt;
        if (nextPos == 8) {
            newCenterInt = 9;
            positionsTags[newCenterInt - 1] = 0;
        } else {
            newCenterInt = vehicle.getCenterInt() + nextPos;
            positionsTags[newCenterInt] = 1;

            // Ensure that the tag for the current position is set to 0 before the vehicle moves.
            if (vehicle.getCenterInt() >= 0) {
                positionsTags[vehicle.getCenterInt()] = 0;
            }
        }

        int Xpos = positions[newCenterInt];
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.8), vehicle.getImageView());
        translateTransition.setToX(Xpos);
        translateTransition.setOnFinished(event -> {
            vehicle.setCenterInt(newCenterInt);

            if (newCenterInt == 9) {
                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());

                if(vehicle.isEmergency()) {

                    if(vehicle.getCalle().equals("East") && vehicle.getDirection().equals("West"))
                        emergency_bool = false;
                    else if(vehicle.getCalle().equals("West") && vehicle.getDirection().equals("East"))
                        emergency_bool_CenterEast = false;


                    vehicles.remove(vehicle);

                }else{
                    vehicles.remove(vehicle);

                }


            }
        });
        translateTransition.play();

        if (newCenterInt == 9) {
           // cant--;
            if(cantChoose == 0)
                cantCenterWest--;
            else
                cantCenterCenterEast--;



            TrafficController_02.removeVehicle(vehicle);
            System.out.println("Car has reached the final position!");
        }

        System.out.println(Arrays.toString(positions));
        System.out.println(Arrays.toString(positionsTags));
        System.out.println("Pos " + nextPos);
        System.out.println("\n");



    }



    private static void moveVehicleTuma(Vehicle vehicle, int nextPos, int[] positionsTags, int[] positions, PriorityBlockingQueue vehicles, int cant, int cantChoose) {
        int newCenterInt;
        if (nextPos == 8) {
            newCenterInt = 9;
            positionsTags[newCenterInt - 1] = 0;
        } else {
            newCenterInt = vehicle.getCenterInt() + nextPos;
            positionsTags[newCenterInt] = 1;

            // Ensure that the tag for the current position is set to 0 before the vehicle moves.
            if (vehicle.getCenterInt() >= 0) {
                positionsTags[vehicle.getCenterInt()] = 0;
            }
        }

        int Xpos = positions[newCenterInt];
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.8), vehicle.getImageView());
        translateTransition.setToX(Xpos);
        translateTransition.setOnFinished(event -> {
            vehicle.setCenterInt(newCenterInt);


            /*
            if (newCenterInt == 9) {
                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());

                if (vehicle.isEmergency()) {
                    if (vehicle.getCalle().equals("East") && vehicle.getDirection().equals("West"))
                        emergency_bool = false;
                    else if (vehicle.getCalle().equals("West") && vehicle.getDirection().equals("East"))
                        emergency_bool_CenterEast = false;

                    vehicles.remove(vehicle);
                } else {
                    vehicles.remove(vehicle);
                }
            }*/


            if (newCenterInt == 2 && vehicle.getCalle().equals("Exit01")) {
                if(vehicle.isEmergency())
                    emergency_bool_RightWest = false;

                vehiclesWestRight.remove(vehicle);
                cantWestRight--;
                positionsTags[2] = 0;

                // Move the car a little bit forward
                TranslateTransition forwardTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
                forwardTransition.setToX(Xpos - 60); // Adjust the value as needed
                forwardTransition.setOnFinished(forwardEvent -> {
                    // Rotate the car
                    RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
                    rotateTransition.setByAngle(90); // Adjust the angle as needed
                    rotateTransition.setOnFinished(rotateEvent -> {
                        // Move the car forward again
                        TranslateTransition forwardAgainTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
                        forwardAgainTransition.setToY( -330); // Adjust the value as needed
                        forwardAgainTransition.setOnFinished(forwardAgainEvent -> {
                            // Delete the car
                            ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());
                            //vehiclesWestRight.remove(vehicle);
                            vehicles.remove(vehicle);
                            TrafficController_02.removeVehicle(vehicle);


                            //positionsTags[2] = 0;

                        });
                        forwardAgainTransition.play();
                    });
                    rotateTransition.play();
                });
                forwardTransition.play();
            }else if(newCenterInt == 5 && vehicle.getCalle().equals("Exit02")){
                if(vehicle.isEmergency())
                    emergency_bool_RightWest = false;

                vehiclesWestRight.remove(vehicle);
                cantWestRight--;
                positionsTags[5] = 0;



                TranslateTransition forwardTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
                forwardTransition.setToX(Xpos - 80); // Adjust the value as needed
                forwardTransition.setOnFinished(forwardEvent -> {
                    // Rotate the car
                    RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
                    rotateTransition.setByAngle(90); // Adjust the angle as needed
                    rotateTransition.setOnFinished(rotateEvent -> {
                        // Move the car forward again
                        TranslateTransition forwardAgainTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
                        forwardAgainTransition.setToY( -330); // Adjust the value as needed
                        forwardAgainTransition.setOnFinished(forwardAgainEvent -> {
                            // Delete the car
                            ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());
                            //vehiclesWestRight.remove(vehicle);
                            vehicles.remove(vehicle);
                            TrafficController_02.removeVehicle(vehicle);


                            //positionsTags[2] = 0;

                        });
                        forwardAgainTransition.play();
                    });
                    rotateTransition.play();
                });
                forwardTransition.play();

            }else if(newCenterInt == 8 && vehicle.getCalle().equals("Exit03")){
                if(vehicle.isEmergency())
                    emergency_bool_RightWest = false;

                vehiclesWestRight.remove(vehicle);
                cantWestRight--;
                positionsTags[8] = 0;


                TranslateTransition forwardTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
                forwardTransition.setToX(Xpos - 80); // Adjust the value as needed
                forwardTransition.setOnFinished(forwardEvent -> {
                    // Rotate the car
                    RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
                    rotateTransition.setByAngle(90); // Adjust the angle as needed
                    rotateTransition.setOnFinished(rotateEvent -> {
                        // Move the car forward again
                        TranslateTransition forwardAgainTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
                        forwardAgainTransition.setToY( -330); // Adjust the value as needed
                        forwardAgainTransition.setOnFinished(forwardAgainEvent -> {
                            // Delete the car
                            ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());
                            //vehiclesWestRight.remove(vehicle);
                            vehicles.remove(vehicle);
                            TrafficController_02.removeVehicle(vehicle);


                            //positionsTags[2] = 0;

                        });
                        forwardAgainTransition.play();
                    });
                    rotateTransition.play();
                });
                forwardTransition.play();

            }




        });
        translateTransition.play();

        /*
        if (newCenterInt == 9) {
            if (cantChoose == 0)
                cantCenterWest--;
            else
                cantCenterCenterEast--;

            TrafficController_02.removeVehicle(vehicle);
            System.out.println("Car has reached the final position!");
        }*/

        System.out.println(Arrays.toString(positions));
        System.out.println(Arrays.toString(positionsTags));
        System.out.println("Pos " + nextPos);
        System.out.println("\n");
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