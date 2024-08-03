package com.example.proyectofinal_icc303;

public class TrafficLight {
    private String id;
    private LightState currentState;

    public TrafficLight(String id) {
        this.id = id;
        this.currentState = LightState.RED; // Default state
    }

    public String getId() {
        return id;
    }

    public void changeLight() {
        switch (currentState) {
            case RED:
                currentState = LightState.GREEN;
                break;
            case YELLOW:
                currentState = LightState.RED;
                break;
            case GREEN:
                currentState = LightState.YELLOW;
                break;
        }
    }

    public boolean isGreen() {
        return currentState == LightState.GREEN;
    }

    public LightState getCurrentState() {
        return currentState;
    }

    public enum LightState {
        RED, YELLOW, GREEN
    }
}