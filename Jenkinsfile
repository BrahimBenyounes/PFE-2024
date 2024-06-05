pipeline {
    agent any

    environment {
        DOCKER_IMAGE_VERSION = '1.0.0'
        DOCKER_HUB_USERNAME = 'brahim2023'
        DOCKER_COMPOSE_FILE = 'docker-compose-test.yml'
    }

    stages {
        stage('Control Docker Compose Services') {
            steps {
                script {
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} down"
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d --build"
                }
            }
        }

        stage('Prometheus') {
            steps {
                script {
                    // Pull Prometheus Docker image
                    sh "docker pull prom/prometheus:latest"

                    // Run Prometheus container
                    sh "docker run -d --name prometheus -p 9090:9090 -v $(pwd)/prometheus/prometheus.yml:/etc/prometheus/prometheus.yml prom/prometheus:latest"
                }
            }
            post {
                always {
                    // Clean up Prometheus container
                    sh "docker stop prometheus"
                    sh "docker rm prometheus"
                }
            }
        }
    }
}
