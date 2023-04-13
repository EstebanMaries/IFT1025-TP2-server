package client.ClientSimple;

import server.models.Course;
import server.models.RegistrationForm;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Client contient toutes les méthodes permettant de communiquer avec le serveur
 * @author Esteban Maries 20235999, Herve Ngisse 20204609
 *
 */
public class Client {

    /**
     * La demande de départ du serveur vers le client
     */
    public final String MAINPROMPT = "1. Consulter les cours offerts pour une session\n2. S'inscrire à un cours";

    /**
     * Demande quelle session le client veut voir
     */
    public final String SESSIONPROMPT = "Veuillez choisir la session pour laquelle vous voulez  consulter  la liste de cours:\n1. Automne\n2. Hiver\n3. Ete";

    /**
     * Correspond à l'espace ou le client doit entrer son choix
     */
    public final String CHOICE = "> Choix: ";

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
    private final Socket clientSocket;


    /**
     * Créer la prise du client avec le serveur
     * @param port le port du serveur
     * @throws IOException si une erreur arrive lors de l'initialisation de la prise
     */
    public Client(int port) throws IOException {
        this.clientSocket = new Socket("127.0.0.1", port);
    }

    /**
     * Commence l'interaction du client avec le serveur.
     */
    public void start() {
        try {
            this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            this.objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

            mainPrompt();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Demande au client ce qu’il cherche à faire.
     */
    private void mainPrompt(){
        Scanner input = new Scanner(System.in);
        System.out.println(MAINPROMPT);
        System.out.print(CHOICE);
        int choice = input.nextInt();
        if (choice==1){
            String session =sessionSelection();
            getCourses(session);
        } else if (choice==2){
            inscription();
        } else {
            mainPrompt();
        }
    }

    /**
     * Inscrit le client au cours choisi.
     */
    private void inscription(){
        String prenom,nom,email,matricule,code,session,cours;
        Scanner input = new Scanner(System.in);
        System.out.print("Veuillez saisir votre prénom: ");
        prenom = input.next();
        System.out.print("Veuillez saisir votre nom: ");
        nom = input.next();
        System.out.print("Veuillez saisir votre email: ");
        email = input.next();
        System.out.print("Veuillez saisir votre matricule: ");
        matricule = input.next();
        System.out.print("Veuillez saisir le code du cours: ");
        code = input.next();
        System.out.print("Veuillez saisir la session du cours: ");
        session = input.next();
        System.out.print("Veuillez saisir le nom du cours: ");
        cours = input.next();
        RegistrationForm form = new RegistrationForm(prenom, nom, email, matricule, new Course(cours, code, session));
        try{
            objectOutputStream.writeObject("INSCRIRE");
            objectOutputStream.writeObject(form);
            Object answer = objectInputStream.readObject();
            if (answer instanceof Boolean){
                System.out.println("Félicitations! Inscription réussie de "+prenom+" au cours "+cours+"\n");
            } else if(answer instanceof String)  {
                System.out.println("\n"+answer+"\n");
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

    /**
     * Imprime les cours de la session
     * @param session la session choisie par le client
     */
    private void getCourses(String session) {
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
            } else {
                System.out.println(obj);
            }
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * Demande au client de choisir une session
     * @return le choix du client
     */
    private String sessionSelection(){
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
        } else {
            return sessionSelection();
        }
    }
}
