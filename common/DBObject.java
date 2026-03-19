package common;

import java.io.Serializable;
/**
 * 
 * C’est la réponse envoyée par le serveur après traitement d’une requête.
Elle contient un statut (OK, ERROR), un message, et éventuellement un objet.
 * DBObject est la classe de base qui représente les objets manipulés dans la base
 */
public class DBObject implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;
    private String content;

    public DBObject(int id, String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }
    
    //Lecture objet
    @Override
    public String toString() {
        return "DBObject [id=" + id + ", name=" + name + ", content=" + content + "]";
    }
}
