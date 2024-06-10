pipeline {
    agent any

    environment {
        DOCKER_IMAGE = 'bitcoin:latest'
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the source code from the repository
                git 'https://github.com/kajitk/bitcoin.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    // Clean and build the Maven project
                    sh 'mvn clean package'
                }
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    // Run unit tests
                    sh 'mvn test'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Build Docker image
                    sh 'docker build -t $DOCKER_IMAGE .'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Run Docker container
                    sh '''
                        docker stop bitcoin || true
                        docker rm bitcoin || true
                        docker run -d -p 8080:8080 --name bitcoin $DOCKER_IMAGE
                    '''
                }
            }
        }
    }

    post {
        always {
            script {
                // Clean up Docker container and image
                sh 'docker rm -f bitcoin || true'
                sh 'docker rmi $DOCKER_IMAGE || true'
            }
        }
    }
}
