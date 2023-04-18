package server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerLauncher {
    private static final int MIN_PORT = 9090;
    private static final int MAX_PORT = 9095;
    public static int findFreePort() throws IOException {
        int port = -1;
        for (int i = MIN_PORT; i <= MAX_PORT; i++) {
            try {
                System.out.println(i);
                ServerSocket socket = new ServerSocket(i);
                socket.close();
                port = i;
                break;
            } catch (IOException e) {
                // Port already in use, continue searching
                continue;
            }
        }
        if (port == -1) {
            throw new IOException("Could not find a free port between " + MIN_PORT + " and " + MAX_PORT);
        }
        return port;
    }


    /**
     * Démarre le serveur sur un port donné et l'exécute.
     * @param arg un tableau d'arguments de ligne de commande (non-utilisé)
     */
    public static void main(String[] arg) {
        Server server;
        try {
            int port = findFreePort();
            server = new Server(port);
            System.out.println("Server is running...");
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}