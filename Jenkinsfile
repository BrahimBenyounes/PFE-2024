pipeline {
    agent any

    environment {
        DOCKER_IMAGE_VERSION = '1.0.0'
        DOCKER_HUB_USERNAME = 'brahim2023'
        DOCKER_HUB_PASSWORD = 'Lifeisgoodbrahim@@'
        DOCKER_COMPOSE_FILE = 'docker-compose.yml'
        SONAR_HOST_URL = 'http://192.168.1.160:9000'
        SONAR_LOGIN = 'admin'
        SONAR_PASSWORD = 'vagrant'
    }

    stages {
        stage('Junit and Mockito Tests') {
            steps {
                script {
                    dir('product') {
                        sh 'mvn clean test'
                    }
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    ["APIGateway", "eureka", "operateur", "product", "stock"].each { project ->
                        echo "Processing project: ${project}"
                        def projectKey = "${project}-${getTimeStamp()}"
                        dir(project) {
                            sh 'mvn clean org.jacoco:jacoco-maven-plugin:prepare-agent install -Dmaven.test.failure.ignore=true'
                            sh "mvn sonar:sonar -Dsonar.login=${env.SONAR_LOGIN} -Dsonar.password=${env.SONAR_PASSWORD} -Dsonar.projectKey=${projectKey} -Dsonar.host.url=${env.SONAR_HOST_URL}"
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
                        dir(project) {
                            sh 'mvn clean deploy'
                        }
                    }
                }
            }
        }

        stage('Maven Package on Server') {
            steps {
                script {
                    ["APIGateway", "eureka", "operateur", "product", "stock"].each { serviceName ->
                        dir(serviceName) {
                            sh 'mvn clean package -Dmaven.test.skip=true'
                        }
                    }
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    [ "eureka", "operateur", "product", "stock"].each { serviceName ->
                        dir(serviceName) {
                            sh "docker build -t ${serviceName}:${DOCKER_IMAGE_VERSION} ."
                        }
                    }
                }
            }
        }

        stage('Deploy Microservices') {
            steps {
                script {
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} down"
                    sh "docker-compose -f ${DOCKER_COMPOSE_FILE} up -d"
                }
            }
        }

        stage('Push Docker Images to Docker Hub') {
            steps {
                script {
                    sh "docker login -u ${env.DOCKER_HUB_USERNAME} -p ${env.DOCKER_HUB_PASSWORD}"
                    ["eureka", "operateur", "product", "stock"].each { serviceName ->
                        sh "docker tag ${serviceName}:${DOCKER_IMAGE_VERSION} ${env.DOCKER_HUB_USERNAME}/${serviceName}:${DOCKER_IMAGE_VERSION}"
                        sh "docker push ${env.DOCKER_HUB_USERNAME}/${serviceName}:${DOCKER_IMAGE_VERSION}"
                    }
                }
            }
        }
    }
}

def getTimeStamp() {
    def date = new Date()
    def formattedDate = date.format('yyyyMMddHHmmss')
    return formattedDate
}
