package server;

import common.DBObject;
import java.io.*;
import java.util.*;
/**
 * C’est la classe qui gère la base de données en mémoire avec une HashMap.
Elle exécute les opérations CRUD, sauvegarde la base dans un fichier (database.ser) via la sérialisation, 
et synchronise les accès avec synchronized.
 */
public class DatabaseManager {
    private Map<Integer, DBObject> database;
    private final String dataFile = "database.ser";
    
    //Charge base de donnée ou en crée une
    public DatabaseManager() {
        loadDatabase();
    }
    
    //Chargement de la base de données depuis le fichier `database.ser` Restaure la HashMap avec déserialisation
    @SuppressWarnings("unchecked")
    private void loadDatabase() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dataFile))) {
            database = (Map<Integer, DBObject>) ois.readObject();
            System.out.println("Base de données chargée (" + database.size() + " objets)."); 
        } catch (FileNotFoundException e) {
            database = new HashMap<>();// Fichier inexistant : création d’une base vide
            System.out.println("Aucune base existante trouvée. Création d'une nouvelle base.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            database = new HashMap<>();
        }
    }
    
    public synchronized void saveDatabase() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dataFile))) {
            oos.writeObject(database);
            System.out.println("Base de données sauvegardée.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public synchronized String create(DBObject obj) {
        if (database.containsKey(obj.getId())) {
            return "Objet avec l'ID " + obj.getId() + " existe déjà.";
        }
        database.put(obj.getId(), obj);
        saveDatabase();
        return "Objet créé avec succès.";
    }
    
    public synchronized DBObject read(int id) {
        return database.get(id);
    }
    
    public synchronized String update(DBObject obj) {
        if (!database.containsKey(obj.getId())) {
            return "Objet avec l'ID " + obj.getId() + " n'existe pas.";
        }
        database.put(obj.getId(), obj);
        saveDatabase();
        return "Objet mis à jour avec succès.";
    }
    
    public synchronized String delete(int id) {
        if (!database.containsKey(id)) {
            return "Objet avec l'ID " + id + " n'existe pas.";
        }
        database.remove(id);
        saveDatabase();
        return "Objet supprimé avec succès.";
    }
    
    public synchronized List<DBObject> getAllObjects() {
        return new ArrayList<>(database.values());
    }
}
