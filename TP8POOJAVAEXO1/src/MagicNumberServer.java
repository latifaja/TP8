import java.io.*;
import java.net.*;
import java.util.Random;

public class MagicNumberServer {
    public static void main(String[] args) {
        final int PORT = 1234;
        Random random = new Random();
        int magicNumber = random.nextInt(101); // Génère un nombre entre 0 et 100
        System.out.println("Le nombre magique est : " + magicNumber);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur en attente de connexions...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connexion acceptée : " + clientSocket.getInetAddress());

                // Gestion des communications avec le client
                try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    out.println("Bienvenue au jeu du nombre magique ! Devinez un nombre entre 0 et 100.");
                    boolean found = false;

                    while (!found) {
                        String input = in.readLine();
                        try {
                            int guess = Integer.parseInt(input);
                            if (guess < magicNumber) {
                                out.println("Votre nombre est trop petit.");
                            } else if (guess > magicNumber) {
                                out.println("Votre nombre est trop grand.");
                            } else {
                                out.println("Félicitations ! Vous avez trouvé le nombre magique !");
                                found = true;
                            }
                        } catch (NumberFormatException e) {
                            out.println("Veuillez entrer un nombre valide.");
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Erreur dans la communication avec le client : " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur du serveur : " + e.getMessage());
        }
    }
}
