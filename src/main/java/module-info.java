module com.example.proyectofinal  {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.proyectofinal_icc303 to javafx.fxml;
    exports com.example.proyectofinal_icc303;
}