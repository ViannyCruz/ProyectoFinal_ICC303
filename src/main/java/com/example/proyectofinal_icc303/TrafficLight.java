package com.example.proyectofinal_icc303;

import javafx.application.Platform;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.concurrent.atomic.AtomicBoolean;

public class TrafficLight {
    private String id;
    private AtomicBoolean green;

    public TrafficLight(String id) {
        this.id = id;
        this.green = new AtomicBoolean(false);
    }

    public void changeLight(){
        green.set(!green.get());
    }

    public boolean isGreen(){
        return green.get();
    }


}
