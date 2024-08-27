module com.example.proyectofinal_icc303 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens escenario1 to javafx.fxml;
    exports escenario1;
}