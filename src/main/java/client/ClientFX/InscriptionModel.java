package client.ClientFX;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.models.RegistrationForm;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class InscriptionModel {
    private static final String HOST = "localhost";
    private static final int PORT = 6060;

    private ObjectOutputStream objectOutputStream;

    private ObjectInputStream objectInputStream;

    private final Socket clientSocket;
    public String SessionModel;

    public InscriptionModel() throws IOException {
        this.clientSocket = new Socket(HOST, PORT);
    }
    public void start() {
        try {
            this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

            // mainPrompt();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public ObservableList<String[]> handleLoadCourses(String arg) {
        ArrayList<String[]> relevantClasses = new ArrayList<>();
        try {
            objectOutputStream.writeObject("CHARGER "+ SessionModel );
            System.out.println(SessionModel);
            Object obj = objectInputStream.readObject();
            if (obj instanceof ArrayList) {
                FileReader classes = new FileReader("data/cours.txt");
                BufferedReader reader = new BufferedReader(classes);
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] content = line.split("\t");
                    if (content[2].equals(arg)) {
                        relevantClasses.add(content);
                    }
                }
                reader.close();
            }
        } catch (Exception e) {
            System.out.println(SessionModel);
            throw new RuntimeException(e);
        }
        // Création de la liste observable
        ObservableList<String[]> coursesList = FXCollections.observableArrayList(relevantClasses);

        // Retourne la liste observable
        return coursesList;
    }
    public void inscription(RegistrationForm form){

        //  RegistrationForm form = new RegistrationForm(prenom, nom, email, matricule, new Course(cours, code, session));
        try{
            objectOutputStream.writeObject("INSCRIRE");
            objectOutputStream.writeObject(form);
            Object answer = objectInputStream.readObject();
            if (answer instanceof Boolean){
                //System.out.println("Félicitations! Inscription réussie de "+prenom+" au cours "+cours+"\n");
                System.out.println("Félicitations! Inscription réussie de ");
            } else {
                System.out.println("Erreurs");
                ArrayList<Integer> info = (ArrayList<Integer>) answer;
                if (info.get(0) == 1) System.out.println("Le prénom est incorrect.");
                if (info.get(1) == 1) System.out.println("Le nom est incorrect.");
                if (info.get(2) == 1) System.out.println("Le mail est incorrect.");
                if (info.get(3) == 1) System.out.println("Le matricule est incorrect.");
                if (info.get(4) == 1) System.out.println("Le cours est incorrect.");
            }
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public void setSessionModel(String SessionModel){
        this.SessionModel= SessionModel;
    }
}

