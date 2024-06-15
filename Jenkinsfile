pipeline {
    agent any

    environment {
        DOCKER_IMAGE_VERSION = '1.0.0'
        DOCKER_HUB_USERNAME = 'brahim2023'
        DOCKER_COMPOSE_FILE = 'docker-compose-test.yml'
        SONAR_HOST_URL = 'http://192.168.1.160:9000'
        SONAR_LOGIN = 'admin'
        SONAR_PASSWORD = 'vagrant'
        SONAR_TOKEN = 'your_sonarqube_token_here' // Update with your SonarQube token
    }

    stages {
        stage('Delete Projects from SonarQube') {
            steps {
                script {
                    def sonarUrl = "${env.SONAR_HOST_URL}"
                    def sonarToken = "${env.SONAR_TOKEN}"

                    // List of project keys to delete
                    def projectsToDelete = ['APIGateway', 'eureka', 'operateur', 'product', 'stock']

                    projectsToDelete.each { projectKey ->
                        // Use curl to send HTTP POST request to delete project
                        sh "curl -u ${sonarToken}: -X POST ${sonarUrl}/api/projects/delete?key=${projectKey}"
                    }
                }
            }
        }

    stage('SonarQube Analysis') {
        steps {
            script {
                // Define the list of projects
                def PROJECTS = ['APIGateway', 'eureka', 'operateur', 'product', 'stock']

                // Loop through each project
                PROJECTS.each { project ->
                    echo "Processing project: ${project}"

                    // Check if project already exists in SonarQube
                    def projectExists = sh(
                        script: "curl -u ${env.SONAR_LOGIN}:${env.SONAR_PASSWORD} -s '${env.SONAR_HOST_URL}/api/projects/search?projects=${project.toLowerCase()}' | jq '.components[] | select(.key == \"${project.toLowerCase()}\") | .key'",
                        returnStatus: true
                    ) == 0

                    if (projectExists) {
                        echo "Project ${project} already exists in SonarQube. Updating analysis."

                        // Update SonarQube analysis
                        dir(project) {
                            sh "mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Dmaven.test.failure.ignore=true"
                            sh "mvn sonar:sonar " +
                               "-Dsonar.login=${env.SONAR_LOGIN} " +
                               "-Dsonar.password=${env.SONAR_PASSWORD} " +
                               "-Dsonar.projectKey=${project.toLowerCase()} " +
                               "-Dsonar.host.url=${env.SONAR_HOST_URL}"
                        }
                    } else {
                        echo "Project ${project} does not exist in SonarQube. Creating new analysis."

                        // Run SonarQube analysis
                        dir(project) {
                            sh "mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Dmaven.test.failure.ignore=true"
                            sh "mvn sonar:sonar " +
                               "-Dsonar.login=${env.SONAR_LOGIN} " +
                               "-Dsonar.password=${env.SONAR_PASSWORD} " +
                               "-Dsonar.projectKey=${project.toLowerCase()} " +
                               "-Dsonar.host.url=${env.SONAR_HOST_URL}"
                        }
                    }
                }
            }
        }
       }

        stage('Nexus Deployment') {
            steps {
                script {
                    // Define the list of projects
                    def PROJECTS = ['APIGateway', 'eureka', 'operateur', 'product', 'stock']

                    // Loop through each project
                    PROJECTS.each { project ->
                        echo "Deploying project: ${project}"

                        // Change directory to the project
                        dir(project) {
                            // Deploy the project to Nexus (assuming Nexus deployment is configured in pom.xml)
                            sh 'mvn clean deploy'
                        }
                    }
                }
            }
        }

        stage('Deploy Microservices') {
            steps {
                script {
                    // Define the list of microservices
                    ["eureka", "actor", "contract", "invoice", "api-gateway", "auth", "settings", "static-tables", "asset"].each { serviceName ->
                        deployMicroservice(serviceName)
                    }
                }
            }
        }
    }
}

def deployMicroservice(serviceName) {
    echo "Deploying microservice: ${serviceName}"
    // Implement deployment logic for each microservice
    // Example: Use Docker Compose or custom deployment scripts
    // For illustration, let's echo the deployment command
    echo "Deploying ${serviceName}..."
    // Replace this with actual deployment commands as per your setup
    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d ${serviceName}"
}
