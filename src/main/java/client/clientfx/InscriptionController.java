package client.ClientFX;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class InscriptionController {
    private InscriptionView view;
    private String selectedSession;
    private boolean isWindowOpen = true;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 1337;

    private ObjectOutputStream objectOutputStream;

    private ObjectInputStream objectInputStream;

    private Socket clientSocket;

    public InscriptionController(InscriptionView view) throws IOException {
        this.clientSocket = new Socket(HOST, PORT);
        this.view = view;
        this.view.getsessionButton().setOnAction((action) -> {
            this.session();
        });
        this.view.getContextMenuSession().setOnAction((action) -> {
            MenuItem item = (MenuItem) action.getTarget();
            selectedSession = item.getText();
            view.getsessionButton().setText(selectedSession);
            view.setSelectedSession(selectedSession);
            System.out.println(selectedSession);
            this.contextMenuSession();
        });
        this.view.getChargerButton().setOnAction((action) -> {
            colorInitializer();
            getCourses();
            view.getTableView().getSelectionModel().selectedItemProperty().
                    addListener((observable, oldValue, newValue) -> {
                        view.setSelectedCourse(newValue);
                    });
            try {
                reconnect();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.view.getSendButton().setOnAction((action) -> {
            colorInitializer();
            doInscription();
            try {
                reconnect();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


    }

    private void reconnect() throws IOException {
        this.clientSocket = new Socket(HOST, PORT);
        start();
    }

    public void start() {
        try {
            this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getCourses() {
        ArrayList<Course> courses;
        if (selectedSession != null) {
            try {
                objectOutputStream.writeObject("CHARGER " + selectedSession);
                Object obj = objectInputStream.readObject();
                if (obj instanceof ArrayList) {
                    courses = (ArrayList<Course>) obj;
                    if (courses.isEmpty()) {
                        view.changetableBorder("red");
                        view.showAlert("Cours invalide", "Veuillez sélectionner un cours");
                    } else {
                        ObservableList<Course> courseShow = FXCollections.observableArrayList(courses);
                        System.out.println("Les cours offerts pendant la session d'" + selectedSession + " sont:");
                        view.getTableView().setItems(courseShow);
                    }
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            view.changeSessionButtonColor("red");
            view.showAlert("Session invalide", "Veuillez sélectionner une session");
        }
    }

    private void doInscription() {
        RegistrationForm form =
                new RegistrationForm(view.getFirstName(), view.getName(), view.getEmail(), view.getMatricule(),
                        view.getSelectedCourse());
        List<String> errorMsgs = new ArrayList<>();
        if (view.getSelectedCourse() != null) {
            try {
                objectOutputStream.writeObject("INSCRIRE");
                objectOutputStream.writeObject(form);
                Object answer = objectInputStream.readObject();
                if (answer instanceof Boolean) {
                    view.ConfirmationDialog(view.getFirstName(), view.getSelectedCourse().getName());
                    view.getTableView().getItems().clear();
                    view.getsessionButton().setText("Selectionnez une session");
                    selectedSession = null;
                    view.firstName.setText("");
                    view.name.setText("");
                    view.email.setText("");
                    view.matricule.setText("");
                } else if (answer instanceof String) {
                    System.out.println("\n" + answer + "\n");
                } else {
                    BorderStroke borderStroke = new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, new BorderWidths(1));
                    Border border = new Border(borderStroke);
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
                    if (!errorMsgs.isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        for (String msg : errorMsgs) {
                            sb.append(msg).append("\n");
                        }
                        view.showAlert("Ces informations sont incorrecte", sb.toString());
                    }
                    // Modifier la bordure des éléments correspondant aux erreurs

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else if (selectedSession == null && view.getSelectedCourse() == null) {
            view.changeSessionButtonColor("red");
            view.changetableBorder("red");
            view.showAlert("Session invalide et cours invalide", "Veuillez sélectionner une session et un cours");
        } else if (selectedSession == null) {
            view.changeSessionButtonColor("red");
            view.showAlert("Session invalide", "Veuillez sélectionner une session");
        } else {
            view.changetableBorder("red");
            view.showAlert("Cours invalide", "Veuillez sélectionner un cours");
        }
    }
    private void colorInitializer() {
        Border transparent = Border.EMPTY;
        view.changeSessionButtonColor("transparent");
        view.changetableBorder("transparent");
        view.firstName.setBorder(transparent);
        view.name.setBorder(transparent);
        view.email.setBorder(transparent);
        view.matricule.setBorder(transparent);
    }

    private void contextMenuSession() {
    }

    private void session() {
        view.getContextMenuSession().show(view.getsessionButton(), Side.BOTTOM, 0, 0);
    }
}