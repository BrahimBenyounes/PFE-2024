pipeline {
    agent any

    environment {
        DOCKER_IMAGE_VERSION = '1.0.0'
        DOCKER_HUB_USERNAME = 'brahim2023'
        DOCKER_COMPOSE_FILE = 'docker-compose-test.yml'
        SONAR_HOST_URL = 'http://192.168.1.160:9000'
        SONAR_LOGIN = 'admin'
        SONAR_PASSWORD = 'vagrant'
    }


    stages {
            stage('SonarQube Analysis') {
                steps {
                    script {
                        // Loop through each microservice
                        ["APIGateway", "eureka", "operateur", "product", "stock"].each { project ->
                            echo "Processing project: ${project}"

                            // Change directory to the project
                            dir(project) {
                                // Run Maven commands for SonarQube analysis
                                sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Dmaven.test.failure.ignore=true'

                                // SonarQube analysis
                                sh "mvn sonar:sonar " +
                                   "-Dsonar.login=${env.SONAR_LOGIN} " +
                                   "-Dsonar.password=${env.SONAR_PASSWORD} " +
                                   "-Dsonar.projectKey=GestionDeStock " +  // Adjust project key as needed
                                   "-Dsonar.host.url=${env.SONAR_HOST_URL}"
                            }
                        }
                    }
                }
            }


        stage('Nexus Deployment') {
            steps {
                script {
                    ["APIGateway", "eureka", "operateur", "product", "stock"].each { project ->
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
