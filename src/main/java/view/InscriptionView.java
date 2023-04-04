package view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.models.InscriptionController;

public class InscriptionView {
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField matriculeField;
    @FXML
    private Button sendButton;
    private Button defilementButton;
    private Button changeButton;
    private GridPane leftColumnData;

    public InscriptionView() {
        // Define left column
        VBox leftColumn = new VBox();
        leftColumn.setSpacing(10);

        // Define left column headers
        HBox leftColumnHeaders = new HBox();
        leftColumnHeaders.setSpacing(10);
        Label codeHeader = new Label("Code");
        Label courseHeader = new Label("Course");
        leftColumnHeaders.getChildren().addAll(codeHeader, courseHeader);

        // Define left column data
        leftColumnData = new GridPane();
        leftColumnData.setHgap(10);
        leftColumnData.setVgap(5);
        ColumnConstraints column1 = new ColumnConstraints(50);
        ColumnConstraints column2 = new ColumnConstraints(150);
        leftColumnData.getColumnConstraints().addAll(column1, column2);
        for (int i = 1; i <= 10; i++) {
            Label codeLabel = new Label("Code " + i);
            Label courseLabel = new Label("Course " + i);
            leftColumnData.addRow(i-1, codeLabel, courseLabel);
        }

        // Add left column headers and data to left column
        leftColumn.getChildren().addAll(leftColumnHeaders, leftColumnData);

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(5);
        ColumnConstraints column1Form = new ColumnConstraints(100);
        ColumnConstraints column2Form = new ColumnConstraints(150);
        form.getColumnConstraints().addAll(column1Form, column2Form);
        Label firstNameLabel = new Label("prenom:");
        firstNameField = new TextField();
        Label lastNameLabel = new Label("nom:");
        lastNameField = new TextField();
        Label emailLabel = new Label("Email:");
        emailField = new TextField();
        Label matriculeLabel = new Label("Matricule:");
        matriculeField = new TextField();
        sendButton = new Button("envoyer");
        form.addRow(0, firstNameLabel, firstNameField);
        form.addRow(1, lastNameLabel, lastNameField);
        form.addRow(2, emailLabel, emailField);
        form.addRow(3, matriculeLabel, matriculeField);
        form.addRow(4, sendButton);

        setCenter(form);

        defilementButton = new Button("session");
        changeButton = new Button("Changer");
       // TextField bottomRow;
       // bottomRow.getChildren().addAll(defilementButton, changeButton);
       // bottomRow.setAlignment(Pos.CENTER_RIGHT);

        //setBottom(bottomRow);

        //setPadding(new Insets(10));
        //setPrefSize(800, 600);
    }

    private void setCenter(GridPane form) {
    }

    public String getFirstName() {
        return firstNameField.getText();
    }

    public String getLastName() {
        return lastNameField.getText();
    }

    public String getEmail() {
        return emailField.getText();
    }

    public String getMatricule() {
        return matriculeField.getText();
    }

    public void setFirstName(String firstName) {
        firstNameField.setText(firstName);
    }

    public void setLastName(String lastName) {
        lastNameField.setText(lastName);
    }

    public void setEmail(String email) {
        emailField.setText(email);
    }

    public void setMatricule(String matricule) {
        matriculeField.setText(matricule);
    }



    public void addDefilementButtonListener(InscriptionController.send  sendListener) {
    }

    public void addChangeButtonListener(InscriptionController.ChangeButtonListener changeButtonListener) {
    }
}

