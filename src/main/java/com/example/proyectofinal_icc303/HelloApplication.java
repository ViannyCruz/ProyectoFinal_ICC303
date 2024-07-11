package com.example.proyectofinal_icc303;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;

public class HelloApplication extends Application {
    private static volatile boolean running = true;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 750, 800);

        stage.setTitle("Avance Proyecto");
        stage.setScene(scene);
        stage.show();

        // Obtener el controlador
        HelloController controller = fxmlLoader.getController();

        Intersection intersection01 = new Intersection("1", true);

        AtomicReference<PriorityBlockingQueue<Vehicle>> vehiclesNorth = new AtomicReference<>(new PriorityBlockingQueue<>());

        Thread workerThread = new Thread(() -> {
            try {
                while (running) {
                    Thread.sleep(1000);


                    vehiclesNorth.set(controller.get_vehiclesNorth());

                    Vehicle vehicle = vehiclesNorth.get().poll();
                    if(vehicle != null) {
                       // HelloController.moveNorth(vehicle);

                       // controller.reduceNorth();
                        Thread.sleep(2000);
                    }



                    /*
                    intersection01.setVehicles(controller.get_vehiclesNorth());
                    Vehicle vehicle = intersection01.getNextVehicle();
                    System.out.println(vehicle.getCalle());

                    */


                    /*
                    while (!controller.getvehicleQueue().isEmpty()) {
                        Vehicle vehicle = controller.getvehicleQueue().poll();
                        System.out.println(vehicle);
                    }*/



                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        workerThread.start();
    }

    public static void main(String[] args) {
        launch();
    }
}
