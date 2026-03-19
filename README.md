Base de données orientée objet en Java

Ce projet universitaire consiste en la réalisation d’une base de données orientée objet développée en Java, avec une architecture client/serveur.
Elle permet à plusieurs utilisateurs de se connecter à un serveur et d’interagir avec une base de données via des opérations CRUD.

La communication entre le client et le serveur repose sur :
- des sockets TCP
- la sérialisation d’objets Java

##Description des répértoire:
- common : objets partagés (requêtes, réponses, utilisateurs, objets métier)
- server : logique serveur, gestion des clients et de la base
- clientlib : API de communication client → serveur
- client : interface utilisateur en ligne de commande


## Lancer le projet

### 1. Compilation
```bash
javac -d bin $(find . -name "*.java")
```

### 2. Lancer le serveur
```bash
java -cp bin server.ServerMain
```

### 3. Lancer le client
```bash
java -cp bin client.ClientMain
```

## Authentification
mot de passe = nom d’utilisateur

## Persistance
Les données sont sauvegardées dans database.ser
