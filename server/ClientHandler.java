package server;

import common.*;
import java.io.*;
import java.net.Socket;

/**
 * Chaque instance de cette classe gère un client dans un thread séparé.
Elle lit les requêtes (Request), les traite via handleRequest(), puis renvoie une Response.
C’est le cœur de la logique client/serveur côté serveur.
 */

public class ClientHandler implements Runnable {
    private Socket clientSocket; //Socket réseau du client
    private DatabaseManager dbManager; //Ref Base de Donnée
    private ObjectInputStream in; //Request
    private ObjectOutputStream out; //Response
    
    //Initialisation
    public ClientHandler(Socket clientSocket, DatabaseManager dbManager) {
        this.clientSocket = clientSocket;
        this.dbManager = dbManager;
    }
    
    //Dialogue complet avec le client
    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream()); //Ouverture flux réseaux
            out.flush(); // envoyer l'en-tête
            in = new ObjectInputStream(clientSocket.getInputStream());
            
            boolean running = true;
            while (running) {
                Object obj = in.readObject(); //Lecture objet
                if (obj instanceof Request) {
                    Request req = (Request) obj;
                    Response res = handleRequest(req);
                    out.writeObject(res);
                    out.flush();
                    //Déconnexion:
                    if(req.getType() == Request.RequestType.LOGOUT) {
                        running = false;
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Client déconnecté (" + e.getMessage() + ").");
        } finally {
            try {//Fermeture du flux
                if (in != null) in.close();
                if (out != null) out.close();
                if (clientSocket != null) clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    //Traite les requête selon les demandes clients
    private Response handleRequest(Request req) {
        switch(req.getType()) {
            case LOGIN:
                // Pour simplifier : on accepte la connexion si le mot de passe est identique au nom d'utilisateur.
                if(req.getData() instanceof User) {
                    User user = (User) req.getData();
                    if(user.getPassword().equals(user.getUsername())) {
                        return new Response(Response.Status.OK, "Authentification réussie.", null);
                    } else {
                        return new Response(Response.Status.ERROR, "Identifiants invalides.", null);
                    }
                }
                break;
            case CREATE:
                if(req.getData() instanceof DBObject) {
                    DBObject dbObj = (DBObject) req.getData();
                    String message = dbManager.create(dbObj);
                    return new Response(Response.Status.OK, message, null);
                }
                break;
            case READ:
                if(req.getData() instanceof Integer) {
                    DBObject result = dbManager.read((Integer) req.getData());
                    if(result != null) {
                        return new Response(Response.Status.OK, "Objet trouvé.", result);
                    } else {
                        return new Response(Response.Status.ERROR, "Objet non trouvé.", null);
                    }
                }
                break;
            case UPDATE:
                if(req.getData() instanceof DBObject) {
                    DBObject dbObj = (DBObject) req.getData();
                    String message = dbManager.update(dbObj);
                    return new Response(Response.Status.OK, message, null);
                }
                break;
            case DELETE:
                if(req.getData() instanceof Integer) {
                    String message = dbManager.delete((Integer) req.getData());
                    return new Response(Response.Status.OK, message, null);
                }
                break;
            case LOGOUT:
                return new Response(Response.Status.OK, "Déconnexion réussie.", null);
            default:
                return new Response(Response.Status.ERROR, "Type de requête inconnu.", null);
        }
        return new Response(Response.Status.ERROR, "Données de requête invalides.", null);
    }
}
