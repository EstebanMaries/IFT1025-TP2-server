module inscription {
    requires javafx.controls;
    requires javafx.fxml;

    opens client.ClientFX to javafx.fxml;
    exports client.ClientFX;
    opens server to javafx.fxml;
    exports server;
    opens server.models to javafx.fxml;
    exports server.models;
}