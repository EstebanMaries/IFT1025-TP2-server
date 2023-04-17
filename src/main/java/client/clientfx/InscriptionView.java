package client.ClientFX;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import server.models.Course;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * InscriptionView est destine à afficher l'interface utilisateur du programme
 * @author Esteban Maries 20235999, Herve Ngisse 20204609
 */
public class InscriptionView extends BorderPane {
    /**
     * divise la fenetre en deux partie
     */
    private SplitPane splitPane;
    /**
     * affiche le conteneur gauche du splitPane
     */
    private VBox leftPane;
    /**
     * affiche le conteneur gauche du splitPane
     */
    private VBox rightPane;
    /**
     * represente la session selectionne
     */
    private String selectedSession= null;
    /**
     * le champs prenom pour la saisie du client
     */
    public TextField firstName;
    /**
     * le champs nom pour la saisie du client
     */
    public TextField name;
    /**
     * le champs mail pour la saisie du client
     */
    public TextField email;
    /**
     * le champs matricule pour la saisie du client
     */
    public TextField matricule;
    /**
     * Bouton pour selectionner une session
     */
    private Button session ;
    /**
     * Bouton pour charger le cours d'une session
     */
    private Button charger;
    /**
     * Bouton pour envoyer le formulaire d'inscription
     */
    private Button sendButton ;
    /**
     * tableau pour afficher les cours
     */
    private TableView<Course> tableView;
    /**
     * Pour afficher le context menu
     */
    private ContextMenu contextMenuSession;
    /**
     * enregistre le cours selectionne
     */
    private ObjectProperty<Course> selectedCourse = new SimpleObjectProperty<>();
    /**
     * organise le champs
     */
    private GridPane grid ;
    /**
     * Pour afficher le titre du formulaire
     */
    private Label titleLabel;


    /**
     * gere l'interface utilisateur du programme
     */
    public InscriptionView() {
        /**
         * lance les deux methodes pour afficher les elements
         */
        columnLeft();
        columnRight();
        /**
         * ajoute les conteurs au splitPane
         */
        splitPane = new SplitPane(leftPane, rightPane);
        splitPane.setPrefSize(800, 600);
        this.setCenter(splitPane);
    }

    //      LA COLONNE GAUCHE

    /**
     * Crée la colonne gauche
     */
    public void columnLeft() {
        /**
         * Crée le tableau
          */
        tableView = new TableView<>();
        createTable();
        /**
         * permet à la table de redimensionner les colonnes automatiquement en fonction de la taille de la table
         */
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        /**
         *  Ajouter les boutons en bas du tableau
         */
        HBox buttonsBox = createButtonsBox();
        /**
         * Crée le conteneur pour le titre et le tableau
         */
        VBox topBox = createTopBox();

        /**
         *  Place les deux conteneurs dans un conteneur VBox global
          */

        leftPane = new VBox(topBox, buttonsBox);
        leftPane.setPrefSize(400, 300);
        leftPane.setPadding(new Insets(10));

        /**
         * Ajoute le conteneur VBox global dans le pane de gauche du SplitPane
         */
        this.setLeft(leftPane);
    }

    //      LES METHODES DE LA COLONNE GAUCHE

    /**
     *configure le Box contenant le titre de la colonne gauche et le titre de la colonne gauche,
     * @return le Box contenant le titre
     */
    private VBox createTopBox() {
        Label label = new Label("Liste de cours");
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        VBox topBox = new VBox(label, tableView);
        topBox.setSpacing(10);
        topBox.setPadding(new Insets(10));
        topBox.setAlignment(Pos.TOP_CENTER);
        return topBox;
    }
    /**
     * Ajoute les boutons en bas du tableau
     * @return le Box Horizontal contenant les deux Boutons
     */
    private HBox createButtonsBox() {
        HBox buttonsBox = new HBox(100);
        buttonsBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonsBox.setPadding(new Insets(10));
        session = new Button("Selectionnez une session");
        charger = new Button("Charger");
        session.setPrefSize(90, 27);
        configureSessionContextMenu();
        buttonsBox.getChildren().addAll(session, charger);
        return buttonsBox;
    }

    /**
     * configure le contex menu de session
     */
    private void configureSessionContextMenu() {
        contextMenuSession = new ContextMenu();
        MenuItem Automne = new MenuItem("Automne");
        MenuItem Hiver = new MenuItem("Hiver");
        MenuItem Ete = new MenuItem("Ete");
        contextMenuSession.getItems().addAll(Automne,Hiver,Ete);
        session.setContextMenu(contextMenuSession);
    }
    /**
     * configure le tableau (les colonnes et affichages des elements dans le tableau)
     */
    private void createTable(){
        tableView = new TableView<>();
        TableColumn<Course, String> columnCode = new TableColumn<>("Code");
        columnCode.setPrefWidth(150);
        columnCode.setStyle("-fx-alignment: CENTER;");
        TableColumn<Course, String> columnCourse = new TableColumn<>("Cours");
        columnCourse.setPrefWidth(250);
        columnCourse.setStyle("-fx-alignment: CENTER;");
        columnCourse.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
        columnCode.setCellValueFactory(new PropertyValueFactory<Course, String>("code"));
        tableView.getColumns().addAll(columnCode, columnCourse);
    }

    //      LES METHODES POUR CHANGER LE SYLE DES ELEMENTS DE LA COLONNE GAUCHE


    /**
     * change la bordure du bouton session
     * @param color  la couleur avec la quelle on veut changer
     */
    public void changeSessionButtonColor(String color) {
        session.setStyle("-fx-border-color:" + color);
    }

    /**
     * change la bordure
     * @param color la couleur avec la quelle on veut changer
     */
    public void changetableBorder(String color){
        tableView.setStyle("-fx-border-color:" + color );
    }



    //      LES GETTERS DES ELEMENTS DE LA COLONNE GAUCHE


    /**
     * recupere le menu contextuel
     * @return le menu contextuel
     */
    public ContextMenu getContextMenuSession() {
        return this.contextMenuSession;
    }

    /**
     * @return la session selectionne dans l'interface du bouton
     */
    public Button getsessionButton() {
        return this.session;
    }
    /**
     * @return le bouton charger
     */
    public Button getChargerButton(){
        return this.charger;
    }
    /**
     * @return le bouton envoyer
     */
    public Button getSendButton(){
        return this.sendButton;
    }

    /**
     * @return le tableau et permet d'y accerder
     */
    public TableView<Course> getTableView() {
        return tableView;
    }

    /**
     * recuperer le cours selectionne dans le tableau
     * @return null si aucun n'est selectonne sinon il retourne le cours selectionne
     */
    public Course getSelectedCourse() {
        Course selectedCourseObject = selectedCourse.get();
        if (selectedCourseObject == null) {
            return null;
        }
        return new Course( selectedCourseObject.getName(),selectedCourseObject.getCode(),
                selectedCourseObject.getSession());
    }


    //      LES SETTERS DES ELEMENTS DE LA COLONNES


    /**
     * definit l'item selectionne dans le menu contextuel comme la valeur du bouton selectionne
     * @param selectedSession la session selection
     */
    public void setSelectedSession(String selectedSession) {
        this.selectedSession= selectedSession;
    }
    /**
     * definit le cours selectionne dans le tableau
     * @param course le cours selectionne
     */
    public void setSelectedCourse(Course course) {
        selectedCourse.set(course);
    }


    //      LA COLONNE DROITE


    /**
     * Crée la colonne droite
     */
    private void columnRight() {
        /**
         *  Crée le titre
         */
        titleLabel = titleRight();
        /**
         *  Crée le titre
         */
        grid = createForm();
        /**
         * Crée le bouton d'envoi
         */
        sendButton = sendButtonRight();
        /**
         * Crée le conteneur VBox de la partie droite
         */
        VBox vbox = VboxRight();
        /**
         * Ajoute le conteneur VBox global dans le pane de gauche du SplitPane
         */
        rightPane = new VBox(vbox);
        rightPane.setPadding(new Insets(10));
        rightPane.setSpacing(10);
    }


    //     LES  METHODES DE LA COLONNE DROITE


    /**
     * configure le formulaire
     * @return
     */
    public GridPane createForm() {
        grid = new GridPane();
        firstName = new TextField();
        name = new TextField();
        email = new TextField();
        matricule = new TextField();

        Label firstNameLabel = new Label("Prénom:");
        Label lastNameLabel = new Label("Nom:");
        Label emailLabel = new Label("Email:");
        Label matriculeLabel = new Label("Matricule:");

        grid.addRow(0, firstNameLabel, firstName);
        grid.addRow(1, lastNameLabel, name);
        grid.addRow(2, emailLabel, email);
        grid.addRow(3, matriculeLabel, matricule);

        grid.setVgap(10);
        grid.getColumnConstraints().add(new ColumnConstraints(100));
        return grid;
    }
    /**
     * configure le bouton envoyer
     * Créer une VBox pour contenir la grille de formulaire et le bouton
     *  Ajouter une marge autour de la VBox
     *  Ajouter un espace entre les éléments de la VBox
     */
    public VBox VboxRight() {
        VBox vbox = new VBox(titleLabel, grid, sendButton);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(50);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }
    /**
     * Ajouter le titre et la VBox à la droite du conteneur principal
     */
    public Label titleRight(){
        Label titleLabel = new Label("Formulaire d'Inscription");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
        VBox titleBox = new VBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(10, 0, 0, 0));
        return titleLabel;
    }
    /**
     * configure le bouton envoyer
     */
    public Button sendButtonRight() {
        Button sendButton = new Button("Envoyer");
        sendButton.setAlignment(Pos.CENTER);
        return sendButton;
    }


    //      LES GETTERS POUR LA COLONNE DROITE


    /**
     * @return le texte dans le champs nom
     */
    public String getFirstName() {
        return firstName.getText();
    }
    /**
     * @return le texte dans le champs name
     */
    public String getName() {
        return name.getText();
    }
    /**
     * @return le texte dans le champs mail
     */
    public String getEmail() {
        return  email.getText();
    }
    /**
     * @return le texte dans le champs matricule
     */
    public String getMatricule() {
        return matricule.getText();
    }


    //      LES METHODES POUR LES BOITES DE DIALOGUES


    /**
     * affiche la confirmation de l'inscription dans une boite de dialogue
     * @param firstName le nom du client
     * @param selectedCourse le cours selectionne
     */
    public void ConfirmationDialog(String firstName, String selectedCourse) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setHeaderText("Message de confirmation");
        alert.setContentText("Félicitations!\nInscription réussie de "  + firstName+  " au cours " +selectedCourse);
        alert.getButtonTypes().remove(ButtonType.CANCEL);
        alert.showAndWait();
    }

    /**
     * affiche une boite de dialogue alerte lorsque le client n'a pas choisi le cours ou sessiion
     * ou remplit correctement le formulaire
     * @param title le titre de l'alerte
     * @param header le contenu de l'alerte
     */
    public void showAlert(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }




}