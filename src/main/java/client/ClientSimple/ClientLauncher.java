package client.ClientSimple;

/**
 * ClientLauncher commence la connection du client avec le serveur
 * @author Esteban Maries 20235999, Herve Ngisse 20204609
 */
public class ClientLauncher{
    /**
     * Port du serveur auquel le client veut se connecter
     */
    public final static int PORT = 1337;

    /**
     * Démarre la connection du client avec le serveur et l'exécute.
     * @param arg un tableau d'arguments de ligne de commande (non-utilisé)
     */
    public static void main(String[] arg)  {
        boolean hasConnected=false;
        while (true){
            Client client;
            try {
                client = new Client(PORT);
                if (!hasConnected){
                    System.out.println("*** Bienvenue au portail d'inscription de cours de l'UDEM ***");
                    hasConnected=true;
                }
                client.start();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
    }
}