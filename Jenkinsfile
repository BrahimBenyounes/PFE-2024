pipeline {
    agent any

    environment {
        DOCKER_IMAGE_VERSION = '1.0.0'
        DOCKER_HUB_USERNAME = 'brahim2023'
        DOCKER_COMPOSE_FILE = 'docker-compose.yml'
        SONAR_HOST_URL = 'http://192.168.1.160:9000'
        SONAR_LOGIN = 'admin'
        SONAR_PASSWORD = 'vagrant'
    }

    stages {
        stage('Unit Test - Product Microservice') {
            steps {
                script {
                    dir('product') {
                        // Run unit tests with Mockito
                        sh 'mvn clean test'
                    }
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    // Loop through each microservice
                    ["APIGateway", "eureka", "operateur", "product", "stock"].each { project ->
                        echo "Processing project: ${project}"

                        // Generate a unique project key based on microservice name and timestamp
                        def projectKey = "${project}-${getTimeStamp()}"

                        // Change directory to the project
                        dir(project) {
                            // Run Maven commands for SonarQube analysis
                            sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Dmaven.test.failure.ignore=true'

                            // SonarQube analysis
                            sh "mvn sonar:sonar " +
                               "-Dsonar.login=${env.SONAR_LOGIN} " +
                               "-Dsonar.password=${env.SONAR_PASSWORD} " +
                               "-Dsonar.projectKey=${projectKey} " +
                               "-Dsonar.host.url=${env.SONAR_HOST_URL}"
                        }
                    }
                }
            }
        }

                stage('Nexus Deployment') {
                    steps {
                        script {
                            ["eureka"].each { project ->
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

        stage('Maven Package on Server') {
            steps {
                script {
                    // Perform Maven clean package for each microservice on the Jenkins server
                    ["APIGateway", "eureka", "operateur", "product", "stock"].each { serviceName ->
                        dir(serviceName) {
                            sh 'mvn clean package -Dmaven.test.skip=true'
                        }
                    }
                }
            }
        }

        stage('Deploy Microservices') {
            steps {
                script {
                    // Stop existing containers before deployment
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} down"

                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d"
                }
            }
        }

        stage('Push Docker Images to Docker Hub') {
            steps {
                script {
                    // Log in to Docker Hub
                    sh "docker login -u ${env.DOCKER_HUB_USERNAME} -p Lifeisgoodbrahim@@"

                    // Tag and push each microservice's Docker image
                    ["APIGateway", "eureka", "operateur", "product", "stock"].each { serviceName ->
                        sh "docker tag ${serviceName}:${DOCKER_IMAGE_VERSION} ${env.DOCKER_HUB_USERNAME}/${serviceName}:${DOCKER_IMAGE_VERSION}"
                        sh "docker push ${env.DOCKER_HUB_USERNAME}/${serviceName}:${DOCKER_IMAGE_VERSION}"
                    }
                }
            }
        }
    }
}

// Function to generate timestamp
def getTimeStamp() {
    def date = new Date()
    def formattedDate = date.format('yyyyMMddHHmmss')
    return formattedDate
}

