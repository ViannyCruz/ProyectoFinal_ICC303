package com.example.proyectofinal_icc303;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class Vehicle implements Comparable<Vehicle> {
    private static int nextId = 1;
    private int id;
    private boolean emergency;
    private String direction;
    private String calle;
    private double x, y; // Posición del vehículo
    private transient ImageView imageView; // Referencia a la imagen en la interfaz gráfica


    @Override
    public int compareTo(Vehicle other) {
        return Integer.compare(this.id, other.id);
    }

    // Constructor actualizado
    public Vehicle(boolean emergency, String direction, String calle, ImageView imageView) {
        this.id = nextId++;
        this.emergency = emergency;
        this.direction = direction;
        this.calle = calle;
        this.imageView = imageView;
        this.x = imageView.getLayoutX(); // Inicializa x, y con la posición actual de imageView
        this.y = imageView.getLayoutY();
    }

    // Métodos getters y setters para x, y e imageView omitidos por brevedad

    public void move() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> {
            if (y > -700) {
                y -= 10;
                imageView.setLayoutY(y);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }



    public Integer getId() {
        return id;
    }

    public boolean isEmergency() {
        return emergency;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setEmergency(boolean emergency) {
        this.emergency = emergency;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }


    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }



}