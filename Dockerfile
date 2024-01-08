# Utilisez une image OpenJDK 17 comme base
FROM openjdk:17-oracle

# Définissez le répertoire de travail dans l'image
WORKDIR /app

# Copiez le fichier JAR de l'application dans l'image
COPY ./target/haringtontraining-0.0.1-SNAPSHOT.jar /app/haringtontraining-0.0.1-SNAPSHOT.jar

# Exposez le port si votre application utilise un port spécifique
EXPOSE 8080

# Commande pour exécuter l'application lors du démarrage du conteneur
RUN ["java", "-jar", "haringtontraining-0.0.1-SNAPSHOT.jar"]
