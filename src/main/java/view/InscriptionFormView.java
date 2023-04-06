package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import server.models.Course;
import server.models.RegistrationForm;


import java.util.Arrays;
import java.util.List;

public class InscriptionFormView extends Application {

    private Stage primaryStage;
    private final int Width = 800; // attribut pour la longueur de la scène
    private SplitPane splitPane;
    private StackPane leftPane;
    private StackPane rightPane;
    private String selectedSession;
    private TextField firstNameField;
    private TextField nameField;
    private TextField emailField;
    private TextField matriculeField;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        columnLeft();
        columnRight();
        SplitPane splitPane = new SplitPane(leftPane, rightPane);
        Scene scene = new Scene(splitPane, Width, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void columnLeft() {
        //creer le tableau
        leftPane = new StackPane(new Label("Liste de cours"));
        leftPane.setPrefWidth(0.7 * Width); //la taille du tableau de 70% de la hauteur de leftPane
        TableView tableView = new TableView();
        TableColumn<String, String> columnCode = new TableColumn<>("Code");
        TableColumn<String, String> columnCourse = new TableColumn<>("Cours");
        tableView.getColumns().addAll(columnCode, columnCourse);
        leftPane.getChildren().add(tableView);

        // Ajouter les boutons en bas de la leftPane
        HBox buttonsBox = new HBox(10); //
        Button session = new Button("Choisir une session");
        Button charger = new Button("charger");
        buttonsBox.getChildren().addAll(session, charger);
        buttonsBox.setAlignment(Pos.BOTTOM_CENTER);
        buttonsBox.setPadding(new Insets(10)); // padding
        leftPane.getChildren().add(buttonsBox);

        // Ajouter les éléments à afficher pour le bouton session
        List<String> listSession = Arrays.asList("Automne", "Hiver", "Eté");


        // Ajouter un menu contextuel pour le bouton session avec les éléments à afficher
        ContextMenu contextMenuSession = new ContextMenu();
        for (String showSession : listSession) {
            MenuItem menuItem = new MenuItem(showSession);
            menuItem.setOnAction(event -> {
                // Stocker la session sélectionnée
                selectedSession = showSession;
               // selectedSession.setselectedSession(selectedSession);
                // Afficher la session sélectionné
                System.out.println(showSession);
            });
            contextMenuSession.getItems().add(menuItem);
        }
        session.setContextMenu(contextMenuSession);
        //

        //action pour afficher les cours
       // charger.setOnAction(e -> handleShowCourseBtn());
    }

    public void columnRight() {
        rightPane = new StackPane(new Label("Formulaire d'Inscription"));
        // Créer les champs de formulaire
        firstNameField = new TextField();
        firstNameField.setPromptText("Prénom");
        firstNameField = new TextField();
        nameField.setPromptText("Nom");
        emailField = new TextField();
        emailField.setPromptText("Email");
        matriculeField = new TextField();
        matriculeField.setPromptText("Matricule");
        GridPane grid = new GridPane();
        grid.addRow(0, firstNameField);
        grid.addRow(1, nameField);
        grid.addRow(2, emailField);
        grid.addRow(3, matriculeField);
        RegistrationForm form = new RegistrationForm(firstNameField,firstNameField, emailField,  matriculeField, new Course(cours,code, session));
        // Créer un bouton pour envoyer les données
        Button sendButton = new Button("Envoyer");
       // sendButton.setOnAction(e -> handlesendButton());
        VBox vbox = new VBox(grid, sendButton);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);
        rightPane.getChildren().add(vbox);


    }
    public String getSelectedSession(String selectedSession) {
        return this.selectedSession = selectedSession;
    }
    public void setSelectedSession(String selectedSession){
        this.selectedSession= selectedSession;
    }



}

