package common;

import java.io.Serializable;
/**
 * C’est la réponse envoyée par le serveur après traitement d’une requête.
Elle contient un statut (OK, ERROR), un message, et éventuellement un objet.
 */
public class Response implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Status {
        OK, ERROR
    }

    private Status status;
    private String message;
    private Object data;

    public Response(Status status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
