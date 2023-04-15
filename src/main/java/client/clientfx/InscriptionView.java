package client.clientfx;

import javafx.scene.Node;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import server.models.Course;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * ClientLauncher commence la connection du client avec le serveur
 * @author Esteban Maries 20235999, Herve Ngisse 20204609
 */
public class InscriptionView extends BorderPane {
    private SplitPane splitPane;
    private VBox leftPane;
    private VBox rightPane;
    private String selectedSession= null;
    public TextField firstName;
    public TextField name;
    public TextField email;
    public TextField matricule;
    private Button session = new Button("Selectionnez une session");
    private Button charger = new Button("charger");
    private Button sendButton = new Button("Envoyer");
    private TableView<Course> tableView;
    private ContextMenu contextMenuSession;
    private ObjectProperty<Course> selectedCourse = new SimpleObjectProperty<>();
    private GridPane grid = new GridPane();

// Getter pour la propriété Observable

    public InscriptionView() {
        columnLeft();
        columnRight();
        splitPane = new SplitPane(leftPane, rightPane);
        splitPane.setPrefSize(800, 600);
        this.setCenter(splitPane);
    }

    public void columnLeft() {
        // Créer le tableau
        tableView = new TableView<>();
        TableColumn<Course, String> columnCode = new TableColumn<>("Code");
        columnCode.setPrefWidth(150);
        columnCode.setStyle("-fx-alignment: CENTER;");
        TableColumn<Course, String> columnCourse = new TableColumn<>("Cours");
        columnCourse.setPrefWidth(250);
        columnCourse.setStyle("-fx-alignment: CENTER;");
        //pour afficher les noms des cours dans les cellules.
        columnCourse.setCellValueFactory(new PropertyValueFactory<Course, String>("name"));
        columnCode.setCellValueFactory(new PropertyValueFactory<Course, String>("code"));

        //ajoute les deux colonnes au tableau
        tableView.getColumns().addAll(columnCode, columnCourse);
        // permet à la table de redimensionner les colonnes automatiquement en fonction de la taille de la table
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Ajouter les boutons en bas du tableau
        HBox buttonsBox = new HBox(100, session,  charger);
        buttonsBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonsBox.setPadding(new Insets(10));

        session.setPrefSize(90, 27);
        // Ajouter les éléments à afficher pour le bouton session
        contextMenuSession = new ContextMenu();
        MenuItem Automne = new MenuItem("Automne");
        MenuItem Hiver = new MenuItem("Hiver");
        MenuItem Ete = new MenuItem("Ete");
        //ajoute les elements au contextMenu
        contextMenuSession.getItems().addAll(Automne,Hiver,Ete);
        session.setContextMenu(contextMenuSession);

        // Créer le conteneur pour le titre et le tableau
        Label label = new Label("Liste de cours");
        label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        VBox topBox = new VBox(label, tableView);
        topBox.setSpacing(10);
        topBox.setPadding(new Insets(10));
        topBox.setAlignment(Pos.TOP_CENTER);

        // Placer les deux conteneurs dans un conteneur VBox global
        leftPane = new VBox(topBox, buttonsBox);
        leftPane.setPrefSize(400, 300);
        leftPane.setPadding(new Insets(10));

        // Ajouter le conteneur VBox global dans le pane de gauche du SplitPane
        this.setLeft(leftPane);
    }

    public void columnRight() {
        rightPane = new VBox();
        rightPane.setPrefSize(400, 300);

        // Créer le titre en gras et en grande taille
        Label titleLabel = new Label("Formulaire d'Inscription");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 18px;");
        VBox titleBox = new VBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER); // centrer le titre
        titleBox.setPadding(new Insets(10, 0, 0, 0));
        // Créer le champs de formulaire
        firstName = new TextField();
        name = new TextField();
        email = new TextField();
        matricule = new TextField();

        // GridPane grid = new GridPane();
        Label firstNameLabel = new Label("Prénom:");
        Label lastNameLabel = new Label("Nom:");
        Label emailLabel = new Label("Email:");
        Label matriculeLabel = new Label("Matricule:");
        grid.addRow(0, firstNameLabel, firstName);
        grid.addRow(1, lastNameLabel, name);
        grid.addRow(2, emailLabel, email);
        grid.addRow(3, matriculeLabel, matricule);
        grid.setVgap(10);

        // Définir la largeur de la colonne de la grille
        grid.getColumnConstraints().add(new ColumnConstraints(100));

        // Créer un bouton pour envoyer les données
        sendButton.setAlignment(Pos.CENTER); // centrer le bouton

        // Créer une VBox pour contenir la grille de formulaire et le bouton
        VBox vbox = new VBox(titleLabel,grid, sendButton);
        vbox.setPadding(new Insets(10)); // Ajouter une marge autour de la VBox
        vbox.setSpacing(50); // Ajouter un espace entre les éléments de la VBox
        vbox.setAlignment(Pos.CENTER);
        // Ajouter le titre et la VBox à la droite du conteneur principal
        rightPane.getChildren().addAll(titleBox, vbox);
    }


    //les getters et setters de tous les attributs,boutons,etc
    public ContextMenu getContextMenuSession() {
        return this.contextMenuSession;
    }
    public Button getsessionButton() {
        return this.session;
    }
    public Button getChargerButton(){
        return this.charger;
    }
    public Button getSendButton(){
        return this.sendButton;
    }
    public void setSelectedSession(String selectedSession) {
        this.selectedSession= selectedSession;
    }

    public String getSelectedSession() {
        return this.selectedSession;
    }
    public TableView<Course> getTableView() {
        return tableView;
    }

    public Course getSelectedCourse() {
        Course selectedCourseObject = selectedCourse.get();
        if (selectedCourseObject == null) {
            return null;
        }
        return new Course( selectedCourseObject.getName(),selectedCourseObject.getCode(),
                selectedCourseObject.getSession());
    }
    // Setter pour la propriété Observable
    public void setSelectedCourse(Course course) {
        selectedCourse.set(course);
    }

    public String getFirstName() {
        return firstName.getText();
    }
    public String getName() {
        return name.getText();
    }
    public String getEmail() {
        return  email.getText();
    }
    public String getMatricule() {
        return matricule.getText();
    }
    //erreur
    public void changeSessionButtonColor(String color) {
        session.setStyle("-fx-border-color:" + color);
    }
    public void changetableBorder(String color){
        tableView.setStyle("-fx-border-color:" + color );
    }
    public void changeGrid(String color){
        BorderStroke borderStroke =
                new BorderStroke(Color.valueOf(color), BorderStrokeStyle.SOLID, null, new BorderWidths(1));
        Border border = new Border(borderStroke);
        for (Node node : grid.getChildren()) {
            if (node instanceof TextField) {
                ((TextField) node).setBorder(border);
            }
        }
    }

    public void ConfirmationDialog(String firstName, String selectedCourse) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setHeaderText("Message de confirmation");
        alert.setContentText("Félicitations!\nInscription réussie de "  + firstName+  " au cours " +selectedCourse);
        alert.getButtonTypes().remove(ButtonType.CANCEL); // enlever le bouton Annuler
        alert.showAndWait();
    }
    public void showAlert(String title, String header) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

}