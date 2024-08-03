package com.example.proyectofinal_icc303;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class TrafficControllerEscenario02 {

    private Queue<Runnable> tasks = new LinkedList<>();

    private volatile boolean crossingNorthOccupied = false;
    private volatile boolean crossingSouthOccupied = false;
    public volatile boolean crossingEastOccupied = false;
    private volatile boolean crossingWestOccupied = false;





    private PriorityBlockingQueue<Vehicle> queue = new PriorityBlockingQueue<>();
    private PriorityBlockingQueue<Vehicle> Emergencyqueue = new PriorityBlockingQueue<>();
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Thread workerThread;

    public TrafficControllerEscenario02() {
        workerThread = new Thread(this::processQueue);
        running.set(true);
        workerThread.start();
    }



    private void scheduleNext() {
        if (!tasks.isEmpty()) {
            tasks.poll().run();
        }
    }

    private void processQueue() {
        while (running.get()) {
            if(!Emergencyqueue.isEmpty()){
                Vehicle emgvehicle = Emergencyqueue.poll();
                for (Vehicle vehicle : queue) {
                    if(vehicle.getCalle().equals(emgvehicle.getCalle())){
                        addVehicleAnimation(vehicle);
                        if(vehicle==emgvehicle){
                            queue.remove(vehicle);
                            break;
                        }
                        queue.remove(vehicle);
                        try {
                            Thread.sleep(6000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }

                }
            }else {
                if (!queue.isEmpty()) {


                    Vehicle vehicle = queue.poll();
                    if (vehicle != null) {

                        addVehicleAnimation(vehicle);

                    }

                }
            }
            // Pequeña pausa para evitar uso excesivo de CPU
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }


    private void addVehicleAnimation(Vehicle vehicle) {
        tasks.offer(() -> {
            if (vehicle.getCalle().equals("North") && vehicle.getDirection().equals("CenterCenterCenter")) {

                CompletableFuture<Void> future = new CompletableFuture<>();
                PauseTransition pause = new PauseTransition(Duration.millis(1));
                pause.setOnFinished(event -> {
                    HelloController.movetuma(vehicle).thenRun(() -> {
                        HelloController.updatePositions();
                        future.complete(null);
                    });
                });
                pause.play();
                future.thenRun(this::scheduleNext);
            }
        });
        scheduleNext();
    }

    public void startControl() {
        while (!queue.isEmpty()) {
            Vehicle vehicle = queue.poll();
            if(vehicle!=null){
                addVehicleAnimation(vehicle);
            }
        }
        scheduleNext();
    }

    public void stopControl() {
        running.set(false);
        workerThread.interrupt();
    }

    public void setQueue(PriorityBlockingQueue<Vehicle> queue) {
        this.queue = queue;
    }

    public void addVehicle(Vehicle vehicle) {
        if(vehicle.isEmergency()){
            Emergencyqueue.add(vehicle);
        }
        queue.add(vehicle);
    }












}
