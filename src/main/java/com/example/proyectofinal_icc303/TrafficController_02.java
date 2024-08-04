package com.example.proyectofinal_icc303;

import javafx.animation.PauseTransition;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class TrafficController_02 {

    private boolean CenterWest;
    private Queue<Runnable> tasks = new LinkedList<>();

    private volatile boolean crossingNorthOccupied = false;
    private volatile boolean crossingSouthOccupied = false;
    public volatile boolean crossingEastOccupied = false;
    private volatile boolean crossingWestOccupied = false;



    private CountDownLatch latch;


    private PriorityBlockingQueue<Vehicle> queue = new PriorityBlockingQueue<>();
    private PriorityBlockingQueue<Vehicle> Emergencyqueue = new PriorityBlockingQueue<>();

    private final AtomicBoolean running = new AtomicBoolean(false);
    private Thread workerThread;

    public TrafficController_02() {
        workerThread = new Thread(this::processQueue);
        running.set(true);
        workerThread.start();
        this.CenterWest = false; ///
    }

    private void scheduleNext() {
        if (!tasks.isEmpty()) {
            tasks.poll().run();
        }
    }

    private void processQueue() {
        while (running.get()) {
            if (!Emergencyqueue.isEmpty()) {
                Vehicle emgvehicle = Emergencyqueue.poll();
                for (Vehicle vehicle : queue) {
                    if (vehicle.getCalle().equals(emgvehicle.getCalle())) {
                        addVehicleAnimation(vehicle);
                        // No need to remove the vehicle from the queue
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            } else {
                if (!queue.isEmpty()) {
                    Vehicle vehicle = queue.peek(); // Peek instead of poll to avoid removing the vehicle
                    if (vehicle != null) {
                        addVehicleAnimation(vehicle);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
            // Pequeña pausa para evitar uso excesivo de CPU 3000 120
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private synchronized void addVehicleAnimation(Vehicle vehicle) {
        tasks.offer(() -> {

            if (vehicle.getCalle().equals("East") && vehicle.getDirection().equals("West")) {

                if(isCenterWest())
                    HelloController.updatePositionsCenterWest();
                else
                    HelloController.updatePositionsCenterWestRed();


            }

            /*

            if(CenterWest){
                if (vehicle.getCalle().equals("East") && vehicle.getDirection().equals("West")) {

                    HelloController.updatePositionsCenterWest();

                }
            }else{
                if (vehicle.getCalle().equals("East") && vehicle.getDirection().equals("West")) {

                   // HelloController.updatePositionsCenterWest();

                }
            }
*/





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

    public boolean isCenterWest() {
        return CenterWest;
    }


    public void setCenterWest() {
        if(CenterWest){
            CenterWest = false;
        }else{
            CenterWest = true;
        }

    }
}