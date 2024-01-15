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
        stage('Docker build') {
            steps {
        script {
            // Exécutez votre commande Docker build
            docker.build('my-build-image').inside("--volume=/var/run/docker.sock:/var/run/docker.sock") {
               // The build here
            }
        }
            }
        }
    }

}
