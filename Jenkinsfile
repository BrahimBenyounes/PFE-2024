pipeline {
    agent any

    environment {
        SONAR_HOST_URL = 'http://192.168.1.160:9000'
        SONAR_LOGIN = credentials('sonar-login')
        PROJECT_KEYS = 'APIGateway,eureka,operateur,product,stock'
    }

    stages {
        stage('Delete Projects from SonarQube') {
            steps {
                script {
                    def projectKeys = PROJECT_KEYS.split(',')

                    projectKeys.each { key ->
                        sh "curl -u admin:vagrant -X POST '${SONAR_HOST_URL}/api/projects/delete?key=${key}'"
                    }
                }
            }
        }

        stage('Recreate and Analyze Projects') {
            steps {
                script {
                    def projectKeys = PROJECT_KEYS.split(',')

                    projectKeys.each { key ->
                        echo "Processing project: ${key}"

                        dir(key) {
                            // Perform necessary actions to recreate project if needed

                            // Example Maven commands to build and analyze
                            sh "mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Dmaven.test.failure.ignore=true"
                            sh "mvn sonar:sonar " +
                               "-Dsonar.login=${env.SONAR_LOGIN} " +
                               "-Dsonar.projectKey=${key.toLowerCase()} " +
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
