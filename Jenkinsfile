pipeline {
    agent any
    
    environment {
        DOCKER_IMAGE_VERSION = '1.0.0'
        DOCKER_HUB_USERNAME = 'brahim2023'
        DOCKER_HUB_PASSWORD = credentials('docker-hub-password') // Store the Docker Hub password in Jenkins credentials
    }

    stages {
        stage('Build Docker Images') {
            steps {
                script {
                    // Log in to Docker Hub
                    sh "docker login -u ${DOCKER_HUB_USERNAME} -p ${DOCKER_HUB_PASSWORD}"
                    
                    // Build and tag the Docker images
                    sh "docker-compose build"
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    // Push the Docker images to Docker Hub
                    sh "docker-compose push"
                }
            }
        }
    }
}
