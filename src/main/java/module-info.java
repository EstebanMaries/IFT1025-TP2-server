module inscription {
    requires javafx.controls;
    requires javafx.fxml;

    opens client.clientfx to javafx.fxml;
    exports client.clientfx;
    opens server to javafx.fxml;
    exports server;
    opens server.models to javafx.fxml;
    exports server.models;
}