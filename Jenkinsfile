pipeline {
    agent any

    environment {
        DOCKER_IMAGE_VERSION = '1.0.0'
        DOCKER_HUB_USERNAME = 'brahim2023'
        DOCKER_COMPOSE_FILE = 'docker-compose-test.yml'
        SONAR_HOST_URL = 'http://localhost:9000'
        SONAR_AUTH_TOKEN = credentials('sonar-auth-token') // Assume you have stored the token in Jenkins credentials
    }

    stages {
       

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') { // 'SonarQube' is the name you gave to the SonarQube server in Jenkins
                    script {
                        sh 'sonar-scanner ' +
                           '-Dsonar.projectKey=Gestiondestock ' +
                           '-Dsonar.sources=. ' +
                           '-Dsonar.host.url=${SONAR_HOST_URL} ' +
                           '-Dsonar.login=${SONAR_AUTH_TOKEN}'
                    }
                }
            }
        }

        stage('Control Docker Compose Services') {
            steps {
                script {
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} down"
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d --build"
                }
            }
        }
    }
}
