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


    static boolean inter01;
    static boolean inter02;
    static boolean inter03;

    static boolean inter01_Right;
    static boolean inter02_Right;
    static boolean inter03_Right;

    static boolean inter01_New;
    static boolean inter02_New;
    static boolean inter03_New;




    static boolean inter01_New_West;
    static boolean inter02_New_West;
    static boolean inter03_New_West;

    static boolean inter01_Right_West;
    static boolean inter02_Right_West;
    static boolean inter03_Right_West;





    static boolean inter01_Left;
    static boolean inter02_Left;
    static boolean inter03_Left;







    public ImageView backgroundImage;

    @FXML
    public Button btnCenterWest;

    @FXML
    public Button btnCenterEast;
    public Button btnRightTurnWest;
    public Button btnLeftTurnWest;
    public Button btnLeftTurnEast;
    public Button btnRightTurnEast;

    private boolean isClickable = true;
    private boolean isOnCooldown = false;
    private boolean isOnCooldownCenterEast = false;
    private boolean isOnCooldownRightWest = false;
    private boolean isOnCooldownRightEast = false;
    private boolean isOnCooldownLeftWest = false;
    private boolean isOnCooldownLeftEast = false;
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
    private static boolean emergency_bool_RightEast =  false;

    private static boolean emergency_bool_LeftWest =  false;
    private static boolean emergency_bool_LeftEast =  false;

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

        inter01 = false;
        inter02 = false;
        inter03 = false;

        inter01_Right = false;
        inter02_Right = false;
        inter03_Right = false;

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
        btnRightTurnEast.setOnAction(event -> handleButtonClickRightWest());
        btnLeftTurnWest.setOnAction(event -> handleButtonClickLeftWest());
        btnLeftTurnEast.setOnAction(event -> handleButtonClickLeftEast());

        emergency_bool =  false;
        emergency_bool_CenterEast =  false;
        emergency_bool_RightWest =  false;
        emergency_bool_RightEast =  false;



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


        //   disableButton(btnCenterWest);
        //    disableButton(btnCenterEast);
       //    disableButton(btnRightTurnEast);
          // disableButton(btnLeftTurnWest);
//disableButton(btnRightTurnWest);
//

    }

    public void disableButton(Button button) {
        button.setDisable(true);
    }

    public static void enableButton(Button button) {
        button.setDisable(false);
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
            System.out.println("Button is on cooldown. Please wait.");
            return;
        }

        handleCreateVehicleCenterWest();

        isOnCooldown = true;
        btnCenterWest.setDisable(true);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(COOLDOWN_DURATION),
                ae -> {
                    isOnCooldown = false;
                    btnCenterWest.setDisable(false);

                }
        ));
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void handleButtonClickCenterEast() {
        if (isOnCooldownCenterEast) {
            System.out.println("Button is on cooldown. Please wait.");
            return;
        }

        handleCreateVehicleCenterEast();

        isOnCooldownCenterEast = true;
        btnCenterEast.setDisable(true);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(COOLDOWN_DURATION),
                ae -> {
                    isOnCooldownCenterEast = false;;
                    btnCenterEast.setDisable(false);

                }
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
        return null;
    }

    @FXML
    private void handleButtonClickRightEast() {
        if (isOnCooldownRightWest) {
            System.out.println("Button is on cooldown. Please wait.");
            return;
        }

        handleCreateVehicleWestRight();

        isOnCooldownRightWest = true;
        btnRightTurnWest.setDisable(true);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(COOLDOWN_DURATION),
                ae -> {
                    isOnCooldownRightWest = false;
                    btnRightTurnWest.setDisable(false);

                }
        ));
        timeline.setCycleCount(1);
        timeline.play();
    }

    @FXML
    private void handleButtonClickLeftWest() {
        if (isOnCooldownLeftWest) {
            System.out.println("Button is on cooldown. Please wait.");
            return;
        }

        handleCreateVehicleWestLeft();

        isOnCooldownLeftWest = true;
        btnLeftTurnWest.setDisable(true);


        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(COOLDOWN_DURATION),
                ae -> {
                    isOnCooldownLeftWest = false;
                    btnLeftTurnWest.setDisable(false);

                }
        ));
        timeline.setCycleCount(1);
        timeline.play();
    }


    @FXML
    private void handleButtonClickLeftEast() {
        if (isOnCooldownLeftEast) {
            System.out.println("Button is on cooldown. Please wait.");
            return;
        }

        handleCreateVehicleEastLeft();

        isOnCooldownLeftEast = true;
        btnLeftTurnEast.setDisable(true);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(COOLDOWN_DURATION),
                ae -> {
                    isOnCooldownLeftEast = false;
                    btnLeftTurnEast.setDisable(false);

                }
        ));
        timeline.setCycleCount(1);
        timeline.play();
    }


    @FXML
    private void handleButtonClickRightWest() {
        if (isOnCooldownRightEast) {
            System.out.println("Button is on cooldown. Please wait.");
            return;
        }

        handleCreateVehicleEastRight();

        isOnCooldownRightEast = true;
        btnRightTurnEast.setDisable(true);

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.millis(COOLDOWN_DURATION),
                ae -> {
                    isOnCooldownRightEast = false;
                    btnRightTurnEast.setDisable(false);

                }
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




    private static PriorityBlockingQueue<Vehicle> vehiclesWestLeft = new PriorityBlockingQueue<>();
    static int cantWestLeft = 0;
    static int cantPositionsWestLeft = 9;
    static int[] positionsWestLeft = {600, 520, 440, 270, 190, 110, -90, -170, -250, -815};
    static int[] positionsTagsWestLeft = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    static Vehicle vehicle_emergency_WestLeft;




    private static PriorityBlockingQueue<Vehicle> vehiclesEastRight = new PriorityBlockingQueue<>();
    static int cantEastRight = 0;
    static int cantPositionsEastRight = 9;
    static int[] positionsEastRight = {-600, -520, -440, -270, -190, -110, 90, 170, 250, 815};
    static int[] positionsTagsEastRight = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    static Vehicle vehicle_emergency_EastRight;


    private static PriorityBlockingQueue<Vehicle> vehiclesEastLeft = new PriorityBlockingQueue<>();
    static int cantEastLeft = 0;
    static int cantPositionsEastLeft = 9;
    static int[] positionsEastLeft = {-600, -520, -440, -270, -190, -110, 90, 170, 250, 815};
    static int[] positionsTagsEastLeft = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    static Vehicle vehicle_emergency_EastLeft;



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
            vehicle = new Vehicle(false, "East", "West", carImage);

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
                vehicle = new Vehicle(true, "East", "West", carImage);
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
            vehicle = new Vehicle(false, "West", "East", carImage);

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
                vehicle = new Vehicle(true, "West", "East", carImage);
                emergency_bool_CenterEast = true;
                vehicle_emergency_CenterEast = vehicle;

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
            vehicle = new Vehicle(false, "East",  getSelectedExit(), carImage);

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
            if(!emergency_bool_RightWest){
                carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
                carImage.setFitHeight(60);
                carImage.setFitWidth(40);
                vehicle = new Vehicle(true, "East",  getSelectedExit(), carImage);
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


    public void handleCreateVehicleWestLeft() {
        if (vehiclesWestLeft.size() == 9) {
            return;
        }

        ImageView carImage = null;
        Vehicle vehicle = null;



        if (!emergencia.isSelected()) {
            carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
            carImage.setFitHeight(60);
            carImage.setFitWidth(40);
            vehicle = new Vehicle(false, "East",  getSelectedExit(), carImage);
            System.out.println("CALLE: " + getSelectedExit());

            vehiclesWestLeft.add(vehicle);
            cantWestLeft++;
            numVehiculos++;

            vehicle.getImageView().setTranslateX(680);
            vehicle.getImageView().setTranslateY(-50);
            vehicle.getImageView().setRotate(-90);

            stackContainer.getChildren().add(vehicle.getImageView());
            vehicles.add(vehicle);
            AllVehicles.add(vehicle);

            trafficController_02.addVehicle(vehicle);
        } else {
            if(!emergency_bool_LeftWest && !emergency_bool_LeftEast){
                carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
                carImage.setFitHeight(60);
                carImage.setFitWidth(40);
                vehicle = new Vehicle(true, "East",  getSelectedExit(), carImage);
                emergency_bool_LeftWest = true;
                emergency_bool_LeftEast = false;
                vehicle_emergency_WestLeft = vehicle;


                vehiclesWestLeft.add(vehicle);
                cantWestLeft++;
                numVehiculos++;

                vehicle.getImageView().setTranslateX(680);
                vehicle.getImageView().setTranslateY(-50);
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




    public void handleCreateVehicleEastRight() {
        if (vehiclesEastRight.size() == 9) {
            return;
        }

        ImageView carImage = null;
        Vehicle vehicle = null;



        if (!emergencia.isSelected()) {
            carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
            carImage.setFitHeight(60);
            carImage.setFitWidth(40);
            vehicle = new Vehicle(false, "West",  getSelectedExit(), carImage);

            vehiclesEastRight.add(vehicle);
            cantEastRight++;
            numVehiculos++;

            vehicle.getImageView().setTranslateX(-680);
            vehicle.getImageView().setTranslateY(140);
            vehicle.getImageView().setRotate(90);

            stackContainer.getChildren().add(vehicle.getImageView());
            vehicles.add(vehicle);
            AllVehicles.add(vehicle);

            trafficController_02.addVehicle(vehicle);
        } else {
            if(!emergency_bool_RightEast){
                carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
                carImage.setFitHeight(60);
                carImage.setFitWidth(40);
                vehicle = new Vehicle(true, "West",  getSelectedExit(), carImage);
                // System.out.println("CALLE: " + calle);
                emergency_bool_RightEast = true;
                vehicle_emergency_EastRight = vehicle;


                vehiclesEastRight.add(vehicle);
                cantEastRight++;
                numVehiculos++;

                vehicle.getImageView().setTranslateX(-680);
                vehicle.getImageView().setTranslateY(140);
                vehicle.getImageView().setRotate(90);

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

    public void handleCreateVehicleEastLeft() {
        if (vehiclesEastLeft.size() == 9) {
            return;
        }

        ImageView carImage = null;
        Vehicle vehicle = null;



        if (!emergencia.isSelected()) {
            carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/Auto.png")));
            carImage.setFitHeight(60);
            carImage.setFitWidth(40);
            vehicle = new Vehicle(false, "West",  getSelectedExit(), carImage);
            System.out.println("CALLE: " + getSelectedExit());

            vehiclesEastLeft.add(vehicle);
            cantEastLeft++;
            numVehiculos++;

            vehicle.getImageView().setTranslateX(-680);
            vehicle.getImageView().setTranslateY(25);
            vehicle.getImageView().setRotate(90);

            stackContainer.getChildren().add(vehicle.getImageView());
            vehicles.add(vehicle);
            AllVehicles.add(vehicle);

            trafficController_02.addVehicle(vehicle);
        } else {
            if(!emergency_bool_LeftEast && !emergency_bool_LeftWest){
                carImage = new ImageView(new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/AutoEmergencia.png")));
                carImage.setFitHeight(60);
                carImage.setFitWidth(40);
                vehicle = new Vehicle(true, "West",  getSelectedExit(), carImage);
                emergency_bool_LeftEast = true;
                emergency_bool_LeftWest = false;
                vehicle_emergency_EastLeft = vehicle;


                vehiclesEastLeft.add(vehicle);
                cantEastLeft++;
                numVehiculos++;

                vehicle.getImageView().setTranslateX(-680);
                vehicle.getImageView().setTranslateY(25);
                vehicle.getImageView().setRotate(90);

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
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        updatePositionsCenterWest();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        updatePositionsCenterEast();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        updatePositionsLeftWest();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        updatePositionsRightEast();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        updatePositionsLeftEast();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("EMERGENCY LEFT EAST: " + emergency_bool_LeftEast);
        System.out.println("EMERGENCY LEFT WEST: " + emergency_bool_LeftWest);


        System.out.println("\n");
        System.out.println("INTER01: " + inter01);
        System.out.println("INTER02: " + inter02);
        System.out.println("INTER03: " + inter03);

        System.out.println("INTER01 RIGHT: " + inter01_Right);
        System.out.println("INTER02 RIGHT: " + inter02_Right);
        System.out.println("INTER03 RIGHT: " + inter03_Right);
        System.out.println("\n");


        if (latch != null) {
            latch.countDown();

        }
    }

    // WEST
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
                    nextPos = getNextPosition_Fix(centerInt, cantPositions, positionsTags, emergency_bool, vehicle_emergency, trafficLights.getFirst() );

                if (nextPos != -1) {
                    //moveVehicle(vehicle, nextPos);
                    //moveVehicle(vehicle, nextPos, positionsTags, positions);
                    // moveVehicle(vehicle, nextPos, positionsTags, positions, vehiclesCenterWest, cantCenterWest, 0);
                    moveVehicle_Fix(vehicle, nextPos, positionsTags, positions, vehiclesCenterWest, cantCenterWest, 0);

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
                    nextPos = getNextPosition_Fix_Right(vehicle, centerInt, cantPositionsWestRight, positionsTagsWestRight, emergency_bool_RightWest, vehicle_emergency_WestRight, trafficLights.getFirst());
                    //System.out.println("Center Int: " + centerInt);
                }                    //nextPos = getNextPosition(centerInt, cantPositionsWestRight, positionsTagsWestRight, emergency_bool_RightWest, vehicle_emergency_WestRight, trafficLights.getFirst() );

                else
                    nextPos = getNextPosition_Fix_Right(vehicle, centerInt, cantPositionsWestRight, positionsTagsWestRight, emergency_bool_RightWest, vehicle_emergency_WestRight, trafficLights.getFirst() );

                System.out.println("Next Post: " + nextPos);

                if (nextPos != -1) {
                    //moveVehicle(vehicle, nextPos);
                    //moveVehicle(vehicle, nextPos, positionsTags, positions);
                    //moveVehicle(vehicle, nextPos, positionsTags, positions, vehiclesCenterWest, cantCenterWest, 0);
                    //moveVehicleRightWest(vehicle, nextPos, positionsTagsWestRight, positionsWestRight, vehiclesWestRight, cantWestRight, 0);
                    moveVehicle_Fix_Right(vehicle, nextPos, positionsTagsWestRight, positionsWestRight, vehiclesWestRight, cantWestRight, 0);

                }
            }
        }
        /*
        if (latch != null) {
            latch.countDown();

        }
*/
    }




    public static void updatePositionsLeftWest() {

        Iterator<Vehicle> iterator = vehiclesWestLeft.iterator();
        while (iterator.hasNext()) {
            System.out.println("emergency_bool_LeftWest: " + emergency_bool_LeftWest);
            Vehicle vehicle = iterator.next();
            int centerInt = vehicle.getCenterInt();
            int nextPos =0;
            if (centerInt < cantPositionsWestLeft) {

                //    private static int getNextPosition(int currentPos, int cantPositions, int [] positionsTags, boolean emergency_bool, Vehicle vehicle_emergency, TrafficLight trafficLight) {

                if(vehicle.isEmergency()) {
                    nextPos = getNextPosition_FixtumaWest(centerInt, cantPositionsWestLeft, positionsTagsWestLeft, emergency_bool_LeftWest, vehicle_emergency_WestLeft, trafficLights.getFirst());
                    //System.out.println("Center Int: " + centerInt);
                }                    //nextPos = getNextPosition(centerInt, cantPositionsWestRight, positionsTagsWestRight, emergency_bool_RightWest, vehicle_emergency_WestRight, trafficLights.getFirst() );

                else
                    nextPos = getNextPosition_FixtumaWest(centerInt, cantPositionsWestLeft, positionsTagsWestLeft, emergency_bool_LeftWest, vehicle_emergency_WestLeft, trafficLights.getFirst() );

                System.out.println("Next Post: " + nextPos);

                if (nextPos != -1) {
                    //moveVehicle(vehicle, nextPos);
                    //moveVehicle(vehicle, nextPos, positionsTags, positions);
                    //moveVehicle(vehicle, nextPos, positionsTags, positions, vehiclesCenterWest, cantCenterWest, 0);
                    // moveVehicle(vehicle, nextPos, positionsTagsWestLeft, positionsWestLeft, vehiclesWestLeft, cantWestLeft, 0);


                    moveVehiclet_FixTurnWest(vehicle, nextPos, positionsTagsWestLeft, positionsWestLeft, vehiclesWestLeft, cantWestLeft, 0);
                    // moveVehicleleft(vehicle, nextPos, positionsTagsWestLeft, positionsWestLeft, vehiclesWestLeft, cantWestLeft, 0);



                }
            }
        }
        /*
        if (latch != null) {
            latch.countDown();

        }
*/
    }
    private static int getNextPosition_FixtumaWest(int currentPos, int cantPositions, int [] positionsTags, boolean emergency_bool, Vehicle vehicle_emergency, TrafficLight trafficLight) {
        System.out.println("getNextPosition");

        if (currentPos == 2) {
            if (inter03 && inter03_Right && inter01_New && inter01_Left) return -1;
        } else if (currentPos == 5) {
            if (inter02 && inter02_Right && inter02_New && inter02_Left) return -1;
        } else if (currentPos == 8) {
            if (inter01 && inter01_Right && inter03_New && inter03_Left) return -1;
        }



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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()   ) {
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
                    if (trafficLight.getCircle().getFill() == Color.GREEN ) {
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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive() ) {
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
                    if (trafficLight.getCircle().getFill() == Color.GREEN ) {
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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive() ) {
                    return 8;
                }
            }
            else if (isCurrentSecondLessThanFive()) {
                if(vehicle_emergency.getCenterInt() > currentPos){
                    if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()  ) {
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
    private static void moveVehiclet_FixTurnWest(Vehicle vehicle, int nextPos, int[] positionsTags, int[] positions, PriorityBlockingQueue vehicles, int cant, int cantChoose) {
        System.out.println("moveVehiclet_FixTurnWest");


        if(vehicle.getCenterInt() == 2)
        {
            inter03 = true;

            inter03_Left = true;
            inter03_Right_West = true;
            inter03_New_West = true;

        }else if(vehicle.getCenterInt() == 5)
        {
            inter02 = true;

            inter02_Left = true;
            inter02_Right_West = true;
            inter02_New_West= true;

        }else if(vehicle.getCenterInt() == 8)
        {
            inter01 = true;

            inter01_Left = true;
            inter01_New_West = true;
            inter01_Right_West = true;

        }

        int newCenterInt;
        if (nextPos == 8) {
            newCenterInt = 9;
            positionsTags[newCenterInt - 1] = 0;
        } else {
            newCenterInt = vehicle.getCenterInt() + nextPos;
            positionsTags[newCenterInt] = 1;

            if (vehicle.getCenterInt() >= 0) {
                positionsTags[vehicle.getCenterInt()] = 0;
            }
        }

        int Xpos = positions[newCenterInt];

        if (vehicle.getCenterInt() == 2 && vehicle.getCalle().equals("Exit01")) {
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[2] - 65); // Adjust the value as needed

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(-90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {





                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());                //vehicles.remove(vehicle);

                vehiclesWestLeft.remove(vehicle);
                cantWestLeft--;

                TrafficController_02.removeVehicle(vehicle);
                positionsTagsWestLeft[newCenterInt] = 0;

                inter03 = false;

                inter03_Left = false;
                inter03_New_West = false;
                inter03_Right_West = false;
                if(vehicle.isEmergency())
                    emergency_bool_LeftWest = false;

            });
            sequentialTransition.play();
        } else if(vehicle.getCenterInt() == 5 && vehicle.getCalle().equals("Exit02")){
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[5] - 65); // Adjust the value as needed

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(-90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {



                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());                //vehicles.remove(vehicle);

                vehiclesWestLeft.remove(vehicle);
                cantWestLeft--;


                TrafficController_02.removeVehicle(vehicle);
                positionsTagsWestLeft[newCenterInt] = 0;

                inter02 = false;

                inter02_Left = false;
                inter02_New_West = false;
                inter02_Right_West = false;
                if(vehicle.isEmergency())
                    emergency_bool_LeftWest = false;

            });
            sequentialTransition.play();
        }else if(vehicle.getCenterInt() == 8 && vehicle.getCalle().equals("Exit03")){
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[8] - 65);

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(-90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {


                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());
                // vehicles.remove(vehicle);

                vehiclesWestLeft.remove(vehicle);
                cantWestLeft--;


                TrafficController_02.removeVehicle(vehicle);
                positionsTagsWestLeft[newCenterInt] = 0;

                inter01 = false;
                inter01_Left = false;
                inter01_New_West = false;
                inter01_Right_West = false;
                if(vehicle.isEmergency())
                    emergency_bool_LeftWest = false;


            });
            sequentialTransition.play();
        }



        else {
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.8), vehicle.getImageView());
            translateTransition.setToX(Xpos);
            translateTransition.setOnFinished(event -> {



                if(vehicle.getCenterInt() == 2)
                {
                    inter03 = false;
                    inter03_Left = false;
                    inter03_New_West = false;
                    inter03_Right_West = false;

                }else if(vehicle.getCenterInt() == 5)
                {
                    inter02 = false;

                    inter02_Left = false;
                    inter02_New_West = false;
                    inter02_Right_West = false;

                }else if(vehicle.getCenterInt() == 8)
                {
                    inter01 = false;

                    inter01_Left = false;
                    inter01_New_West = false;
                    inter01_Right_West = false;

                }

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
            });
            translateTransition.play();
        }

        /*
        if (newCenterInt == 9) {
            if (cantChoose == 0)
                cantCenterWest--;
            else
                cantCenterCenterEast--;

            TrafficController_02.removeVehicle(vehicle);
            System.out.println("Car has reached the final position!");
        }
*/
        System.out.println(Arrays.toString(positions));
        System.out.println(Arrays.toString(positionsTags));
        System.out.println("Pos " + nextPos);
        System.out.println("\n");
    }


    public static void updatePositionsLeftEast() {

        Iterator<Vehicle> iterator = vehiclesEastLeft.iterator();
        while (iterator.hasNext()) {
            System.out.println("emergency_bool_LeftEast: " + emergency_bool_LeftEast);
            Vehicle vehicle = iterator.next();
            int centerInt = vehicle.getCenterInt();
            int nextPos =0;
            if (centerInt < cantPositionsEastLeft) {

                //    private static int getNextPosition(int currentPos, int cantPositions, int [] positionsTags, boolean emergency_bool, Vehicle vehicle_emergency, TrafficLight trafficLight) {

                if(vehicle.isEmergency()) {
                    nextPos = getNextPositionEmergencytumalditamadre(centerInt, cantPositionsEastLeft, positionsTagsEastLeft, emergency_bool_LeftEast, trafficLights.get(3));
                    //System.out.println("Center Int: " + centerInt);
                }                    //nextPos = getNextPosition(centerInt, cantPositionsWestRight, positionsTagsWestRight, emergency_bool_RightWest, vehicle_emergency_WestRight, trafficLights.getFirst() );

                else
                    nextPos = getNextPosition_Fixtuma(centerInt, cantPositionsEastLeft, positionsTagsEastLeft, emergency_bool_LeftEast, vehicle_emergency_EastLeft, trafficLights.get(3) );

                System.out.println("Next Post: " + nextPos);

                if (nextPos != -1) {
                    //moveVehicle(vehicle, nextPos);
                    //moveVehicle(vehicle, nextPos, positionsTags, positions);
                    //moveVehicle(vehicle, nextPos, positionsTags, positions, vehiclesCenterWest, cantCenterWest, 0);
                    // moveVehicle(vehicle, nextPos, positionsTagsWestLeft, positionsWestLeft, vehiclesWestLeft, cantWestLeft, 0);


                    moveVehiclet_FixTurn(vehicle, nextPos, positionsTagsEastLeft, positionsEastLeft, vehiclesEastLeft, cantEastLeft, 0);
                    // moveVehicleleft(vehicle, nextPos, positionsTagsWestLeft, positionsWestLeft, vehiclesWestLeft, cantWestLeft, 0);



                }
            }
        }
        /*
        if (latch != null) {
            latch.countDown();

        }
*/
    }




    private static void moveVehicle_Fix(Vehicle vehicle, int nextPos, int[] positionsTags, int[] positions, PriorityBlockingQueue vehicles, int cant, int cantChoose) {
        System.out.println("moveVehicle_Fix");



        if(vehicle.getCenterInt() == 2)
        {
            inter03 = true;

            inter03_Left = true;
            inter03_Right_West = true;
            inter03_New_West = true;

        }else if(vehicle.getCenterInt() == 5)
        {
            inter02 = true;

            inter02_Left = true;
            inter02_Right_West = true;
            inter02_New_West= true;

        }else if(vehicle.getCenterInt() == 8)
        {
            inter01 = true;

            inter01_Left = true;
            inter01_New_West = true;
            inter01_Right_West = true;

        }

        /*
        if(vehicle.getCenterInt() == 2)
        {
            inter01 = true;

        }else if(vehicle.getCenterInt() == 5)
        {
            inter02 = true;

        }else if(vehicle.getCenterInt() == 8)
        {
            inter03 = true;

        }*/



        int newCenterInt;
        if (nextPos == 8) {
            newCenterInt = 9;
            positionsTags[newCenterInt - 1] = 0;
        } else {
            newCenterInt = vehicle.getCenterInt() + nextPos;
            positionsTags[newCenterInt] = 1;

            if (vehicle.getCenterInt() >= 0) {
                positionsTags[vehicle.getCenterInt()] = 0;
            }
        }

        int Xpos = positions[newCenterInt];



        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.8), vehicle.getImageView());
        translateTransition.setToX(Xpos);
        translateTransition.setOnFinished(event -> {


            /*
            if(vehicle.getCenterInt() == 2)
            {
                inter01 = false;

            }else if(vehicle.getCenterInt() == 5)
            {
                inter02 = false;

            }else if(vehicle.getCenterInt() == 8)
            {
                inter03 = false;

            }
*/


            if(vehicle.getCenterInt() == 2)
            {
                inter03 = false;

                inter03_Left = false;
                inter03_Right_West = false;
                inter03_New_West = false;

            }else if(vehicle.getCenterInt() == 5)
            {
                inter02 = false;

                inter02_Left = false;
                inter02_Right_West = false;
                inter02_New_West= false;

            }else if(vehicle.getCenterInt() == 8)
            {
                inter01 = false;

                inter01_Left = false;
                inter01_New_West = false;
                inter01_Right_West = false;

            }



            vehicle.setCenterInt(newCenterInt);









            if (newCenterInt == 9) {
                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());

                if (vehicle.isEmergency()) {
                        emergency_bool = false;


                    vehicles.remove(vehicle);
                } else {
                    vehicles.remove(vehicle);
                }
            }
        });
        translateTransition.play();

        if (newCenterInt == 9) {
            if (cantChoose == 0)
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
    private static int getNextPosition_Fix(int currentPos, int cantPositions, int [] positionsTags, boolean emergency_bool, Vehicle vehicle_emergency, TrafficLight trafficLight) {
        System.out.println("getNextPosition");
        if (currentPos == 2) {
            if (inter03 && inter03_Right && inter01_New && inter01_Left) return -1;
        } else if (currentPos == 5) {
            if (inter02 && inter02_Right && inter02_New && inter02_Left) return -1;
        } else if (currentPos == 8) {
            if (inter01 && inter01_Right && inter03_New && inter03_Left) return -1;
        }

        /*
        if (currentPos == 2) {
            if (inter01) return -1;
        } else if (currentPos == 5) {
            if (inter02) return -1;
        } else if (currentPos == 8) {
            if (inter03) return -1;
        }
*/


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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()   ) {
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
                    if (trafficLight.getCircle().getFill() == Color.GREEN ) {
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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive() ) {
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
                    if (trafficLight.getCircle().getFill() == Color.GREEN ) {
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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive() ) {
                    return 8;
                }
            }
            else if (isCurrentSecondLessThanFive()) {
                if(vehicle_emergency.getCenterInt() > currentPos){
                    if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()  ) {
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


    private static void moveVehicle_Fix_Right(Vehicle vehicle, int nextPos, int[] positionsTags, int[] positions, PriorityBlockingQueue vehicles, int cant, int cantChoose) {
        System.out.println("moveVehicle_Fix_Right");

        int newCenterInt;
        if (nextPos == 8) {
            newCenterInt = 9;
            positionsTags[newCenterInt - 1] = 0;
        } else {
            newCenterInt = vehicle.getCenterInt() + nextPos;
            positionsTags[newCenterInt] = 1;

            if (vehicle.getCenterInt() >= 0) {
                positionsTags[vehicle.getCenterInt()] = 0;
            }
        }

        if(vehicle.getCenterInt() == 2)
        {
            inter01_Right  = true;

        }else if(vehicle.getCenterInt() == 5)
        {
            inter02_Right  = true;

        }else if(vehicle.getCenterInt() == 8)
        {
            inter03_Right  = true;

        }



        int Xpos = positions[newCenterInt];

        if (vehicle.getCenterInt() == 2 && vehicle.getCalle().equals("Exit01")) {
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[2] - 65); // Adjust the value as needed

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(-330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {





                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());                //vehicles.remove(vehicle);

                vehiclesWestRight.remove(vehicle);
                cantWestRight--;

                TrafficController_02.removeVehicle(vehicle);
                positionsTagsWestRight[newCenterInt] = 0;

                inter01_Right = false;
                if(vehicle.isEmergency())
                    emergency_bool_RightWest = false;





            });
            sequentialTransition.play();
        } else if(vehicle.getCenterInt() == 5 && vehicle.getCalle().equals("Exit02")){
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[5] - 65); // Adjust the value as needed

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(-330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {



                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());                //vehicles.remove(vehicle);

                vehiclesWestRight.remove(vehicle);
                cantWestRight--;

                TrafficController_02.removeVehicle(vehicle);
                positionsTagsWestRight[newCenterInt] = 0;

                inter02_Right = false;
                if(vehicle.isEmergency())
                    emergency_bool_RightWest = false;

            });
            sequentialTransition.play();
        }else if(vehicle.getCenterInt() == 8 && vehicle.getCalle().equals("Exit03")){
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[8] - 65);

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(-330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {


                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());                //vehicles.remove(vehicle);

                vehiclesWestRight.remove(vehicle);
                cantWestRight--;

                TrafficController_02.removeVehicle(vehicle);
                positionsTagsWestRight[newCenterInt] = 0;

                inter03_Right = false;
                if(vehicle.isEmergency())
                    emergency_bool_RightWest = false;


            });
            sequentialTransition.play();
        }



        else {
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.8), vehicle.getImageView());
            translateTransition.setToX(Xpos);
            translateTransition.setOnFinished(event -> {



                if(vehicle.getCenterInt() == 2)
                {
                    inter01_Right  = false;

                }else if(vehicle.getCenterInt() == 5)
                {
                    inter02_Right  = false;

                }else if(vehicle.getCenterInt() == 8)
                {
                    inter03_Right = false;

                }

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
            });
            translateTransition.play();
        }

        /*
        if (newCenterInt == 9) {
            if (cantChoose == 0)
                cantCenterWest--;
            else
                cantCenterCenterEast--;

            TrafficController_02.removeVehicle(vehicle);
            System.out.println("Car has reached the final position!");
        }
*/
        System.out.println(Arrays.toString(positions));
        System.out.println(Arrays.toString(positionsTags));
        System.out.println("Pos " + nextPos);
        System.out.println("\n");


    }
    private static int getNextPosition_Fix_Right(Vehicle vehicle, int currentPos, int cantPositions, int [] positionsTags, boolean emergency_bool, Vehicle vehicle_emergency, TrafficLight trafficLight) {
        System.out.println("getNextPosition_Fix_Right");



        if (currentPos == 2 && vehicle.getCalle().equals("Exit01")) {
            if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                return 3;
            if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                return 2;
            if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                return 1;
        }else if(currentPos == 5 && vehicle.getCalle().equals("Exit02")){
            if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                return 3;
            if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                return 2;
            if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                return 1;}
        else if(currentPos == 8 && vehicle.getCalle().equals("Exit03")){

            return 8;
        }

        if (currentPos == 2) {
            if (inter01_Right ) return -1;
        } else if (currentPos == 5) {
            if (inter02_Right ) return -1;
        } else if (currentPos == 8) {
            if (inter03_Right ) return -1;
        }



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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()   ) {
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
                    if (trafficLight.getCircle().getFill() == Color.GREEN ) {
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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive() ) {
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
                    if (trafficLight.getCircle().getFill() == Color.GREEN ) {
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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive() ) {
                    return 8;
                }
            }
            else if (isCurrentSecondLessThanFive()) {
                if(vehicle_emergency.getCenterInt() > currentPos){
                    if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()  ) {
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
    private static int getNextPositionEmergency_Right(int currentPos, int cantPositions, int [] positionsTags, boolean emergency_bool, TrafficLight trafficLight) {
        System.out.println("getNextPositionEmergency_Right");

        if (currentPos == 2) {
            if (inter01_Right) return -1;
        } else if (currentPos == 5) {
            if (inter02_Right) return -1;
        } else if (currentPos == 8) {
            if (inter03_Right) return -1;
        }

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














    private static int getNextPosition_Fixtuma(int currentPos, int cantPositions, int [] positionsTags, boolean emergency_bool, Vehicle vehicle_emergency, TrafficLight trafficLight) {
        System.out.println("getNextPosition");

        if (currentPos == 2) {
            if (inter03 && inter03_Right && inter01_New) return -1;
        } else if (currentPos == 5) {
            if (inter02 && inter02_Right && inter02_New) return -1;
        } else if (currentPos == 8) {
            if (inter01 && inter01_Right && inter03_New) return -1;
        }



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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()   ) {
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
                    if (trafficLight.getCircle().getFill() == Color.GREEN ) {
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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive() ) {
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
                    if (trafficLight.getCircle().getFill() == Color.GREEN ) {
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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive() ) {
                    return 8;
                }
            }
            else if (isCurrentSecondLessThanFive()) {
                if(vehicle_emergency.getCenterInt() > currentPos){
                    if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()  ) {
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
    private static void moveVehiclet_FixTurn(Vehicle vehicle, int nextPos, int[] positionsTags, int[] positions, PriorityBlockingQueue vehicles, int cant, int cantChoose) {
        System.out.println("moveVehiclet_FixTurn");


        if(vehicle.getCenterInt() == 2)
        {
            inter03 = true;
            inter03_Right = true;
            inter03_New = true;


        }else if(vehicle.getCenterInt() == 5)
        {
            inter02 = true;
            inter02_Right = true;
            inter02_New = true;

        }else if(vehicle.getCenterInt() == 8)
        {
            inter01 = true;
            inter01_New = true;
            inter01_Right = true;

        }

        int newCenterInt;
        if (nextPos == 8) {
            newCenterInt = 9;
            positionsTags[newCenterInt - 1] = 0;
        } else {
            newCenterInt = vehicle.getCenterInt() + nextPos;
            positionsTags[newCenterInt] = 1;

            if (vehicle.getCenterInt() >= 0) {
                positionsTags[vehicle.getCenterInt()] = 0;
            }
        }

        int Xpos = positions[newCenterInt];

        if (vehicle.getCenterInt() == 2 && vehicle.getCalle().equals("Exit01")) {
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[2] + 65); // Adjust the value as needed

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(-90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(-330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {





                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());                //vehicles.remove(vehicle);

                vehiclesEastLeft.remove(vehicle);
                cantEastLeft--;

                TrafficController_02.removeVehicle(vehicle);
                positionsTagsEastLeft[newCenterInt] = 0;

                inter03 = false;
                inter03_New = false;
                inter03_Right = false;
                if(vehicle.isEmergency())
                    emergency_bool_LeftEast = false;

            });
            sequentialTransition.play();
        } else if(vehicle.getCenterInt() == 5 && vehicle.getCalle().equals("Exit02")){
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[5] + 65); // Adjust the value as needed

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(-90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(-330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {



                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());                //vehicles.remove(vehicle);

                vehiclesEastLeft.remove(vehicle);
                cantEastLeft--;


                TrafficController_02.removeVehicle(vehicle);
                positionsTagsEastLeft[newCenterInt] = 0;

                inter02 = false;
                inter02_New = false;
                inter02_Right = false;
                if(vehicle.isEmergency())
                    emergency_bool_LeftEast = false;

            });
            sequentialTransition.play();
        }else if(vehicle.getCenterInt() == 8 && vehicle.getCalle().equals("Exit03")){
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[8] + 65);

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(-90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(-330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {


                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());
                // vehicles.remove(vehicle);

                vehiclesEastLeft.remove(vehicle);
                cantEastLeft--;


                TrafficController_02.removeVehicle(vehicle);
                positionsTagsEastLeft[newCenterInt] = 0;

                inter01 = false;
                inter01_New = false;
                inter01_Right = false;
                if(vehicle.isEmergency())
                    emergency_bool_LeftEast = false;


            });
            sequentialTransition.play();
        }



        else {
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.8), vehicle.getImageView());
            translateTransition.setToX(Xpos);
            translateTransition.setOnFinished(event -> {



                if(vehicle.getCenterInt() == 2)
                {
                    inter03 = false;
                    inter03_New = false;
                    inter03_Right = false;

                }else if(vehicle.getCenterInt() == 5)
                {
                    inter02 = false;
                    inter02_New = false;
                    inter02_Right = false;

                }else if(vehicle.getCenterInt() == 8)
                {
                    inter01 = false;
                    inter01_New = false;
                    inter01_Right = false;

                }

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
            });
            translateTransition.play();
        }

        /*
        if (newCenterInt == 9) {
            if (cantChoose == 0)
                cantCenterWest--;
            else
                cantCenterCenterEast--;

            TrafficController_02.removeVehicle(vehicle);
            System.out.println("Car has reached the final position!");
        }
*/
        System.out.println(Arrays.toString(positions));
        System.out.println(Arrays.toString(positionsTags));
        System.out.println("Pos " + nextPos);
        System.out.println("\n");
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
                    nextPos = getNextPositionEmergency_FixEast( centerInt, cantPositionsCenterEast, positionsTagsCenterEast, emergency_bool_CenterEast, trafficLights.get(3));
                else
                    nextPos = getNextPosition_FixEast(centerInt, cantPositionsCenterEast, positionsTagsCenterEast, emergency_bool_CenterEast, vehicle_emergency_CenterEast, trafficLights.get(3));

                if (nextPos != -1) {
                    //moveVehicle(vehicle, nextPos);
                    //private static void moveVehicle(Vehicle vehicle, int nextPos, int []positionsTags, int [] positions, PriorityBlockingQueue vehicles, int cant, int EmergencyChoose) {
                    moveVehicle_FixEast(vehicle, nextPos, positionsTagsCenterEast, positionsCenterEast, vehiclesCenterEast, cantCenterCenterEast, 1);
                }
            }
        }
        /*
        if (latch != null) {
            latch.countDown();

        }
*/
    }

    private static void moveVehicle_FixEast(Vehicle vehicle, int nextPos, int[] positionsTags, int[] positions, PriorityBlockingQueue vehicles, int cant, int cantChoose) {
        System.out.println("moveVehicle_FixEast");



        if(vehicle.getCenterInt() == 2)
        {
            inter01_New= true;

        }else if(vehicle.getCenterInt() == 5)
        {
            inter02_New = true;

        }else if(vehicle.getCenterInt() == 8)
        {
            inter03_New = true;

        }



        int newCenterInt;
        if (nextPos == 8) {
            newCenterInt = 9;
            positionsTags[newCenterInt - 1] = 0;
        } else {
            newCenterInt = vehicle.getCenterInt() + nextPos;
            positionsTags[newCenterInt] = 1;

            if (vehicle.getCenterInt() >= 0) {
                positionsTags[vehicle.getCenterInt()] = 0;
            }
        }

        int Xpos = positions[newCenterInt];



        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.8), vehicle.getImageView());
        translateTransition.setToX(Xpos);
        translateTransition.setOnFinished(event -> {


            if(vehicle.getCenterInt() == 2)
            {
                inter01_New = false;

            }else if(vehicle.getCenterInt() == 5)
            {
                inter02_New = false;

            }else if(vehicle.getCenterInt() == 8)
            {
                inter03_New = false;

            }






            vehicle.setCenterInt(newCenterInt);









            if (newCenterInt == 9) {
                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());

                if (vehicle.isEmergency()) {
                    emergency_bool = false;


                    vehicles.remove(vehicle);
                } else {
                    vehicles.remove(vehicle);
                }
            }
        });
        translateTransition.play();

        if (newCenterInt == 9) {
            if (cantChoose == 0)
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
    private static int getNextPosition_FixEast(int currentPos, int cantPositions, int [] positionsTags, boolean emergency_bool, Vehicle vehicle_emergency, TrafficLight trafficLight) {
        System.out.println("getNextPosition_FixEast");

        if (currentPos == 2) {
            if (inter01_New) return -1;
        } else if (currentPos == 5) {
            if (inter02_New) return -1;
        } else if (currentPos == 8) {
            if (inter03_New) return -1;
        }



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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()   ) {
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
                    if (trafficLight.getCircle().getFill() == Color.GREEN ) {
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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive() ) {
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
                    if (trafficLight.getCircle().getFill() == Color.GREEN ) {
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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive() ) {
                    return 8;
                }
            }
            else if (isCurrentSecondLessThanFive()) {
                if(vehicle_emergency.getCenterInt() > currentPos){
                    if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()  ) {
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
    private static int getNextPositionEmergency_FixEast(int currentPos, int cantPositions, int [] positionsTags, boolean emergency_bool, TrafficLight trafficLight) {
        System.out.println("getNextPositionEmergency_FixEast");

        if (currentPos == 2) {
            if (inter01_New) return -1;
        } else if (currentPos == 5) {
            if (inter02_New) return -1;
        } else if (currentPos == 8) {
            if (inter03_New) return -1;
        }

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




















    public static void updatePositionsRightEast() {

        Iterator<Vehicle> iterator = vehiclesEastRight.iterator();
        while (iterator.hasNext()) {
            System.out.println("EMERGENCY: " + emergency_bool_RightEast);
            Vehicle vehicle = iterator.next();
            int centerInt = vehicle.getCenterInt();
            int nextPos =0;
            if (centerInt < cantPositionsEastRight) {

                //    private static int getNextPosition(int currentPos, int cantPositions, int [] positionsTags, boolean emergency_bool, Vehicle vehicle_emergency, TrafficLight trafficLight) {

                if(vehicle.isEmergency()) {
                    nextPos = getNextPosition_Fix_Right_East(vehicle, centerInt, cantPositionsEastRight, positionsTagsEastRight, emergency_bool_RightEast, vehicle_emergency_EastRight, trafficLights.get(3));
                    //System.out.println("Center Int: " + centerInt);
                }                    //nextPos = getNextPosition(centerInt, cantPositionsWestRight, positionsTagsWestRight, emergency_bool_RightWest, vehicle_emergency_WestRight, trafficLights.getFirst() );

                else
                    nextPos = getNextPosition_Fix_Right_East(vehicle, centerInt, cantPositionsEastRight, positionsTagsEastRight, emergency_bool_RightEast, vehicle_emergency_EastRight, trafficLights.get(3) );

                System.out.println("Next Post: " + nextPos);

                if (nextPos != -1) {
                    //moveVehicle(vehicle, nextPos);
                    //moveVehicle(vehicle, nextPos, positionsTags, positions);
                    //moveVehicle(vehicle, nextPos, positionsTags, positions, vehiclesCenterWest, cantCenterWest, 0);
                    //moveVehicleRightWest(vehicle, nextPos, positionsTagsWestRight, positionsWestRight, vehiclesWestRight, cantWestRight, 0);
                    moveVehicle_Fix_Right_East(vehicle, nextPos, positionsTagsEastRight, positionsEastRight, vehiclesEastRight, cantEastRight, 0);

                }
            }
        }
        /*
        if (latch != null) {
            latch.countDown();

        }
*/
    }

    private static void moveVehicle_Fix_Right_East(Vehicle vehicle, int nextPos, int[] positionsTags, int[] positions, PriorityBlockingQueue vehicles, int cant, int cantChoose) {
        System.out.println("moveVehicle_Fix_Right");

        int newCenterInt;
        if (nextPos == 8) {
            newCenterInt = 9;
            positionsTags[newCenterInt - 1] = 0;
        } else {
            newCenterInt = vehicle.getCenterInt() + nextPos;
            positionsTags[newCenterInt] = 1;

            if (vehicle.getCenterInt() >= 0) {
                positionsTags[vehicle.getCenterInt()] = 0;
            }
        }

        if(vehicle.getCenterInt() == 2)
        {
            inter01_Right_West  = true;

        }else if(vehicle.getCenterInt() == 5)
        {
            inter02_Right_West  = true;

        }else if(vehicle.getCenterInt() == 8)
        {
            inter03_Right_West  = true;

        }



        int Xpos = positions[newCenterInt];

        if (vehicle.getCenterInt() == 2 && vehicle.getCalle().equals("Exit01")) {
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[2] + 65); // Adjust the value as needed

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {





                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());                //vehicles.remove(vehicle);

                vehiclesEastRight.remove(vehicle);
                cantEastRight--;

                TrafficController_02.removeVehicle(vehicle);
                positionsTagsEastRight[newCenterInt] = 0;

                inter01_Right_West = false;
                if(vehicle.isEmergency())
                    emergency_bool_RightEast = false;





            });
            sequentialTransition.play();
        } else if(vehicle.getCenterInt() == 5 && vehicle.getCalle().equals("Exit02")){
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[5] + 65); // Adjust the value as needed

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {



                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());                //vehicles.remove(vehicle);

                vehiclesEastRight.remove(vehicle);
                cantEastRight--;

                TrafficController_02.removeVehicle(vehicle);
                positionsTagsEastRight[newCenterInt] = 0;

                inter02_Right_West = false;
                if(vehicle.isEmergency())
                    emergency_bool_RightEast = false;

            });
            sequentialTransition.play();
        }else if(vehicle.getCenterInt() == 8 && vehicle.getCalle().equals("Exit03")){
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[8] + 65);

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {


                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());                //vehicles.remove(vehicle);

                vehiclesEastRight.remove(vehicle);
                cantEastRight--;

                TrafficController_02.removeVehicle(vehicle);
                positionsTagsEastRight[newCenterInt] = 0;

                inter03_Right_West = false;
                if(vehicle.isEmergency())
                    emergency_bool_RightEast = false;


            });
            sequentialTransition.play();
        }



        else {
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.8), vehicle.getImageView());
            translateTransition.setToX(Xpos);
            translateTransition.setOnFinished(event -> {



                if(vehicle.getCenterInt() == 2)
                {
                    inter01_Right_West  = false;

                }else if(vehicle.getCenterInt() == 5)
                {
                    inter02_Right_West  = false;

                }else if(vehicle.getCenterInt() == 8)
                {
                    inter03_Right_West = false;

                }

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
            });
            translateTransition.play();
        }

        /*
        if (newCenterInt == 9) {
            if (cantChoose == 0)
                cantCenterWest--;
            else
                cantCenterCenterEast--;

            TrafficController_02.removeVehicle(vehicle);
            System.out.println("Car has reached the final position!");
        }
*/
        System.out.println(Arrays.toString(positions));
        System.out.println(Arrays.toString(positionsTags));
        System.out.println("Pos " + nextPos);
        System.out.println("\n");


    }
    private static int getNextPosition_Fix_Right_East(Vehicle vehicle, int currentPos, int cantPositions, int [] positionsTags, boolean emergency_bool, Vehicle vehicle_emergency, TrafficLight trafficLight) {
        System.out.println("getNextPosition_Fix_Right");


        if (currentPos == 2 && vehicle.getCalle().equals("Exit01")) {
            if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                return 3;
            if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                return 2;
            if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                return 1;
        }else if(currentPos == 5 && vehicle.getCalle().equals("Exit02")){
            if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                return 3;
            if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                return 2;
            if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                return 1;}
        else if(currentPos == 8 && vehicle.getCalle().equals("Exit03")){

                return 8;
        }



        if (currentPos == 2 ) {
            if (inter01_Right_West ) return -1;
        } else if (currentPos == 5) {
            if (inter02_Right_West ) return -1;
        } else if (currentPos == 8) {
            if (inter03_Right_West ) return -1;
        }



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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()   ) {
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
                    if (trafficLight.getCircle().getFill() == Color.GREEN ) {
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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive() ) {
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
                    if (trafficLight.getCircle().getFill() == Color.GREEN ) {
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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive() ) {
                    return 8;
                }
            }
            else if (isCurrentSecondLessThanFive()) {
                if(vehicle_emergency.getCenterInt() > currentPos){
                    if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()  ) {
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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive() ) {
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









    private static int getNextPosition02(int currentPos, int cantPositions, int [] positionsTags, boolean emergency_bool, Vehicle vehicle_emergency, TrafficLight trafficLight) {
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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive() && !emergency_bool_LeftWest  ) {
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
                    if (trafficLight.getCircle().getFill() == Color.GREEN  && !emergency_bool_LeftWest) {
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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()  && !emergency_bool_LeftWest) {
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
                    if (trafficLight.getCircle().getFill() == Color.GREEN  && !emergency_bool_LeftWest) {
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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive() && !emergency_bool_LeftWest) {
                    return 8;
                }
            }
            else if (isCurrentSecondLessThanFive()) {
                if(vehicle_emergency.getCenterInt() > currentPos){
                    if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()  && !emergency_bool_LeftWest) {
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

    private static int getNextPosition03(int currentPos, int cantPositions, int [] positionsTags, boolean emergency_bool, Vehicle vehicle_emergency, TrafficLight trafficLight) {
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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive() && !emergency_bool_LeftEast  ) {
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
                    if (trafficLight.getCircle().getFill() == Color.GREEN  && !emergency_bool_LeftEast) {
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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()  && !emergency_bool_LeftEast) {
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
                    if (trafficLight.getCircle().getFill() == Color.GREEN  && !emergency_bool_LeftEast) {
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
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive() && !emergency_bool_LeftEast) {
                    return 8;
                }
            }
            else if (isCurrentSecondLessThanFive()) {
                if(vehicle_emergency.getCenterInt() > currentPos){
                    if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()  && !emergency_bool_LeftEast) {
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

        if (currentPos == 2) {
            if (inter01) return -1;
        } else if (currentPos == 5) {
            if (inter02) return -1;
        } else if (currentPos == 8) {
            if (inter03) return -1;
        }

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


    private static int getNextPositionEmergencytumalditamadre(int currentPos, int cantPositions, int [] positionsTags, boolean emergency_bool, TrafficLight trafficLight) {
        System.out.println("getNextPositionEmergency");

        if (currentPos == 2) {
            if (inter03) return -1;
        } else if (currentPos == 5) {
            if (inter02) return -1;
        } else if (currentPos == 8) {
            if (inter01) return -1;
        }

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
    private static int getNextPositionEmergencytumalditamadre02(int currentPos, int cantPositions, int [] positionsTags, boolean emergency_bool, TrafficLight trafficLight) {
        System.out.println("getNextPositionEmergency");

        if (currentPos == 2) {
            if (inter03_New) return -1;
        } else if (currentPos == 5) {
            if (inter02_New) return -1;
        } else if (currentPos == 8) {
            if (inter01_New) return -1;
        }

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



    private static void moveVehicle02(Vehicle vehicle, int nextPos, int[] positionsTags, int[] positions, PriorityBlockingQueue vehicles, int cant, int cantChoose) {
        int newCenterInt;
        if (nextPos == 8) {
            newCenterInt = 9;
            positionsTags[newCenterInt - 1] = 0;
        } else {
            newCenterInt = vehicle.getCenterInt() + nextPos;
            positionsTags[newCenterInt] = 1;

            if (vehicle.getCenterInt() >= 0) {
                positionsTags[vehicle.getCenterInt()] = 0;
            }
        }

        int Xpos = positions[newCenterInt];
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.8), vehicle.getImageView());
        translateTransition.setToX(Xpos);
        translateTransition.setOnFinished(event -> {
            vehicle.setCenterInt(newCenterInt);




            if (newCenterInt == 2 && vehicle.getCalle().equals("Exit01")) {
                if(vehicle.isEmergency())
                    emergency_bool_RightWest = false;

                vehiclesWestRight.remove(vehicle);
                cantWestRight--;
                positionsTags[2] = 0;

                TranslateTransition forwardTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
                forwardTransition.setToX(Xpos - 60); // Adjust the value as needed
                forwardTransition.setOnFinished(forwardEvent -> {
                    RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
                    rotateTransition.setByAngle(90); // Adjust the angle as needed
                    rotateTransition.setOnFinished(rotateEvent -> {
                        TranslateTransition forwardAgainTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
                        forwardAgainTransition.setToY( -330); // Adjust the value as needed
                        forwardAgainTransition.setOnFinished(forwardAgainEvent -> {
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
                    RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
                    rotateTransition.setByAngle(90); // Adjust the angle as needed
                    rotateTransition.setOnFinished(rotateEvent -> {
                        TranslateTransition forwardAgainTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
                        forwardAgainTransition.setToY( -330); // Adjust the value as needed
                        forwardAgainTransition.setOnFinished(forwardAgainEvent -> {
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
                    RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
                    rotateTransition.setByAngle(90); // Adjust the angle as needed
                    rotateTransition.setOnFinished(rotateEvent -> {
                        TranslateTransition forwardAgainTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
                        forwardAgainTransition.setToY( -330); // Adjust the value as needed
                        forwardAgainTransition.setOnFinished(forwardAgainEvent -> {
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



        System.out.println(Arrays.toString(positions));
        System.out.println(Arrays.toString(positionsTags));
        System.out.println("Pos " + nextPos);
        System.out.println("\n");
    }

    private static void moveVehicle03(Vehicle vehicle, int nextPos, int[] positionsTags, int[] positions, PriorityBlockingQueue vehicles, int cant, int cantChoose) {
        int newCenterInt;
        if (nextPos == 8) {
            newCenterInt = 9;
            positionsTags[newCenterInt - 1] = 0;
        } else {
            newCenterInt = vehicle.getCenterInt() + nextPos;
            positionsTags[newCenterInt] = 1;

            if (vehicle.getCenterInt() >= 0) {
                positionsTags[vehicle.getCenterInt()] = 0;
            }
        }

        int Xpos = positions[newCenterInt];
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.8), vehicle.getImageView());
        translateTransition.setToX(Xpos);
        translateTransition.setOnFinished(event -> {
            vehicle.setCenterInt(newCenterInt);




            if (newCenterInt == 2 && vehicle.getCalle().equals("Exit01")) {
                if(vehicle.isEmergency())
                    emergency_bool_RightEast = false;

                vehiclesEastRight.remove(vehicle);
                cantEastRight--;
                positionsTags[2] = 0;

                TranslateTransition forwardTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
                forwardTransition.setToX(Xpos + 60); // Adjust the value as needed
                forwardTransition.setOnFinished(forwardEvent -> {
                    RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
                    rotateTransition.setByAngle(90); // Adjust the angle as needed
                    rotateTransition.setOnFinished(rotateEvent -> {
                        TranslateTransition forwardAgainTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
                        forwardAgainTransition.setToY( 330); // Adjust the value as needed
                        forwardAgainTransition.setOnFinished(forwardAgainEvent -> {
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
                    emergency_bool_RightEast = false;

                vehiclesEastRight.remove(vehicle);
                cantEastRight--;
                positionsTags[5] = 0;



                TranslateTransition forwardTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
                forwardTransition.setToX(Xpos + 80); // Adjust the value as needed
                forwardTransition.setOnFinished(forwardEvent -> {
                    RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
                    rotateTransition.setByAngle(90); // Adjust the angle as needed
                    rotateTransition.setOnFinished(rotateEvent -> {
                        TranslateTransition forwardAgainTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
                        forwardAgainTransition.setToY( 330); // Adjust the value as needed
                        forwardAgainTransition.setOnFinished(forwardAgainEvent -> {
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
                    emergency_bool_RightEast = false;

                vehiclesEastRight.remove(vehicle);
                cantEastRight--;
                positionsTags[8] = 0;


                TranslateTransition forwardTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
                forwardTransition.setToX(Xpos + 80); // Adjust the value as needed
                forwardTransition.setOnFinished(forwardEvent -> {
                    RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
                    rotateTransition.setByAngle(90); // Adjust the angle as needed
                    rotateTransition.setOnFinished(rotateEvent -> {
                        TranslateTransition forwardAgainTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
                        forwardAgainTransition.setToY( 330); // Adjust the value as needed
                        forwardAgainTransition.setOnFinished(forwardAgainEvent -> {
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



        System.out.println(Arrays.toString(positions));
        System.out.println(Arrays.toString(positionsTags));
        System.out.println("Pos " + nextPos);
        System.out.println("\n");
    }





    private static int getNextPositiontuti(int currentPos, int cantPositions, int[] positionsTags, boolean emergency_bool, Vehicle vehicle_emergency, TrafficLight trafficLight) {
        System.out.println("getNextPosition");
        if (currentPos == 0) {
            if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                return 2;
            if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                return 1;
            return -1;
        } else if (currentPos == 2) {
            if (!emergency_bool) {
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()) {
                    if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                        return 3;
                    if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                        return 2;
                    if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                        return 1;
                }
            } else if (isCurrentSecondLessThanFive()) { // Emergencia
                if (vehicle_emergency.getCenterInt() > currentPos) {
                    if (trafficLight.getCircle().getFill() == Color.GREEN) {
                        if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                            return 3;
                        if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                            return 2;
                        if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                            return 1;
                    }
                } else {
                    if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                        return 3;
                    if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                        return 2;
                    if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                        return 1;
                }
            }
            return -1;
        } else if (currentPos == 5) {
            if (!emergency_bool) {
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()) {
                    if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                        return 3;
                    if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                        return 2;
                    if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                        return 1;
                }
            } else if (isCurrentSecondLessThanFive()) {
                if (vehicle_emergency.getCenterInt() > currentPos) {
                    if (trafficLight.getCircle().getFill() == Color.GREEN) {
                        if (currentPos + 3 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0 && positionsTags[currentPos + 3] == 0)
                            return 3;
                        if (currentPos + 2 < cantPositions && positionsTags[currentPos + 1] == 0 && positionsTags[currentPos + 2] == 0)
                            return 2;
                        if (currentPos + 1 < cantPositions && positionsTags[currentPos + 1] == 0)
                            return 1;
                    }
                } else {
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
            if (!emergency_bool) {
                if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()) {
                    return 8;
                }
            } else if (isCurrentSecondLessThanFive()) {
                if (vehicle_emergency.getCenterInt() > currentPos) {
                    if (trafficLight.getCircle().getFill() == Color.GREEN && isCurrentSecondLessThanFive()) {
                        return 8;
                    }
                } else {
                    return 8;
                }
            }
            return -1;
        } else if (currentPos > 0 && currentPos < cantPositions - 1) {
            if (positionsTags[currentPos + 1] == 0)
                return 1;
        }
        return -1;
    }

    private static void moveVehicletuti(Vehicle vehicle, int nextPos, int[] positionsTags, int[] positions, PriorityBlockingQueue vehicles, int cant, int cantChoose) {

        // La intercepcion esta libre?


        System.out.println("moveVehicletuti");
        int newCenterInt;
        if (nextPos == 8) {
            newCenterInt = 9;
            positionsTags[newCenterInt - 1] = 0;
        } else {
            newCenterInt = vehicle.getCenterInt() + nextPos;
            positionsTags[newCenterInt] = 1;

            if (vehicle.getCenterInt() >= 0) {
                positionsTags[vehicle.getCenterInt()] = 0;
            }
        }

        int Xpos = positions[newCenterInt];

        if (vehicle.getCenterInt() == 2 && vehicle.getCalle().equals("Exit01")) {
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[2] - 65); // Adjust the value as needed

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(-90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {
                /*
                if(vehicle.isEmergency())
                    emergency_bool_LeftWest = false;*/

                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());                //vehicles.remove(vehicle);

                vehiclesWestLeft.remove(vehicle);
                cantWestLeft--;

                TrafficController_02.removeVehicle(vehicle);
                positionsTagsWestLeft[newCenterInt] = 0;

                emergency_bool_LeftWest = false;
            });
            sequentialTransition.play();
        } else if(vehicle.getCenterInt() == 5 && vehicle.getCalle().equals("Exit02")){
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[5] - 65); // Adjust the value as needed

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(-90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {


                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());                //vehicles.remove(vehicle);

                vehiclesWestLeft.remove(vehicle);
                cantWestLeft--;


                TrafficController_02.removeVehicle(vehicle);
                positionsTagsWestLeft[newCenterInt] = 0;

                emergency_bool_LeftWest = false;
            });
            sequentialTransition.play();
        }else if(vehicle.getCenterInt() == 8 && vehicle.getCalle().equals("Exit03")){
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[8] - 65);

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(-90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {



                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());                //vehicles.remove(vehicle);

                vehiclesWestLeft.remove(vehicle);
                cantWestLeft--;


                TrafficController_02.removeVehicle(vehicle);
                positionsTagsWestLeft[newCenterInt] = 0;

                emergency_bool_LeftWest = false;

            });
            sequentialTransition.play();
        }



        else {
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
            });
            translateTransition.play();
        }

/*
        if (newCenterInt == 9) {
            if (cantChoose == 0)
                cantCenterWest--;
            else
                cantCenterCenterEast--;

            TrafficController_02.removeVehicle(vehicle);
            System.out.println("Car has reached the final position!");
        }
*/
        System.out.println(Arrays.toString(positions));
        System.out.println(Arrays.toString(positionsTags));
        System.out.println("Pos " + nextPos);
        System.out.println("\n");
    }


    private static void moveVehicletuti02(Vehicle vehicle, int nextPos, int[] positionsTags, int[] positions, PriorityBlockingQueue vehicles, int cant, int cantChoose) {
        System.out.println("moveVehicletuti02");
        int newCenterInt;
        if (nextPos == 8) {
            newCenterInt = 9;
            positionsTags[newCenterInt - 1] = 0;
        } else {
            newCenterInt = vehicle.getCenterInt() + nextPos;
            positionsTags[newCenterInt] = 1;

            if (vehicle.getCenterInt() >= 0) {
                positionsTags[vehicle.getCenterInt()] = 0;
            }
        }

        int Xpos = positions[newCenterInt];

        if (vehicle.getCenterInt() == 2 && vehicle.getCalle().equals("Exit01")) {
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[2] + 65); // Adjust the value as needed

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(-90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(-330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {

                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());                //vehicles.remove(vehicle);

                vehiclesEastLeft.remove(vehicle);
                cantEastLeft--;

                TrafficController_02.removeVehicle(vehicle);
                positionsTagsEastLeft[newCenterInt] = 0;
            });
            sequentialTransition.play();
        } else if(vehicle.getCenterInt() == 5 && vehicle.getCalle().equals("Exit02")){
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[5] + 65); // Adjust the value as needed

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(-90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(-330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {



                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());                //vehicles.remove(vehicle);

                vehiclesEastLeft.remove(vehicle);
                cantEastLeft--;


                TrafficController_02.removeVehicle(vehicle);
                positionsTagsEastLeft[newCenterInt] = 0;
                emergency_bool_LeftEast = false;

            });
            sequentialTransition.play();
        }else if(vehicle.getCenterInt() == 8 && vehicle.getCalle().equals("Exit03")){
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[8] + 65);

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(-90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(-330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {


                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());
                // vehicles.remove(vehicle);

                vehiclesEastLeft.remove(vehicle);
                cantEastLeft--;


                TrafficController_02.removeVehicle(vehicle);
                positionsTagsEastLeft[newCenterInt] = 0;
                emergency_bool_LeftEast = false;

            });
            sequentialTransition.play();
        }



        else {
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
            });
            translateTransition.play();
        }

        /*
        if (newCenterInt == 9) {
            if (cantChoose == 0)
                cantCenterWest--;
            else
                cantCenterCenterEast--;

            TrafficController_02.removeVehicle(vehicle);
            System.out.println("Car has reached the final position!");
        }
*/
        System.out.println(Arrays.toString(positions));
        System.out.println(Arrays.toString(positionsTags));
        System.out.println("Pos " + nextPos);
        System.out.println("\n");
    }
    private static void moveVehicletuti03(Vehicle vehicle, int nextPos, int[] positionsTags, int[] positions, PriorityBlockingQueue vehicles, int cant, int cantChoose) {
        System.out.println("moveVehicletuti02");
        int newCenterInt;
        if (nextPos == 8) {
            newCenterInt = 9;
            positionsTags[newCenterInt - 1] = 0;
        } else {
            newCenterInt = vehicle.getCenterInt() + nextPos;
            positionsTags[newCenterInt] = 1;

            if (vehicle.getCenterInt() >= 0) {
                positionsTags[vehicle.getCenterInt()] = 0;
            }
        }

        int Xpos = positions[newCenterInt];

        if (vehicle.getCenterInt() == 2 && vehicle.getCalle().equals("Exit01")) {
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[2] + 65); // Adjust the value as needed

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(-90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(-330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {
                if(vehicle.isEmergency())
                    emergency_bool_LeftWest = false;

                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());
                vehicles.remove(vehicle);

                vehiclesWestLeft.remove(vehicle);
                cantWestLeft--;

                TrafficController_02.removeVehicle(vehicle);
                positionsTagsWestLeft[newCenterInt] = 0;
            });
            sequentialTransition.play();
        } else if(vehicle.getCenterInt() == 5 && vehicle.getCalle().equals("Exit02")){
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[5] + 65); // Adjust the value as needed

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(-90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(-330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {
                if(vehicle.isEmergency())
                    emergency_bool_LeftWest = false;


                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());
                vehicles.remove(vehicle);

                vehiclesWestLeft.remove(vehicle);
                cantWestLeft--;


                TrafficController_02.removeVehicle(vehicle);
                positionsTagsWestLeft[newCenterInt] = 0;
            });
            sequentialTransition.play();
        }else if(vehicle.getCenterInt() == 8 && vehicle.getCalle().equals("Exit03")){
            TranslateTransition moveForward1 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward1.setToX(positions[8] + 65);

            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
            rotate.setByAngle(-90);

            TranslateTransition moveForward2 = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            moveForward2.setToY(-330);

            SequentialTransition sequentialTransition = new SequentialTransition(moveForward1, rotate, moveForward2);
            sequentialTransition.setOnFinished(event -> {
                if(vehicle.isEmergency())
                    emergency_bool_LeftWest = false;

                ((StackPane) vehicle.getImageView().getParent()).getChildren().remove(vehicle.getImageView());
                vehicles.remove(vehicle);

                vehiclesWestLeft.remove(vehicle);
                cantWestLeft--;


                TrafficController_02.removeVehicle(vehicle);
                positionsTagsWestLeft[newCenterInt] = 0;
            });
            sequentialTransition.play();
        }



        else {
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.8), vehicle.getImageView());
            translateTransition.setToX(Xpos);
            translateTransition.setOnFinished(event -> {
                vehicle.setCenterInt(newCenterInt);

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
                }
            });
            translateTransition.play();
        }

        if (newCenterInt == 9) {
            if (cantChoose == 0)
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


    private static void moveVehicleleft(Vehicle vehicle, int nextPos, int []positionsTags, int [] positions, PriorityBlockingQueue vehicles, int cant, int cantChoose) {


        System.out.println("moveVehicleleft");
        int newCenterInt;
        if (nextPos == 8) {
            newCenterInt = 9;
            positionsTags[newCenterInt - 1] = 0;
        } else {
            newCenterInt = vehicle.getCenterInt() + nextPos;
            positionsTags[newCenterInt] = 1;

            if (vehicle.getCenterInt() >= 0) {
                positionsTags[vehicle.getCenterInt()] = 0;
            }
        }

        // newCenterInt > 2 && vehicle.getCalle().equals("Exit01")
        int Xpos = positions[newCenterInt];
        String calle = vehicle.getCalle();
        if(newCenterInt > 2 && calle.equals("Exit01") ) {

            if(vehicle.isEmergency())
                emergency_bool_LeftWest = false;

            vehiclesWestLeft.remove(vehicle);
            cantWestLeft--;
            positionsTags[2] = 0;


            TranslateTransition forwardTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            forwardTransition.setToX( positions[2] - 60);
            forwardTransition.setOnFinished(forwardEvent -> {
                RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
                rotateTransition.setByAngle(-90);
                rotateTransition.setOnFinished(rotateEvent -> {
                    TranslateTransition forwardAgainTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
                    forwardAgainTransition.setToY( 330);
                    forwardAgainTransition.setOnFinished(forwardAgainEvent -> {
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
        else if(newCenterInt > 5 && calle.equals("Exit02")) {
            if(vehicle.isEmergency())
                emergency_bool_LeftWest = false;

            vehiclesWestLeft.remove(vehicle);
            cantWestLeft--;
            positionsTags[5] = 0;


            TranslateTransition forwardTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            forwardTransition.setToX( positions[5] - 60);
            forwardTransition.setOnFinished(forwardEvent -> {
                RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
                rotateTransition.setByAngle(-90);
                rotateTransition.setOnFinished(rotateEvent -> {
                    TranslateTransition forwardAgainTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
                    forwardAgainTransition.setToY( 330);
                    forwardAgainTransition.setOnFinished(forwardAgainEvent -> {
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
        else if(newCenterInt > 8 && calle.equals("Exit03")) {
            if(vehicle.isEmergency())
                emergency_bool_LeftWest = false;

            vehiclesWestLeft.remove(vehicle);
            cantWestLeft--;
            positionsTags[8] = 0;
            if(vehicle.isEmergency())
                emergency_bool_LeftWest = false;

            vehiclesWestLeft.remove(vehicle);
            cantWestLeft--;
            positionsTags[8] = 0;


            TranslateTransition forwardTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
            forwardTransition.setToX( positions[8] - 60); // Adjust the value as needed
            forwardTransition.setOnFinished(forwardEvent -> {
                RotateTransition rotateTransition = new RotateTransition(Duration.seconds(0.5), vehicle.getImageView());
                rotateTransition.setByAngle(-90); // Adjust the angle as needed
                rotateTransition.setOnFinished(rotateEvent -> {
                    TranslateTransition forwardAgainTransition = new TranslateTransition(Duration.seconds(0.5), vehicle.getImageView());
                    forwardAgainTransition.setToY( 330); // Adjust the value as needed
                    forwardAgainTransition.setOnFinished(forwardAgainEvent -> {
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
        else{
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
            }

            System.out.println(Arrays.toString(positions));
            System.out.println(Arrays.toString(positionsTags));
            System.out.println("Pos " + nextPos);
            System.out.println("\n");
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