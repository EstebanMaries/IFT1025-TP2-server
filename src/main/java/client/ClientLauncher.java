package client;


public class ClientLauncher{
    public final static int PORT = 1337;
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