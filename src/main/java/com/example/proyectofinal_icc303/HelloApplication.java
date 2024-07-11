package com.example.proyectofinal_icc303;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.AllPermission;
import java.util.Objects;
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
        AtomicReference<PriorityBlockingQueue<Vehicle>> vehiclesSouth = new AtomicReference<>(new PriorityBlockingQueue<>());
        AtomicReference<PriorityBlockingQueue<Vehicle>> vehiclesEast = new AtomicReference<>(new PriorityBlockingQueue<>());
        AtomicReference<PriorityBlockingQueue<Vehicle>> vehiclesWest = new AtomicReference<>(new PriorityBlockingQueue<>());
        AtomicReference<PriorityBlockingQueue<Vehicle>> vehicles = new AtomicReference<>(new PriorityBlockingQueue<>());

        Thread workerThread = new Thread(() -> {
            try {
                while (running) {
                    Thread.sleep(1000);

<<<<<<< HEAD
                    vehiclesNorth.set(controller.get_vehiclesNorth());
                    vehiclesSouth.set(controller.get_vehiclesSouth());
                    vehiclesEast.set(controller.get_vehiclesEast());
                    vehiclesWest.set(controller.get_vehiclesWest());



                    vehicles.set(controller.get_vehicles());


                    // Determinar de que conjunto quiero el auto
                    //Vehicle vehicle = vehiclesNorth.get().poll();

                    // Todo : esto se podria buscar por calle tal vez mejor





                    //EMERGENCY
                    String emergency = controller.getEmergency();

                    if( emergency.isEmpty()) {

                        Vehicle vehicle = vehicles.get().poll();
                        Vehicle vehicle1 ;
                        if (vehicle != null ){
                            if(vehicle.getCalle().equals("North")) {
                                vehicle1 = findVehicle(vehiclesNorth, vehicle);
                                if (vehicle1 != null){
                                    vehiclesNorth.get().poll();
                                    HelloController.moveNorth(vehicle1);
                                    controller.reduceNorth();
                                }
                            }else if(vehicle.getCalle().equals("South")) {
                                vehicle1 = findVehicle(vehiclesSouth, vehicle);
                                if (vehicle1 != null){
                                    vehiclesSouth.get().poll();
                                    HelloController.moveSouth(vehicle1);
                                    controller.reduceSouth();
                                }
                            }else if(vehicle.getCalle().equals("East")) {
                                vehicle1 = findVehicle(vehiclesEast, vehicle);
                                if (vehicle1 != null){
                                    vehiclesEast.get().poll();
                                    HelloController.moveEast(vehicle1);
                                    controller.reduceEast();
                                }
                            }else if(vehicle.getCalle().equals("West")) {
                                vehicle1 = findVehicle(vehiclesWest, vehicle);
                                if (vehicle1 != null){
                                    vehiclesWest.get().poll();
                                    HelloController.moveWest(vehicle1);
                                    controller.reduceWest();
                                }
                            }

                        }



                    } else  {

                        /*
                        while (!vehiclesNorth.get().isEmpty()) {
                            Vehicle vehicle02 = vehiclesNorth.get().poll();
                            if (vehicle02 != null) {

                                HelloController.moveNorth(vehicle02);
                                controller.reduceNorth();




                            }
                        }*/



                        System.out.println("UN LLAMADO DE EMERGENCIA BABY");
/*
                        Vehicle holder;
                        while ((holder = vehiclesNorth.get().poll()) != null) {
                            HelloController.moveNorth(holder);
                            controller.reduceNorth();
                            Thread.sleep(1000);

                            if (holder.isEmergency()) {

                                controller.resetEmergency();
                                break;

                            }
                        }*/


                        Vehicle holder;

                        if(emergency.equals("North")){
                            while ((holder = vehiclesNorth.get().poll()) != null) {
                                HelloController.moveNorth(holder);
                                controller.reduceNorth();
                                Thread.sleep(1000);

                                if (holder.isEmergency()) {

                                    controller.resetEmergency();
                                    break;

                                }
                            }
                        }else if(emergency.equals("South")){
                            while ((holder = vehiclesSouth.get().poll()) != null) {
                                HelloController.moveSouth(holder);
                                controller.reduceSouth();
                                Thread.sleep(1000);

                                if (holder.isEmergency()) {

                                    controller.resetEmergency();
                                    break;

                                }
                            }
                        }else if(emergency.equals("East")){
                            while ((holder = vehiclesEast.get().poll()) != null) {
                                HelloController.moveEast(holder);
                                controller.reduceEast();
                                Thread.sleep(1000);

                                if (holder.isEmergency()) {

                                    controller.resetEmergency();
                                    break;

                                }
                            }
                        }else{
                            while ((holder = vehiclesWest.get().poll()) != null) {
                                HelloController.moveWest(holder);
                                controller.reduceWest();
                                Thread.sleep(1000);

                                if (holder.isEmergency()) {

                                    controller.resetEmergency();
                                    break;

                                }
                            }
                        }


=======
>>>>>>> 3510010982bb679ad471b2b24556d0690945cfa6



                    }


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

    public static Vehicle findVehicle(AtomicReference<PriorityBlockingQueue<Vehicle>> vehicles, Vehicle targetVehicle) {
        for (Vehicle vehicle : vehicles.get()) {
            if (vehicle.equals(targetVehicle)) {
                return vehicle;
            }
        }
        return null;
    }
}