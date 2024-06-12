pipeline {
    agent any

    environment {
        DOCKER_IMAGE_VERSION = '1.0.0'
        DOCKER_HUB_USERNAME = 'brahim2023'
        DOCKER_COMPOSE_FILE = 'docker-compose-test.yml'
    }

    stages {
        stage('Build and Deploy to Nexus') {
            steps {
                script {
                    // Build Maven project and deploy artifacts to Nexus
                    sh "mvn clean deploy -DskipTests=true"

                    // Clean up and start microservices using Docker Compose
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} down"
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d --build"
                }
            }
        }
    }
}
