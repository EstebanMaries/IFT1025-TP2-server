package client;


public class ClientLauncher{
    public final static int PORT = 1337;
    public static void main(String[] arg)  {
        Client client;
        try {
            client = new Client(PORT);
            System.out.println("Server is running...");
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}