import java.io.*;
import java.net.*;

public class FileClient {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Connecté au serveur de fichiers.");
            System.out.println(in.readLine()); // Message de bienvenue

            System.out.print("Entrez le nom du fichier : ");
            String fileName = consoleReader.readLine();
            out.println(fileName);

            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println(serverResponse);
            }
        } catch (IOException e) {
            System.err.println("Erreur client : " + e.getMessage());
        }
    }
}
