pipeline {
    agent any

    environment {
        DOCKER_IMAGE_VERSION = '1.0.0'
        DOCKER_HUB_USERNAME = 'brahim2023'
        DOCKER_COMPOSE_FILE = 'docker-compose-test.yml'
    }

    stages {


        stage('SonarQube analysis') {
            steps {
                script {
                    // Change directory to 'eureka'
                    dir('eureka') {
                        // Run the SonarQube analysis
                        sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Dmaven.test.failure.ignore=true'
                        sh """
                            mvn sonar:sonar \
                            -Dsonar.login=admin \
                            -Dsonar.password=vagrant \
                            -Dsonar.projectKey=EUREKA \
                            -Dsonar.host.url=http://192.168.1.160:9000
                        """
                    }
                }
            }
        }

        stage('Build and Deploy eureka') {
            steps {
                script {
                    dir('eureka') {
                        sh 'mvn clean deploy'
                    }
                }
            }
        }
        
        stage('Build and Deploy to Nexus') {
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
