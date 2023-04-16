package server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerBonusLauncher {
    static ServerSocket serverSocket; // 0 permet au système de choisir un port libre

    static {
        int attempts = 0;
        while (attempts < 10) { // Limite le nombre de tentatives pour éviter une boucle infinie
            try {
                serverSocket = new ServerSocket(0);
                break; // Sort de la boucle si la création de ServerSocket réussit
            } catch (IOException e) {
                attempts++;
            }
        }
    }

    static int PORT = serverSocket.getLocalPort(); // Récupérer le port choisi par le système

    /**
     * Le port utilisé pour le serveur
     */
    //public final static int PORT = 9090;

    public ServerBonusLauncher() throws IOException {
        Server server;
        try {
            server = new Server(PORT);
            System.out.println("Server is running...");
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Démarre le serveur sur un port donné et l'exécute.
     * @param arg un tableau d'arguments de ligne de commande (non-utilisé)
     */
    public static void main(String[] arg) throws IOException {
        new ServerLauncher();
    }
}