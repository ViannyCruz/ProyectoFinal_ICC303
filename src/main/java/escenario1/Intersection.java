package escenario1;

import java.util.concurrent.PriorityBlockingQueue;

public class Intersection {
    private String id;
    private boolean righTurnAllowed;
    private PriorityBlockingQueue<Vehicle> vehicleQueue;

    public Intersection(String id, boolean righTurnAllowed) {
        this.id = id;
        this.righTurnAllowed = righTurnAllowed;
        this.vehicleQueue = new PriorityBlockingQueue<>(); // Aqui el profesor puso otra cosa incompleta, agregarla bien
    }

    public void setVehicles(PriorityBlockingQueue<Vehicle> vehicles) {
        this.vehicleQueue = vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleQueue.add(vehicle);
    }

    public Vehicle getNextVehicle() {
        return vehicleQueue.poll();
    }

}