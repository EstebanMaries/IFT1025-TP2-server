package client.ClientFX;

import javafx.collections.ObservableList;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import server.models.RegistrationForm;
//import java.awt.event.MouseEvent;

public class InscriptionController {
        private InscriptionView view;
        private InscriptionModel model;
        private String SessionSelected;
        private boolean isWindowOpen = true;

        public InscriptionController(InscriptionView view,InscriptionModel model){
                this.view = view;
                this.model = model;
                this.view.getsessionButton().setOnAction((action) -> {
                        this.session();
                });
                //   this.view.setOnCloseRequest(event -> {
                //      this.isWindowOpen = false;
                //  });
                this.view.getContextMenuSession().setOnAction((action) -> {
                        MenuItem item = (MenuItem) action.getTarget();
                        String selectedSession = item.getText();
                        view.getsessionButton().setText(selectedSession);
                        view.setSelectedSession(selectedSession);
                        System.out.println(selectedSession);
                        model.setSessionModel(selectedSession);
                        //System.out.println(model.setSessionModel(selectedSession));
                        this.contextMenuSession();
                });

                this.view.getChargerButton().setOnAction((action) -> {
                        //System.out.println(this.model.handleLoadCourses());
                        view.getTableView().setItems(this.model.handleLoadCourses(this.view.getSelectedSession()));
                        view.getTableView().getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                                view.setSelectedCourse(newValue);
                              //  System.out.println(view.getSelectedCourse());
                        });
                });
                this.view.getSendButton().setOnAction((action) ->{
                        // Récupérer l'objet Course correspondant à la ligne sélectionnée
                        RegistrationForm form = new RegistrationForm(view.getFirstName(),view.getName(),view.getEmail(),view.getMatricule(), view.getSelectedCourse());
                        this.model.inscription(form);
                });


        }

        private void contextMenuSession() {}

        private void session() {
                view.getContextMenuSession().show(view.getsessionButton(), Side.BOTTOM, 0, 0);
        }

}
