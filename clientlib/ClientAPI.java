package clientlib;

import common.*;
import java.io.*;
import java.net.Socket;
/**
 * 
 * Les fonctions du clients passe par cette classe

 * 
 * 
 */

public class ClientAPI {
    private Socket socket;//Connexion réseau avec le serveur
    private ObjectOutputStream out; //Request
    private ObjectInputStream in; //Response
    
    //Établit la connexion avec le serveur à partir de son IP et de son port.
    public boolean connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    //Envoie les identifiants de l’utilisateur pour tenter une authentification.
    public Response login(String username, String password) {
        User user = new User(username, password);
        Request req = new Request(Request.RequestType.LOGIN, user);
        return sendRequest(req);
    }
    
    /*
     * 
     * CRUD
     * 
     */
    public Response create(DBObject obj) {
        Request req = new Request(Request.RequestType.CREATE, obj);
        return sendRequest(req);
    }
    
    public Response read(int id) {
        Request req = new Request(Request.RequestType.READ, id);
        return sendRequest(req);
    }
    
    public Response update(DBObject obj) {
        Request req = new Request(Request.RequestType.UPDATE, obj);
        return sendRequest(req);
    }
    
    public Response delete(int id) {
        Request req = new Request(Request.RequestType.DELETE, id);
        return sendRequest(req);
    }
    
    //requête de déconnexion au serveuR
    public Response logout() {
        Request req = new Request(Request.RequestType.LOGOUT, null);
        Response res = sendRequest(req);
        close();
        return res;
    }
    
    //envoyer une requête et attendre la réponse du serveur
    private Response sendRequest(Request req) {
        try {
            out.writeObject(req);
            out.flush();
            Object response = in.readObject();
            if(response instanceof Response) {
                return (Response) response;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Response(Response.Status.ERROR, "Erreur lors de l'envoi de la requête.", null);
    }
    
    //FERMETURE SOCKET 
    private void close() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
