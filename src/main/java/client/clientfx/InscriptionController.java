package client.clientfx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import server.models.Course;
import server.models.RegistrationForm;
import javafx.geometry.Side;
import javafx.scene.control.MenuItem;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * InscriptionController est destine à gerer les interactions entre le client et le serveur via  :
 * le modele : RegistrationForm et Course
 * la Vue : InscriptionView
 * @author Esteban Maries 20235999, Herve Ngisse 20204609
 */
public class InscriptionController {
    /**
     * permet d'acceder à InscriptionView
     */
    private InscriptionView view;
    /**
     * selectionne la session
     */
    private String selectedSession;
    /**
     * verifie si la fenetre de l'utilisateur est ouvert
     */
    private boolean isWindowOpen = true;


    //     LES ATTRIBUTS ET OBJETS DE CONNEXION


    /**
     * adresse du serveur auquel le client veut se connecter
     */
    private static final String HOST = "127.0.0.1";
    /**
     * Port du serveur auquel le client veut se connecter
     */
    private static final int PORT = 9090;
    /**
     * La sortie des arguments du client vers le serveur
     */

    private ObjectOutputStream objectOutputStream;
    /**
     * L'entrée des arguments du serveur vers le client
     */

    private ObjectInputStream objectInputStream;

    /**
     * La prise du client
     */

    private Socket clientSocket;


    public InscriptionController(InscriptionView view) throws IOException {

        //      LA CONNEXION

        try {
            this.clientSocket = new Socket(HOST, PORT);
            this.view = view;
        }catch (Exception e){
            errorAlertConnexion();
        }


        //      LES ACTIONS

        /**
         * Ajoute une action sur le bouton session dans le but d'afficher le menu contextuel
         * et affiche les sessions dans le menu contextuel
         */
        this.view.getsessionButton().setOnAction((action) -> {
            view.getContextMenuSession().show(view.getsessionButton(), Side.BOTTOM, 0, 0);
        });

        /**
         * Ajoute une action lorsqu'on clique sur un element dans le menu contextuell
         */
        this.view.getContextMenuSession().setOnAction((action) -> {
            /**
             * changer la valeur afficher dans l'interface du bouton session par l'element selectionne
             * dans le menu contextuel
             */
            changeValueBtnSession(action);
        });

        /**
         * lorsqu'on clique sur le bouton charger
         */
        this.view.getChargerButton().setOnAction((action) -> {
            /**
             * reinitialise la couleur des bordures du tableau, des boutons et des grilles
             */
            colorInitializer();
            /**
             * charge une session
             */
            getCourses();
            /**
             * place les cours de la session selectionne dans le tableau
             */
            setItemsInTab();
            /**
             *  reconnecter le client au serveur
             * @throws IOException
             */
            try {
                reconnect();
            } catch (IOException e) {
                errorAlertConnexion();
            }
        });

        /**
         * lorsqu'on clique sur le bouton envoyer
         */
        this.view.getSendButton().setOnAction((action) -> {
            /**
             * reinitialise la couleur des bordures du tableau, des boutons et des grilles
             */
            colorInitializer();
            /**
             * envoie la requete d'inscription au cours au serveur
             */
            doInscription();
            /**
             *  reconnecter le client au serveur
             * @throws IOException
             */
            try {
                reconnect();
            } catch (IOException e) {
                errorAlertConnexion();
            }
        });
    }

    //      LES METHODES DE CONNEXION


    /**
     * reconnecte le client apres une requete vers le serveur
     * Start() recree une prise avec le meme port et vers le meme serveur
     * @throws IOException
     */
    private void reconnect() throws IOException {
        /**
         * ferme le socket
         */
        if (clientSocket != null) {
            try {
                objectOutputStream.close();
                objectInputStream.close();
                clientSocket.close();
            } catch (IOException e) {
                errorAlertConnexion();
            }
        }
        this.clientSocket = new Socket(HOST, PORT);
        this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
    }

    /**
     * Créer la prise du client avec le serveur
     * @throws IOException si une erreur arrive lors de l'initialisation de la prise
     */
    public void start() {
        try {
            this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
           errorAlertConnexion();
        }
    }


    //      LES METHODES QUI COMMUNIQUENT AVEC MODEL ET VIEW


    /**
     * Envoie la requete au serveur de fournir la liste des cours d'une session
     */
    private void getCourses() {
        ArrayList<Course> courses;
        /**
         * verifie si une session est selectionnee
         */
        if (selectedSession != null) {
            /**
             * essaie si la connexion est etablie
             */
            try {
                /**
                 * envoie la requete CHARGER + le nom de la session au serveur
                 */
                objectOutputStream.writeObject("CHARGER " + selectedSession);
                /**
                 * enregistre la reponse du serveur dans un objet
                 */
                Object obj = objectInputStream.readObject();
                /**
                 * verifie si l'objet est une instance de cours
                 */
                if (obj instanceof ArrayList) {
                    courses = (ArrayList<Course>) obj;
                    /**
                     * si aucun cours n'est renvoye.le controlleur afficher le message d'erreur
                     */
                    if (courses.isEmpty()) {
                        errorAlert();
                        /**
                         * sinon il transforme la liste de cours en une liste observable
                         * ensuite il place les elements dans le tableau
                         */
                    } else {
                        ObservableList<Course> courseShow = FXCollections.observableArrayList(courses);
                        view.getTableView().setItems(courseShow);
                    }
                }

            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            /**
             * si aucun cours n'est selectionne,il affiche un message d'erreur
             */
        } else {
            errorAlert();
        }
    }

    /**
     * Envoie la requete au serveur d'inscrire un etudiant a un cours
     */
    private void doInscription() {
        /**
         * envoie les informations ecrit dans le formulaire
         */
        RegistrationForm form =
                new RegistrationForm(view.getFirstName(), view.getName(), view.getEmail(), view.getMatricule(),
                        view.getSelectedCourse());
        /**
         * cree un tableau d'erreurs
         */
        List<String> errorMsgs = new ArrayList<>();
        /**
         * verifie si un cours et selectionne dans le tableau
         */
        if (view.getSelectedCourse() != null) {
            /**
             * essaie si la connexion est etablie
             */
            try {
                /**
                 * envoie la requete INSCRIRE + l'objet form
                 */
                objectOutputStream.writeObject("INSCRIRE");
                objectOutputStream.writeObject(form);
                /**
                 * lit la reponse du serveur
                 */
                Object answer = objectInputStream.readObject();
                if (answer instanceof Boolean) {
                    confirmationRegistration();
                } else if (answer instanceof String) {
                    System.out.println("\n" + answer + "\n");
                } else {
                    /**
                     * defini la couleur du bordure
                     */
                    BorderStroke borderStroke = new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, new BorderWidths(1));
                    Border border = new Border(borderStroke);
                    /**
                     * condition pour chaque champs du formulaire
                     * ajoute l'erreur dans le tableau
                     * et change la bordure du champs
                     */
                    ArrayList<Integer> info = (ArrayList<Integer>) answer;
                    if (info.get(0) == 1) {
                        view.firstName.setBorder(border);
                        errorMsgs.add("Le prénom est incorrect.");
                    }
                    if (info.get(1) == 1) {
                        view.name.setBorder(border);
                        errorMsgs.add("Le nom est incorrect.");
                    }
                    if (info.get(2) == 1) {
                        view.email.setBorder(border);
                        errorMsgs.add("Le mail est incorrect.");
                    }
                    if (info.get(3) == 1) {
                        view.matricule.setBorder(border);
                        errorMsgs.add("Le matricule est incorrect.");
                    }
                    /**
                     * affiche les erreurs si la liste n'est vide pas vide
                     */
                    if (!errorMsgs.isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        for (String msg : errorMsgs) {
                            sb.append(msg).append("\n");
                        }
                        view.showAlert("Ces informations sont incorrecte", sb.toString());
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            /**
             * affiche les erreurs lient a la selection d'un cours
             */
        } else if (selectedSession == null && view.getSelectedCourse() == null) {
            errorAlert();
        } else if (selectedSession == null) {
            errorAlert();
        } else {
            errorAlert();
        }
    }


    //      METHODES


    /**
     * sauvegarde le cours selectionne dans le menu Contextuel
     * recuperer la valeur deja dans le bouton et la remplace par
     * affiche dans l'interface du bouton,le cours selectionnée dans le menu Contextuel
     */
    private void changeValueBtnSession(ActionEvent action){
        MenuItem item = (MenuItem) action.getTarget();
        selectedSession = item.getText();
        view.getsessionButton().setText(selectedSession);
        view.setSelectedSession(selectedSession);
    }


    /**
     * initialise la couleur des bordures
     */
    private void colorInitializer() {
        Border transparent = Border.EMPTY;
        view.changeSessionButtonColor("transparent");
        view.changetableBorder("transparent");
        view.firstName.setBorder(transparent);
        view.name.setBorder(transparent);
        view.email.setBorder(transparent);
        view.matricule.setBorder(transparent);
    }

    /**
     * affiche les erreurs et change la bordure en rouge
     */
    private void errorAlert() {
        if (selectedSession == null && view.getSelectedCourse() == null) {
            view.changeSessionButtonColor("red");
            view.changetableBorder("red");
            view.showAlert("Session invalide et cours invalide", "Veuillez sélectionner une session et un cours");
        } else if (selectedSession == null ) {
            view.changeSessionButtonColor("red");
            view.showAlert("Session invalide", "Veuillez sélectionner une session");
         }else {
            view.changetableBorder("red");
            view.showAlert("Cours invalide", "Veuillez sélectionner un cours");
        }
    }

    /**
     * pour les erreurs lient à la connexion avec le serveur
     */
    private void errorAlertConnexion(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de connexion");
        alert.setHeaderText("Impossible de se connecter au serveur \n Veuillez réesayer");
        alert.showAndWait();
    }


    /**
     * affiche le message de confirmation
     * efface les elements dans le tableau,le formulaire et reinitialialise la valeur du bouton session
     */
    private void confirmationRegistration(){
        view.ConfirmationDialog(view.getFirstName(), view.getSelectedCourse().getName());
        view.getTableView().getItems().clear();
        view.getsessionButton().setText("Selectionnez une session");
        selectedSession = null;
        view.firstName.setText("");
        view.name.setText("");
        view.email.setText("");
        view.matricule.setText("");
    }

    //      SETTER


    /**
     * place les elements dans le tableau
     */
    private void setItemsInTab(){
        view.getTableView().getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> {
                    view.setSelectedCourse(newValue);
                });
    }
}