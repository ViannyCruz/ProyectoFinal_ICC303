package com.example.proyectofinal_icc303;

import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
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