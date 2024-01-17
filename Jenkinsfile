pipeline {
    agent any
    tools {
        maven 'maven'
    }
    stages {
        stage('Build Maven (sans tests)') {
            steps {
                checkout scmGit(branches: [[name: '*/master']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/BTaha8242/haringtontraining']])
                sh 'mvn clean install -DskipTests'
            }
        }
//         stage('Run Tests and Generate Report') {
//             steps {
//                 script {
//                     // Ajoutez ici le script pour exécuter vos tests et générer le rapport
//                     sh 'mvn test'
//                     // Ajoutez d'autres commandes si nécessaire pour générer le rapport
//                 }
//             }
//         }
        stage('Docker build image') {
            steps {
                  script {
                        // Build Docker image with the JAR file
                         // sh 'docker build -t user/haringtontraining-0.0.1-SNAPSHOT .'
                         docker.withRegistry('', 'docker-hub-credentials') {
                         customImage = docker.build('waelbenammara/haringtontraining:tag', '--build-arg JAR_FILE=target/haringtontraining-0.0.1-SNAPSHOT.jar .')
                         // Push Docker image to Docker Hub
                         customImage.push()
                         }
                       }
                }
        }
    }
}
