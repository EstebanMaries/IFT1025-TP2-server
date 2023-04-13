package client.ClientFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InscriptionLauncher extends Application {
    // public final static int PORT = 1337;
    @Override
    public void start(Stage stage) throws Exception {
        InscriptionModel Model = new InscriptionModel();
        InscriptionView View = new InscriptionView();
        InscriptionController Controller = new InscriptionController(View, Model);
        Scene scene = new Scene(View, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Inscription");
        stage.show();
        Model.start();
    }

    public static void main(String[] args) {
        try {
            launch(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

