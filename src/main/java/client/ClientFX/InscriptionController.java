package client.ClientFX;

import javafx.geometry.Side;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
//import java.awt.event.MouseEvent;

public class InscriptionController {
        private InscriptionView view;
        private InscriptionModel model;
        public InscriptionController(InscriptionView view,InscriptionModel model){
                this.view = view;
                this.model = model;
                this.view.getsessionButton().setOnAction((action) -> {
                        this.session();
                });
                this.view.getContextMenuSession().setOnAction((action) -> {
                        MenuItem item = (MenuItem) action.getTarget();
                        String selectedSession = item.getText();
                        view.getsessionButton().setText(selectedSession);
                        view.setSelectedSession(selectedSession);
                        System.out.println(selectedSession);
                        this.contextMenuSession();
                });

        }

        private void contextMenuSession() {}

        private void session() {
                view.getContextMenuSession().show(view.getsessionButton(), Side.BOTTOM, 0, 0);
        }

        private void listeners() {
                // Ajouter des listeners pour les boutons

                view.getContextMenuSession().setOnAction(event -> {
                        MenuItem item = (MenuItem) event.getTarget();
                        String selectedSession = item.getText();
                        view.getsessionButton().setText(selectedSession);
                        // Sauvegarder l'élément sélectionné dans un attribut
                        // view.setSelectedSession(selectedSession);
                });

                view.getChargerButton().setOnMouseClicked(eventLoad -> {
                        if (eventLoad.getButton().equals(MouseButton.PRIMARY) && eventLoad.getClickCount() == 2) {
                                System.out.println("Mouse ");
                                // Code à exécuter lorsque le bouton "charger" est cliqué
                                // Ajoutez ici le code pour charger les données dans le tableau
                        }});

                view.getSendButton().setOnAction(e -> {
                        // Code à exécuter lorsque le bouton "envoyer" est cliqué
                        // Ajoutez ici le code pour valider le formulaire et envoyer les données au serveur
                });
        }
}
