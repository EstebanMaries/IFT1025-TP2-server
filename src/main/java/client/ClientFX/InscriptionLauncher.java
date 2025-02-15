package client.ClientFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * InscriptionLauncher étend l'interface Application.
 * @author Esteban Maries 20235999, Herve Ngisse 20204609
 */
public class InscriptionLauncher extends Application {

    /**
     * Start est appelé lorsque le programme est lancé et prend en
     * @param stage qui représente la fenêtre principale du programme.
     */
    @Override
    public void start(Stage stage) throws Exception {

        // InscriptionView représente la vue de l'interface utilisateur
        InscriptionView View = new InscriptionView();

        // InscriptionController est responsable de la gestion des interactions de l'utilisateur avec l'interface
        InscriptionController Controller = new InscriptionController(View);

        // Scene définit la taille de la fenêtre
        Scene scene = new Scene(View, 800, 600);

        // stage
        stage.setScene(scene);
        stage.setTitle("Inscription");
        stage.show();

        // connecte le client au serveur
        Controller.start();
    }

    /**
     * main crée une instance d'InscriptionLauncher et lance le programme
     * @param args un tableau d'arguments de ligne de commande
     */
    public static void main(String[] args) {
        //tentative de lancement
        try {
            launch(args);
            // Arrête le programme si un composant est manquant
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur de chargement. \n Veuillez réessayer");
            alert.showAndWait();
            e.printStackTrace();
            //System.exit(1);
        }
    }
}
