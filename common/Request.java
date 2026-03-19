package common;

import java.io.Serializable;
/**
 * Cette classe représente une requête envoyée par le client.
Elle contient un type (CREATE, READ, etc.) et des données associées.
Elle est sérialisable pour pouvoir transiter dans le réseau.

 */
public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum RequestType {
        LOGIN, CREATE, READ, UPDATE, DELETE, LOGOUT
    }

    private RequestType type; //Type requête
    private Object data; //Données associé

    public Request(RequestType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public RequestType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }
}