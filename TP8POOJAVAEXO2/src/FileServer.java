import java.io.*;
import java.net.*;

public class FileServer {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur de fichiers en écoute sur le port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nouveau client connecté : " + clientSocket.getInetAddress());
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Erreur serveur : " + e.getMessage());
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            out.println("Bienvenue au serveur de fichiers ! Envoyez le nom du fichier souhaité :");

            String fileName = in.readLine();
            System.out.println("Demande reçue pour le fichier : " + fileName);

            File file = new File(fileName);
            if (file.exists() && !file.isDirectory()) {
                try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
                    out.println("Fichier trouvé. Contenu :");
                    String line;
                    while ((line = fileReader.readLine()) != null) {
                        out.println(line);
                    }
                }
            } else {
                out.println("Fichier introuvable : " + fileName);
            }
        } catch (IOException e) {
            System.err.println("Erreur avec le client : " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("Connexion client fermée.");
            } catch (IOException e) {
                System.err.println("Erreur lors de la fermeture de la connexion client : " + e.getMessage());
            }
        }
    }
}
