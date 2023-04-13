package client.ClientFX;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import server.models.Course;


public class InscriptionView extends BorderPane {
    private SplitPane splitPane;
    private VBox leftPane;
    private VBox rightPane;
    private String selectedSession= null;
    private TextField firstName;
    private TextField name;
    private TextField email;
    private TextField matricule;
    private Button session = new Button("Choisir une session");
    private Button charger = new Button("charger");
    private Button sendButton = new Button("Envoyer");
    private TableView<String[]> tableView;
    private ContextMenu contextMenuSession;
    private ObjectProperty<String[]> selectedCourse = new SimpleObjectProperty<>();

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
        TableColumn<String[], String> columnCode = new TableColumn<>("Code");
        columnCode.setPrefWidth(150);
        columnCode.setStyle("-fx-alignment: CENTER;");
        TableColumn<String[], String> columnCourse = new TableColumn<>("Cours");
        columnCourse.setPrefWidth(250);
        columnCourse.setStyle("-fx-alignment: CENTER;");
        //pour afficher les elements dans les cellules.
        columnCourse.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                return new SimpleStringProperty(p.getValue()[1]);
            }
        });
        columnCode.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<String[], String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<String[], String> p) {
                return new SimpleStringProperty(p.getValue()[0]);
            }
        });
        //ajoute les deux colonnes au tableau
        tableView.getColumns().addAll(columnCode, columnCourse);
        // permet à la table de redimensionner les colonnes automatiquement en fonction de la taille de la table
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Ajouter les boutons en bas du tableau
        HBox buttonsBox = new HBox(100, session,  charger);
        buttonsBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonsBox.setPadding(new Insets(10));


        // Ajouter les éléments à afficher pour le bouton session
        contextMenuSession = new ContextMenu();
        MenuItem Automne = new MenuItem("Automne");
        MenuItem Hiver = new MenuItem("Hiver");
        MenuItem Ete = new MenuItem("Eté");
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

        // Créer les champs de formulaire
        firstName = new TextField();
        name = new TextField();
        email = new TextField();
        matricule = new TextField();

        // Créer la grille de formulaire avec des étiquettes à gauche de chaque ligne
        GridPane grid = new GridPane();
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


    //les getters et setter de tous les attributs,boutons,etc
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
    public TableView<String[]> getTableView() {
        return tableView;
    }
    // public ObjectProperty<String[]> selectedCourseProperty() {
    //     return selectedCourse;
    // }
    // public String getSelectedCourse() {
    //     String[] selected = selectedCourse.get();
    //     if (selected != null) {
    //         return selected[0] + " (" + selected[1] + ")";
    //     }
    //     return "";
    //return selectedCourse;
    // }
    public Course getSelectedCourse() {
        String[] selectedCourseArray = selectedCourse.get();
        if (selectedCourseArray == null) {
            return null;
        }
        String courseName = selectedCourseArray[0];
        String courseCode = selectedCourseArray[1];
        String courseSession = selectedCourseArray[2];
        return new Course(selectedCourseArray[0], selectedCourseArray[1], selectedCourseArray[2]);
    }
    // Setter pour la propriété Observable
    public void setSelectedCourse(String[] course) {
        selectedCourse.set(course);
    }
    // public TextField getFirstName() {
    //     return firstName;
    //}
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


}