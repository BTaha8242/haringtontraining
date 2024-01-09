# Utilisez une image OpenJDK 17 comme base
FROM openjdk:17-oracle

USER root
RUN chmod 777 /var/run/docker.sock
USER jenkins

# Définissez le répertoire de travail dans l'image
WORKDIR /app

# Copiez le fichier JAR de l'application dans l'image
COPY ./target/haringtontraining-0.0.1-SNAPSHOT.jar /app/haringtontraining-0.0.1-SNAPSHOT.jar

# Exposez le port si votre application utilise un port spécifique
EXPOSE 8080

# Commande pour exécuter l'application lors du démarrage du conteneur
CMD ["java", "-jar", "haringtontraining-0.0.1-SNAPSHOT.jar"]
