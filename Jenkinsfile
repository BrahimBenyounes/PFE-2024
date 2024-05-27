pipeline {
    agent any

    environment {
        DOCKER_COMPOSE_FILE = 'docker-compose.yml'
        DOCKER_HUB_USERNAME = 'brahim2023'
        DOCKER_HUB_PASSWORD = 'Lifeisgoodbrahim@@'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Docker Login') {
            steps {
                script {
                    // Log in to Docker Hub
                    sh "docker login -u ${DOCKER_HUB_USERNAME} -p ${DOCKER_HUB_PASSWORD}"
                }
            }
        }

        stage('Build and Push Docker Images') {
            steps {
                script {
                    // Define services to build and push
                    def services = ['angular-app', 'eureka', 'product', 'stock', 'operateur', 'gateway']
                    services.each { service ->
                        // Build Docker image
                        sh "docker-compose -f ${DOCKER_COMPOSE_FILE} build ${service}"
                        // Tag and push the image to Docker Hub
                        sh "docker push ${DOCKER_HUB_USERNAME}/${service}:latest"
                    }
                }
            }
        }

        stage('Deploy Services') {
            steps {
                script {
                    // Run Docker Compose to start up the services
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d"
                }
            }
        }

        stage('Health Check') {
            steps {
                script {
                    // Check health of each service
                    def services = [
                        [name: 'angular-app', url: 'http://localhost:80'],
                        [name: 'eureka', url: 'http://localhost:8761/actuator/health'],
                        [name: 'product', url: 'http://localhost:8087'],
                        [name: 'stock', url: 'http://localhost:8084'],
                        [name: 'operateur', url: 'http://localhost:8086'],
                        [name: 'gateway', url: 'http://localhost:8888']
                    ]
                    services.each { service ->
                        def response = sh(script: "curl -s -o /dev/null -w '%{http_code}' ${service.url}", returnStdout: true).trim()
                        if (response != '200') {
                            error "${service.name} is not healthy"
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            // Clean up Docker containers and networks
            sh "docker-compose -f ${DOCKER_COMPOSE_FILE} down"
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
