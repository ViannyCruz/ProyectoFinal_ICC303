package escenario2;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class EscenarioAvance extends Application {


    public static void main(String[] args) {
       // launch(args);
    }


    @Override
    public void start(Stage stage) throws Exception {

        StackPane root = new StackPane();
        Scene scene = new Scene(root, 800, 800);

        Image image = new Image("com/example/proyectofinal_icc303/Fondo.png");
        ImageView imageView = new ImageView(image);
        root.getChildren().add(imageView);

        stage.setTitle("Avance Proyecto");
        stage.setScene(scene);
        stage.show();
    }
}