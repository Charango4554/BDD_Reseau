package client;

import clientlib.ClientAPI;
import common.DBObject;
import common.Response;

import java.util.Scanner;

/**
 * C’est le point d’entrée du programme côté client.
    Il fournit un menu texte interactif, gère la connexion, 
    l’authentification, et les opérations CRUD via la classe ClientAPI.
 */


public class ClientMain {
    public static void main(String[] args) {
        ClientAPI api = new ClientAPI();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Connexion au serveur...");
        if (!api.connect("localhost", 12345)) {
            System.out.println("Connexion échouée.");
            scanner.close();
            return;
        }
        
        // Authentification
        System.out.print("Entrez le nom d'utilisateur : ");
        String username = scanner.nextLine();
        System.out.print("Entrez le mot de passe (pour simplifier, identique au nom d'utilisateur) : ");
        String password = scanner.nextLine();
        Response loginRes = api.login(username, password);
        System.out.println(loginRes.getMessage());
        if (loginRes.getStatus() != Response.Status.OK) {
            scanner.close();
            return;
        }
        
        boolean running = true;
        while (running) {
            System.out.println("\nMenu :");
            System.out.println("1. Créer un objet");
            System.out.println("2. Lire un objet");
            System.out.println("3. Mettre à jour un objet");
            System.out.println("4. Supprimer un objet");
            System.out.println("5. Déconnexion");
            System.out.print("Choix : ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.print("ID de l'objet : ");
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Nom de l'objet : ");
                    String name = scanner.nextLine();
                    System.out.print("Contenu : ");
                    String content = scanner.nextLine();
                    DBObject obj = new DBObject(id, name, content);
                    Response createRes = api.create(obj);
                    System.out.println(createRes.getMessage());
                    break;
                case 2:
                    System.out.print("Entrez l'ID de l'objet à lire : ");
                    int readId = Integer.parseInt(scanner.nextLine());
                    Response readRes = api.read(readId);
                    if (readRes.getStatus() == Response.Status.OK && readRes.getData() != null) {
                        System.out.println("Objet trouvé : " + readRes.getData().toString());
                    } else {
                        System.out.println("Objet non trouvé.");
                    }
                    break;
                case 3:
                    System.out.print("ID de l'objet à mettre à jour : ");
                    int updateId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Nouveau nom de l'objet : ");
                    String newName = scanner.nextLine();
                    System.out.print("Nouveau contenu : ");
                    String newContent = scanner.nextLine();
                    DBObject updatedObj = new DBObject(updateId, newName, newContent);
                    Response updateRes = api.update(updatedObj);
                    System.out.println(updateRes.getMessage());
                    break;
                case 4:
                    System.out.print("Entrez l'ID de l'objet à supprimer : ");
                    int deleteId = Integer.parseInt(scanner.nextLine());
                    Response deleteRes = api.delete(deleteId);
                    System.out.println(deleteRes.getMessage());
                    break;
                case 5:
                    Response logoutRes = api.logout();
                    System.out.println(logoutRes.getMessage());
                    running = false;
                    break;
                default:
                    System.out.println("Choix invalide.");
            }
        }
        scanner.close();
    }
}
