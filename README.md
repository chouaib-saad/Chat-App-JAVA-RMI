# **Application de Messagerie RMI avec Java Swing**

## **Description du Projet**
Cette application de bureau est une plateforme de communication en temps réel qui permet à plusieurs utilisateurs de :
- **Envoyer des messages privés** à des utilisateurs spécifiques connectés.
- **Envoyer des messages de diffusion** à tous les utilisateurs connectés.  

Le projet est conçu avec :
- **Java RMI** pour la communication entre le serveur et les clients.
- **Threads** pour gérer les connexions simultanées.
- **Java Swing** pour une interface utilisateur simple et intuitive.

Réalisé dans le cadre d’un projet académique par **Chouaib Saad**, année **2023-2024**.

---

## **Fonctionnalités Principales**
### Serveur :
- **Gérer les connexions clients** via RMI.
- Diffuser des messages privés ou globaux en temps réel.

### Utilisateur :
- **Voir la liste des utilisateurs connectés** (mise à jour dynamique).
- **Envoyer des messages privés** à un utilisateur spécifique.
- **Envoyer un message de diffusion** à tous les utilisateurs connectés.

---

## **Technologies Utilisées**
- **Java SE 20** ou supérieur.
- **Java RMI** pour la communication réseau.
- **Threads** pour le traitement des connexions multiples.
- **Java Swing** pour l’interface utilisateur.

---

## **Structure du Projet**
Le projet suit une organisation logique :  
```
messaging-app/
├── src/
│   ├── server/
│   │   ├── Server.java                   # Serveur RMI
│   │   ├── ServerIHM.java                # Interface graphique pour le serveur
│   │   ├── ServerReceive.java            # Gestion de la réception des messages
│   │   └── ServerSend.java               # Gestion de l'envoi des messages
│   ├── user/
│   │   ├── User.java                     # Classe principale pour le client
│   │   ├── UserIHM.java                  # Interface graphique pour le client
│   │   ├── UserReceive.java              # Gestion de la réception des messages côté client
│   │   └── UserSend.java                 # Gestion de l'envoi des messages côté client
└── README.md                             # Documentation du projet
```

---

## **Prérequis**
1. **JDK 20** ou supérieur.
2. **IntelliJ IDEA** ou un IDE similaire.
3. Réseau local ou global pour tester la communication.
4. Port RMI configuré pour la communication entre le serveur et les clients.

---

## **Installation et Lancement**
### Étape 1 : Télécharger le projet
Clonez le dépôt GitHub ou récupérez les fichiers source :  
```bash
git clone https://github.com/votre-utilisateur/messaging-app.git
cd messaging-app
```

### Étape 2 : Configuration
1. **Configurer le port RMI** dans `Server.java`. Par exemple :  
   ```java
   int port = 1099; // Port RMI
   ```
2. **Compiler toutes les classes** dans votre IDE.

### Étape 3 : Lancer le serveur
1. Ouvrez le fichier **Server.java**.
2. Exécutez le programme pour démarrer le serveur RMI.

### Étape 4 : Lancer les clients
1. Ouvrez le fichier **User.java**.
2. Exécutez le programme pour démarrer un client.
3. Répétez cette étape autant de fois que nécessaire pour connecter plusieurs utilisateurs.

---

## **Comment utiliser l'application**
1. **Connexion** : Les utilisateurs se connectent automatiquement au serveur.
2. **Messagerie privée** : Sélectionnez un utilisateur dans la liste et envoyez un message.
3. **Messagerie de diffusion** : Tapez un message dans le champ de diffusion et envoyez-le à tous les utilisateurs.

---

## **Contributeur**
- **Nom complet** : Chouaib Saad  
- **Année académique** : 2023 - 2024  

---

## **Licence**
Tous droits réservés © **Chouaib Saad**.  
Projet destiné uniquement à des fins éducatives.

--
