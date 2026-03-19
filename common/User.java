package common;

import java.io.Serializable;
/**
 * C’est la réponse envoyée par le serveur après traitement d’une requête.
Elle contient un statut (OK, ERROR), un message, et éventuellement un objet.
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String username;
    private String password; // Pour simplifier, en clair (à améliorer en production)

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}