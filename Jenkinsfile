pipeline {
    agent any

    environment {
        DOCKER_IMAGE_VERSION = '1.0.0'
        DOCKER_HUB_USERNAME = 'brahim2023'
        DOCKER_COMPOSE_FILE = 'docker-compose-test.yml'
        SONAR_HOST_URL = 'http://192.168.1.160:9000'
    }

    stages {
        stage('Angular Frontend Build and SonarQube Analysis') {
            steps {
                dir('angular-front') {
                    script {
                        // Install npm dependencies and build Angular app
                        sh "npm install"
                        sh "npm run build --prod"

                        // Run SonarQube analysis using sonar-scanner
                        withSonarQubeEnv('SonarQube') {
                            sh """
                                sonar-scanner \
                                -Dsonar.host.url=${SONAR_HOST_URL} \
                                -Dsonar.login=admin \
                                -Dsonar.password=vagrant \
                                -Dsonar.projectKey=GestionDestock \
                                -Dsonar.sources=src \
                                -Dsonar.exclusions=**/*.test.*,**/node_modules/** \
                                -Dsonar.sourceEncoding=UTF-8
                            """
                        }
                    }
                }
            }
        }

        stage('Microservices Build and Docker Compose') {
            steps {
                script {
                    // Example: Build and start microservices using Docker Compose
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} down"
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d --build"
                }
            }
        }
    }
}
