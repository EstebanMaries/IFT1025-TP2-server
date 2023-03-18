package server;

import javafx.util.Pair;
import server.models.Course;
import server.models.RegistrationForm;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class Server {

    public final static String REGISTER_COMMAND = "INSCRIRE";
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    /**
        * Crée un nouveau serveur qui écoute sur le port spécifié.
        * @param port Le port sur lequel le serveur doit écouter.
        * @throws IOException Si une erreur se produit lors de la création du socket du serveur.
    */
    public Server(int port) throws IOException {
        this.server = new ServerSocket(port,1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     * Ajoute un événement h au géreur d’événements.
     * @param h de type EventHandler, l’événement à ajouter.
     */
    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    /**
     * Mets en action les événements ajoutés.
     * @param cmd la commande à renvoyer
     * @param arg l’argument a donné à la commande.
     */
    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    /**
     * Commence l’interaction client serveur
     */
    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
                System.out.println("Client déconnecté!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Récupère les entrées du client.
     * @throws IOException, ClassNotFoundException si une erreur se produit lors de la lecture des entrées.
     */
    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    /**
     * Mets en page la commande obtenue.
     * @param line une commande
     * @return Pair la separation entre le nom de la commande et son argument
     */
    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    /**
     * Déconnecte le client du serveur.
     * @throws IOException si une erreur se produit lors de la déconnection.
     */
    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     * Gère la commande donnée.
     * @param cmd la commande à traiter
     * @param arg l'argument a utilisé avec la commande.
     */
    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration();
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     Lire un fichier texte contenant des informations sur les cours et les transformer en liste d'objets 'Course'.
     La méthode filtre les cours par la session spécifiée en argument.
     Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     @param arg la session pour laquelle on veut récupérer la liste des cours
     @throws Exception si une erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux
     */
    public void handleLoadCourses(String arg) {
        ArrayList<Course> relevantClasses = new ArrayList<>();
        try {
            FileReader classes = new FileReader("data/cours.txt");
            BufferedReader reader = new BufferedReader(classes);
            String line;
            System.out.println("Gathering courses...");
            while ((line = reader.readLine()) != null) {
                String[] content = line.split("\t");
                if (content[2].equals(arg)) {
                    relevantClasses.add(new Course(content[1], content[0], content[2]));
                }
            }
            this.objectOutputStream.writeObject(relevantClasses);
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     et renvoyer un message de confirmation au client.
     @throws Exception ClassNotFoundException si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration() {
        BufferedWriter writer;
        try {
            Object form = objectInputStream.readObject();
            if (form instanceof RegistrationForm){
                RegistrationForm rForm = (RegistrationForm) form;
                writer = new BufferedWriter(new FileWriter("data/inscription.txt", true));
                String session = rForm.getCourse().getSession();
                String code = rForm.getCourse().getCode();
                String matricule = rForm.getMatricule();
                String prenom = rForm.getPrenom();
                String nom = rForm.getNom();
                String email = rForm.getEmail();
                writer.append(session+"\t"+code+"\t"+matricule+"\t"+prenom+"\t"+nom+"\t"+email+"\t\n");
                writer.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}