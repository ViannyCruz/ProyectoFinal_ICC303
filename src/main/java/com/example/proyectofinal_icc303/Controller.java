package com.example.proyectofinal_icc303;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private StackPane stackContainer;

    @FXML
    public Button btnCenterWest;
    public Button btnCenterEast;
    public Button btnRightTurnWest;
    public Button btnLeftTurnWest;
    public Button btnLeftTurnEast;
    public Button btnRightTurnEast;

    @FXML
    public RadioButton emergencia;
    public RadioButton exit01;
    public RadioButton exit02;
    public RadioButton exit03;




    @FXML
    private Label welcomeText;

    @FXML
    private Button changeBackgroundButton;

    private ImageView imageView;
    private Image currentImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //*** UI Initialization ***//
        currentImage = new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/BG_Inicio03.png"));
        imageView = new ImageView(currentImage);
        imageView.setFitHeight(800);
        imageView.setFitWidth(1280);
        imageView.setPreserveRatio(true);
        imageView.setPickOnBounds(true);

        stackContainer.getChildren().add(imageView);

        // Ocultar botones
        btnCenterWest.setVisible(false);
        btnCenterEast.setVisible(false);
        btnRightTurnWest.setVisible(false);
        btnLeftTurnWest.setVisible(false);
        btnLeftTurnEast.setVisible(false);
        btnRightTurnEast.setVisible(false);


        emergencia.setVisible(false);
        exit01.setVisible(false);
        exit02.setVisible(false);
        exit03.setVisible(false);





    }

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    protected void onChangeBackgroundButtonClick() {
        // Change to a new background image
        currentImage = new Image(getClass().getResourceAsStream("/com/example/proyectofinal_icc303/BG.png"));
        imageView.setImage(currentImage);
    }
}