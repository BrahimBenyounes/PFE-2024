
pipeline {
    agent any

    environment {
        DOCKER_IMAGE_VERSION = '1.0.0'
        DOCKER_HUB_USERNAME = 'brahim2023'
        DOCKER_HUB_PASSWORD = 'Lifeisgoodbrahim@@'
    }

    stages {
        stage('Build and Push Docker Images') {
            steps {
                script {
                    // Build and push Angular app Docker image
                    sh 'docker build -t angular-app:${DOCKER_IMAGE_VERSION} ./angular-front'
                    sh "docker tag angular-app:${DOCKER_IMAGE_VERSION} ${DOCKER_HUB_USERNAME}/angular-app:${DOCKER_IMAGE_VERSION}"
                    sh "docker login -u ${DOCKER_HUB_USERNAME} -p ${DOCKER_HUB_PASSWORD}"
                    sh "docker push ${DOCKER_HUB_USERNAME}/angular-app:${DOCKER_IMAGE_VERSION}"

                    // Build and push Eureka Docker image
                    sh 'docker build -t eureka:${DOCKER_IMAGE_VERSION} ./eureka'
                    sh "docker tag eureka:${DOCKER_IMAGE_VERSION} ${DOCKER_HUB_USERNAME}/eureka:${DOCKER_IMAGE_VERSION}"
                    sh "docker push ${DOCKER_HUB_USERNAME}/eureka:${DOCKER_IMAGE_VERSION}"

                    // Build and push Product Docker image
                    sh 'docker build -t product:${DOCKER_IMAGE_VERSION} ./product'
                    sh "docker tag product:${DOCKER_IMAGE_VERSION} ${DOCKER_HUB_USERNAME}/product:${DOCKER_IMAGE_VERSION}"
                    sh "docker push ${DOCKER_HUB_USERNAME}/product:${DOCKER_IMAGE_VERSION}"

                    // Build and push Stock Docker image
                    sh 'docker build -t stock:${DOCKER_IMAGE_VERSION} ./stock'
                    sh "docker tag stock:${DOCKER_IMAGE_VERSION} ${DOCKER_HUB_USERNAME}/stock:${DOCKER_IMAGE_VERSION}"
                    sh "docker push ${DOCKER_HUB_USERNAME}/stock:${DOCKER_IMAGE_VERSION}"

                    // Build and push Operateur Docker image
                    sh 'docker build -t operateur:${DOCKER_IMAGE_VERSION} ./operateur'
                    sh "docker tag operateur:${DOCKER_IMAGE_VERSION} ${DOCKER_HUB_USERNAME}/operateur:${DOCKER_IMAGE_VERSION}"
                    sh "docker push ${DOCKER_HUB_USERNAME}/operateur:${DOCKER_IMAGE_VERSION}"

                    // Build and push API Gateway Docker image
                    sh 'docker build -t gateway:${DOCKER_IMAGE_VERSION} ./APIGateway'
                    sh "docker tag gateway:${DOCKER_IMAGE_VERSION} ${DOCKER_HUB_USERNAME}/gateway:${DOCKER_IMAGE_VERSION}"
                    sh "docker push ${DOCKER_HUB_USERNAME}/gateway:${DOCKER_IMAGE_VERSION}"
                }
            }
        }

        stage('Control Docker Compose Services') {
            steps {
                script {
                    // Managing Docker Compose services
                    sh 'docker-compose down'
                    sh 'docker-compose up -d --build'
                }
            }
        }
    }
}
