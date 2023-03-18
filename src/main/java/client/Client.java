package client;

import server.models.Course;
import server.models.RegistrationForm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {
    public final String MAINPROMPT = "1. Consulter les cours offerts pour une session\n2. S'inscrire à un cours";
    public final String SESSIONPROMPT = "Veuillez choisir la session pour laquelle vous voulez  consulter  la liste de cours:\n1. Automne\n2. Hiver\n3. Ete";
    public final String CHOICE = "> Choix: ";
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private final Socket clientSocket;


    public Client(int port) throws IOException {
        this.clientSocket = new Socket("127.0.0.1", port);
    }
    public void start() {
        try {
            this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

            mainPrompt();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void mainPrompt(){
        Scanner input = new Scanner(System.in);
        System.out.println(MAINPROMPT);
        System.out.print(CHOICE);
        int choice = input.nextInt();
        if (choice==1){
            String session =sessionSelection();
            getCourses(session);
        } else if (choice==2){
            inscription();
        }
    }

    private void inscription(){
        Scanner input = new Scanner(System.in);
        System.out.print("Veuillez saisir votre prénom: ");
        String prenom = input.next();
        System.out.print("Veuillez saisir votre nom: ");
        String nom = input.next();
        System.out.print("Veuillez saisir votre email: ");
        String email = input.next();
        System.out.print("Veuillez saisir votre matricule: ");
        String matricule = input.next();
        System.out.print("Veuillez saisir le code du cours: ");
        String code = input.next();
        System.out.print("Veuillez saisir la session du cours: ");
        String session = input.next();
        System.out.print("Veuillez saisir le nom du cours: ");
        String cours = input.next();
        RegistrationForm form = new RegistrationForm(prenom, nom, email, matricule, new Course(cours,code, session));
        try{

            objectOutputStream.writeObject("INSCRIRE");
            objectOutputStream.writeObject(form);
            System.out.println("Félicitations! Inscription réussie de "+prenom+" au cours "+cours+"\n");

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void getCourses(String session)  {
        ArrayList<Course> courses;
        try{
            objectOutputStream.writeObject("CHARGER "+session);
            Object obj = objectInputStream.readObject();
            if (obj instanceof ArrayList) {
                courses = (ArrayList<Course>) obj;
                System.out.println("Les cours offerts pendant la session d'"+session+" sont:");
                for (int i=0;i<courses.size();i++){
                    Course course = courses.get(i);
                    System.out.println((i+1)+"\t"+course.getName()+"\t"+course.getCode());
                }
            }
        } catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
    }

    public String sessionSelection(){
        Scanner input = new Scanner(System.in);
        System.out.println(SESSIONPROMPT);
        System.out.print(CHOICE);
        int choice = input.nextInt();
        if (choice==1){
            return "Automne";
        } else if (choice==2){
            return "Hiver";
        } else if (choice==3){
            return "Ete";
        }
         return "";
    }



}
