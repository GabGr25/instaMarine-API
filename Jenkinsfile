pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        stage('Docker Build & Push') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'github-creds', usernameVariable: 'USERNAME', passwordVariable: 'TOKEN')]) {
                        sh '''
                        echo $TOKEN | docker login ghcr.io -u GabGr25 --password-stdin
                        docker build -t ghcr.io/GabGr25/instaMarine-API:latest .
                        docker push ghcr.io/GabGr25/instaMarine-API:latest
                        '''
                    }
                }
            }
        }
    }
}