package client.clientfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class InscriptionLauncher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        InscriptionView View = new InscriptionView();
        InscriptionController Controller = new InscriptionController(View);
        Scene scene = new Scene(View, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Inscription");
        stage.show();
        Controller.start();
    }

    public static void main(String[] args) {
        try {
            launch(args);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
