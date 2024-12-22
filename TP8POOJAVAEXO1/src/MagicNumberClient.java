import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MagicNumberClient {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost";
        final int PORT = 1234;

        try (Socket socket = new Socket(SERVER_ADDRESS, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connecté au serveur.");
            System.out.println(in.readLine()); // Message de bienvenue

            boolean gameFinished = false;

            while (!gameFinished) {
                System.out.print("Entrez un nombre : ");
                String input = scanner.nextLine();
                out.println(input);

                String response = in.readLine();
                System.out.println(response);

                if (response.contains("Félicitations")) {
                    gameFinished = true;
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
    }
}
