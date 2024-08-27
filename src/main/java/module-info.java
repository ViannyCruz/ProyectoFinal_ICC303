module com.example.proyectofinal_icc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens escenario1 to javafx.fxml;
    exports escenario1;

    opens escenario2 to javafx.fxml;
    exports escenario2;

    opens com.example to javafx.fxml;
    exports com.example to javafx.graphics;

}
