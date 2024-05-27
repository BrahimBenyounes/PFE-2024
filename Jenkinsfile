pipeline {
    agent any

    environment {
        DOCKER_IMAGE_NAME = 'app-front'
        DOCKER_IMAGE_VERSION = '1.0.0'
        DOCKER_HUB_USERNAME = 'brahim2023' 
    }

    stages {
        stage('Build Docker Image') {
            steps {
                script {
                    // Build the Docker image
                    sh "docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_VERSION} ."
                }
            }
        }
       // stage('Push Docker Image to Docker Hub') {
        //    steps {
       //         script {
                    // Log in and push to Docker Hub using hardcoded credentials
         //           sh "docker login -u brahimbenyouns@gmail.com -p Lifeisgoodbrahim@@"
       //             sh "docker tag ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_VERSION} ${DOCKER_HUB_USERNAME}/${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_VERSION}"
         //           sh "docker push ${DOCKER_HUB_USERNAME}/${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_VERSION}"
       //         }
        //    }
      //  }
        stage('Remove Docker Compose Containers') {
            steps {
                script {
                    sh 'docker-compose down || true'
                }
            }
        }
        stage('Start Docker Compose') {
            steps {
                script {
                    sh 'docker-compose up -d --build'
                }
            }
        }
    }
}
