pipeline {
    agent any

    environment {
        DOCKER_IMAGE_VERSION = '1.0.0'
        DOCKER_HUB_USERNAME = 'brahim2023'
        DOCKER_COMPOSE_FILE = 'docker-compose-test.yml'
        SONAR_HOST_URL = 'http://192.168.1.160:9000' // Use the configured SonarQube server URL
    }

    stages {
        stage('SonarQube Analysis') {
            steps {
                script {
                    // Ensure SonarScanner tool is correctly found
                    def scannerHome = tool name: 'SonarScanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
                    echo "SonarScanner Home: ${scannerHome}"
                    
                    // Print available tools for verification
                    tool 'SonarScanner'
                    sh 'sonar-scanner -h'

                    // Run SonarQube analysis
                    withSonarQubeEnv('SonarQube') {
                        sh """
                            sonar-scanner \
                            -Dsonar.host.url=${SONAR_HOST_URL} \
                            -Dsonar.login=admin \
                            -Dsonar.password=vagrant \
                            -Dsonar.projectKey=GestionDestock \
                            -Dsonar.sources=angular-front/src \
                            -Dsonar.exclusions=**/*.test.*,**/node_modules/** \
                            -Dsonar.sourceEncoding=UTF-8
                        """
                    }
                }
            }
        }

        stage('Control Docker Compose Services') {
            steps {
                script {
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} down"
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d --build"
                }
            }
        }
    }
}
