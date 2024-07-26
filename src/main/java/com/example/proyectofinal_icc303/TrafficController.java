package com.example.proyectofinal_icc303;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class TrafficController {
    private Queue<Runnable> tasks = new LinkedList<>();

    private volatile boolean crossingNorthOccupied = false;
    private volatile boolean crossingSouthOccupied = false;
    private volatile boolean crossingEastOccupied = false;
    private volatile boolean crossingWestOccupied = false;



    private PriorityBlockingQueue<Vehicle> queue = new PriorityBlockingQueue<>();
    private final AtomicBoolean running = new AtomicBoolean(false);
    private Thread workerThread;

    public TrafficController() {
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
            if (!queue.isEmpty()) {
                Vehicle vehicle = queue.poll();
                if (vehicle != null) {
                    addVehicleAnimation(vehicle);
                }
            }
            // Pequeña pausa para evitar uso excesivo de CPU
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private synchronized void addVehicleAnimation(Vehicle vehicle) {
        tasks.offer(() -> {
            if (vehicle.getCalle().equals("North") && vehicle.getDirection().equals("South")) {
                while (crossingEastOccupied) {
                    try {
                        wait(); // Espera hasta que el cruce se libere
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                crossingEastOccupied = true; // Marca el cruce como ocupado

                PauseTransition pause = new PauseTransition(Duration.millis(1200));
                pause.setOnFinished(event -> {

                    HelloController.moveNorth(vehicle);

                    crossingEastOccupied = false; // Libera el cruce
                    synchronized (this) {
                        notifyAll(); // Notifica a otros vehículos que el cruce está libre
                    }
                    scheduleNext();
                });
                pause.play();
            }
            if (vehicle.getCalle().equals("North") && vehicle.getDirection().equals("North")) {
                while (crossingNorthOccupied||crossingWestOccupied||crossingEastOccupied) {
                    try {
                        wait(); // Espera hasta que el cruce se libere
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                crossingNorthOccupied = true;
                crossingWestOccupied = true;
                crossingEastOccupied = true;


                PauseTransition pause = new PauseTransition(Duration.millis(1200));
                pause.setOnFinished(event -> {


                        HelloController.moveNorthUTurn(vehicle);


                    crossingNorthOccupied = false;
                    crossingWestOccupied = false;
                    crossingEastOccupied = false;
                    synchronized (this) {
                        notifyAll(); // Notifica a otros vehículos que el cruce está libre
                    }
                    scheduleNext();
                });
                pause.play();
            }





//            while (crossingOccupied) {
//                try {
//                    wait(); // Espera hasta que el cruce se libere
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//            }
//            crossingOccupied = true; // Marca el cruce como ocupado
//
//            PauseTransition pause = new PauseTransition(Duration.millis(1200));
//            pause.setOnFinished(event -> {
//                if (vehicle.getCalle().equals("North")) {
//                    HelloController.moveNorth(vehicle);
//                } else if (vehicle.getCalle().equals("South")) {
//                    HelloController.moveSouthUturn(vehicle);
//                } else if (vehicle.getCalle().equals("East")) {
//                    HelloController.moveEast(vehicle);
//                } else if (vehicle.getCalle().equals("West")) {
//                    HelloController.moveWest(vehicle);
//                }
//                crossingOccupied = false; // Libera el cruce
//                synchronized (this) {
//                    notifyAll(); // Notifica a otros vehículos que el cruce está libre
//                }
//                scheduleNext();
//            });
//            pause.play();
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
        queue.add(vehicle);
    }
}