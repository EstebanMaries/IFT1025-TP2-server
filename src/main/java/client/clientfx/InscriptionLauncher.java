package client.clientfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * InscriptionLauncher etend l'interface Application.
 * @author Esteban Maries 20235999, Herve Ngisse 20204609
 */
public class InscriptionLauncher extends Application {
    /**
     *Start est appele lorsque le programme est lancee et prend en
     * @param stage qui représente la fenêtre principale du programme.
     */
    @Override
    public void start(Stage stage) throws Exception {
            /**
             * InscriptionView reprensete la vue de l'interface utilisateur
             */
            InscriptionView View = new InscriptionView();
            /**
             * InscriptionnController est responsable de la gestion des interactions de l'utilisateur avec l'interface
             */
            InscriptionController Controller = new InscriptionController(View);
            /**
             *Scene definit la taille de la fenetre
             */
            Scene scene = new Scene(View, 800, 600);
            /**
             * stage
             */
            stage.setScene(scene);
            stage.setTitle("Inscription");
            stage.show();
            /**
             * connecte le client au serveur
             */
            Controller.start();
    }
    /**
     * main cree une instance d'InscriptionLauncher et lance le programme
     * @param args un tableau d'arguments de ligne de commande
     */
    public static void main(String[] args) {
        /**
         * tentative de lancement
         */
        try {
            launch(args);
            /**
             * arrete le programme si une composante est manquante
             */
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur de chargement. \n Veuillez réessayer");
            alert.showAndWait();
            e.printStackTrace();
            System.exit(1);
        }
    }
}
