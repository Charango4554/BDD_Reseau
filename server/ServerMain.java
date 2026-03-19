package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/***
 * C’est le point d’entrée du serveur.
Il lance un ServerSocket, attend les connexions, et crée un thread (ClientHandler) pour chaque client connecté.
Il initialise également la base avec DatabaseManager
 */
public class ServerMain {
    private static final int PORT = 12345;
    
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Serveur démarré sur le port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connecté depuis " + clientSocket.getInetAddress());
                ClientHandler handler = new ClientHandler(clientSocket, dbManager);
                new Thread(handler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
