pipeline {
    agent any

    environment {
        DOCKER_IMAGE_VERSION = '1.0.0'
        DOCKER_HUB_USERNAME = 'brahim2023'
        DOCKER_COMPOSE_FILE = 'docker-compose-test.yml'
        PROJECTS = ['APIGateway', 'eureka', 'operateur', 'product', 'stock']
    }

    stages {
        stage('Build, Test, and Deploy') {
            steps {
                script {
                    // Loop through each project in PROJECTS
                    for (String project : env.PROJECTS) {
                        echo "Processing project: ${project}"

                        // Change directory to the project
                        dir(project) {
                            // Run Maven commands
                            sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Dmaven.test.failure.ignore=true'
                            sh 'mvn sonar:sonar ' +
                               "-Dsonar.login=admin " +
                               "-Dsonar.password=vagrant " +
                               "-Dsonar.projectKey=${project} " +
                               "-Dsonar.host.url=http://192.168.1.160:9000"

                            // Deploy the project
                            sh 'mvn clean deploy'
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
