pipeline {
    agent any

    environment {
        DOCKER_IMAGE_VERSION = '1.0.0'
        DOCKER_HUB_USERNAME = 'brahim2023'
        DOCKER_COMPOSE_FILE = 'docker-compose-test.yml'
        PROJECTS = ['APIGateway', 'eureka', 'operateur', 'product', 'stock']
        SONAR_LOGIN = 'admin'
        SONAR_PASSWORD = 'vagrant'
        SONAR_HOST_URL = 'http://192.168.1.160:9000'
    }

    stages {
        stage('Build and Deploy') {
            steps {
                script {
                    // Loop through each project
                    for (project in env.PROJECTS) {
                        dir(project) {
                            // Clean and deploy using Maven
                            sh 'mvn clean deploy'
                        }
                    }
                }
            }
        }

        stage('SonarQube analysis') {
            steps {
                script {
                    // Loop through each project
                    for (project in env.PROJECTS) {
                        dir(project) {
                            // Prepare for code coverage and run SonarQube analysis
                            sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Dmaven.test.failure.ignore=true'
                            sh """
                                mvn sonar:sonar \
                                -Dsonar.login=${env.SONAR_LOGIN} \
                                -Dsonar.password=${env.SONAR_PASSWORD} \
                                -Dsonar.projectKey=${project} \
                                -Dsonar.host.url=${env.SONAR_HOST_URL}
                            """
                        }
                    }
                }
            }
        }

        stage('Deploy Microservices') {
            steps {
                script {
                    // Clean up and start microservices using Docker Compose
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} down"
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d --build"
                }
            }
        }
    }
}
