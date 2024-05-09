# Gestion Championnat

## TP Java Spring JPA

Application qui permet de gérer les résultats d’un championnat de foot. Les utilisateurs une fois connectés pourront créer des championnats, des équipes, des journées et saisir des résultats. Les visiteurs du site pourront consulter les championnats, les équipes et voir le classement d’un championnat.

![schéma de base de donnée](/info/schema.png)

### Lancement de l'application

```Bash
# Exécution du conteneur MySQL / phpmyadmin
docker-compose up -d

# Build Maven
mvn package
# Exécution du Java
java -jar .\target\GestionChampionnat-0.0.1-SNAPSHOT.jar
```
