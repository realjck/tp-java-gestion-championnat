# TP Java Gestion Championnat

*IPI CDEV 2023-2024 - JC. Kleinbourg*

## Exercice Java Spring JPA

Application qui permet de gérer les résultats d’un championnat de foot. Les utilisateurs une fois connectés peuvent créer des championnats, des équipes, des journées et saisir des résultats. Les visiteurs du site peuvent consulter les championnats, les équipes et voir le classement d’un championnat.

![schéma de base de donnée](/info/schema.png)

## Lancement de l'application avec Docker

```Bash
# Exécution du conteneur MySQL / phpmyadmin
docker-compose up -d

# Build Maven
mvn package
# Exécution du JAR
java -jar .\target\GestionChampionnat-0.0.1-SNAPSHOT.jar
```

L'application est servie sur le port 8090:
```
localhost:8090/api/
```


## [Collection Postman des routes API](info/postman-collection/TPJavaGestionChampionship.postman_collection.json)
```
info/postman-collection/TPJavaGestionChampionship.postman_collection.json
```

Le fichier Postman de collection comprend les syntaxes utilisées par l'API. Celles-ci sont documentées dans la Javadoc des méthodes des contrôleurs.

**La route `/api/user/` est ouverte (PermitAll()) : **
- ### Utilisateurs (`/api/user/`)
  - getAllUsers()
  - getUserById()
  - getUserByEmailAndPassword()
  - saveUser()
  - updateUser()
  - deleteUser()

**Pour les routes suivantes, les méthodes POST, PUT/PATCH et DELETE nécessitent une authentification (Basic auth avec username = email et password), avec un utilisateur créé via `/api/user/` :**

- ### Championnats (`/api/championship/`)
  - getAllChampionships()
  - getChampionshipById()
  - saveChampionship()
  - updateChampionship()
  - deleteChampionship()
- ### Équipes (`/api/team/`)
  - getAllTeams()
  - getAllTeamsByChampionshipId()
  - getTeamById()
  - saveTeam()
  - addTeamToChampionship()
  - updateTeam()
  - deleteTeam()
- ### Journées (`/api/day/`)
  - getAllDays()
  - getAllDaysByChampionshipId()
  - getDayById()
  - saveDay()
  - updateDay()
  - deleteDay()
- ### Résultats (`/api/game/`)
  - getAllGames()
  - getAllGamesByChampionshipId()
  - getAllGamesByDayId()
  - getGameById()
  - saveGame()
  - updateGame()
  - deleteGame()

*NB: Il a été choisi que l'API renvoie 204 No Content au lieu de tableaux vides.*

## Interface OpenAPI Swagger

L'application sert une interface d'accès à l'API avec Swagger. Celle-ci a été configurée pour fonctionner avec la dernière version de SpringBoot :

```
http://localhost:8090/swagger-ui/index.html
```

![Screenshot de Swagger](info/swagger-screenshot.png)

*En l'état il s'agit d'une page auto-générée, une page avec documentation plus complète serait envisageable.*

## [Résultats des tests](info/postman-resultat-test/TPJavaGestionChampionship.postman_test_run.json)

La collection Postman comprend 136 tests sur l'ensemble des routes de l'API. Les résultats sont dans ce fichier :

```
info/postman-resultat-test/TPJavaGestionChampionship.postman_test_run.json
```

*Les tests doivent s'exécuter avec la base de données à vide.*

![Screenshot de Postman](info/postman-screenshot.png)
