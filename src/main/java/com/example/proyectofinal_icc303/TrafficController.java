package com.example.proyectofinal_icc303;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;

public class TrafficController {
    private Queue<Runnable> tasks = new LinkedList<>();

    private void scheduleNext() {
        if (!tasks.isEmpty()) {
            tasks.poll().run();
        }
    }

    private void addVehicleAnimation(Vehicle vehicle) {
        tasks.offer(() -> {
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(event -> {
                if (vehicle.getCalle().equals("North")) {
                    HelloController.moveNorth(vehicle);
                } else if (vehicle.getCalle().equals("South")) {
                    HelloController.moveSouth(vehicle);
                } else if (vehicle.getCalle().equals("East")) {
                    HelloController.moveEast(vehicle);
                } else if (vehicle.getCalle().equals("West")) {
                    HelloController.moveWest(vehicle);
                }
                scheduleNext();
            });
            pause.play();
        });
    }

    public void startControl(PriorityBlockingQueue<Vehicle> vehicleQueue) {
        while (!vehicleQueue.isEmpty()) {
            Vehicle vehicle = vehicleQueue.poll();
            assert vehicle != null;
            addVehicleAnimation(vehicle);
        }
        scheduleNext();
    }
}