pipeline {
    agent any

    environment {
        DOCKER_IMAGE_VERSION = '1.0.0'
        DOCKER_HUB_USERNAME = 'brahim2023'
        DOCKER_COMPOSE_FILE = 'docker-compose.yml'
    }

    stages {
        stage('Build Docker Images') {
            steps {
                script {
                    def services = [
                        [name: 'angular-app', context: './angular-front'],
                        [name: 'eureka', context: './eureka'],
                        [name: 'product', context: './product'],
                        [name: 'stock', context: './stock'],
                        [name: 'operateur', context: './operateur'],
                        [name: 'gateway', context: './APIGateway']
                    ]

                    services.each { service ->
                        dir(service.context) {
                            sh "docker build -t ${DOCKER_HUB_USERNAME}/${service.name}:${DOCKER_IMAGE_VERSION} ."
                        }
                    }
                }
            }
        }

        stage('Push Docker Images to Docker Hub') {
            steps {
                script {
                    withCredentials([string(credentialsId: 'docker-hub-password', variable: 'DOCKER_HUB_PASSWORD')]) {
                        sh 'echo $DOCKER_HUB_PASSWORD | docker login -u $DOCKER_HUB_USERNAME --password-stdin'

                        def services = ['angular-app', 'eureka', 'product', 'stock', 'operateur', 'gateway']
                        services.each { service ->
                            sh "docker tag ${DOCKER_HUB_USERNAME}/${service}:${DOCKER_IMAGE_VERSION} ${DOCKER_HUB_USERNAME}/${service}:${DOCKER_IMAGE_VERSION}"
                            sh "docker push ${DOCKER_HUB_USERNAME}/${service}:${DOCKER_IMAGE_VERSION}"
                        }
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
