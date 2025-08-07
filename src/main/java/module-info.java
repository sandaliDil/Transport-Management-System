module com.vms.transportmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires mysql.connector.j;
    requires org.apache.poi.ooxml;
    requires itextpdf;


    opens com.vms.transportmanagementsystem.enitiy to javafx.base, javafx.fxml;
    opens com.vms.transportmanagementsystem.controller to javafx.fxml;
    opens com.vms.transportmanagementsystem to javafx.fxml;
    exports com.vms.transportmanagementsystem;
}