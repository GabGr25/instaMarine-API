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
                        echo $TOKEN | docker login ghcr.io -u gabgr25 --password-stdin
                        docker build -t ghcr.io/gabgr25/instamarine-api:latest .
                        docker push ghcr.io/gabgr25/instamarine-api:latest
                        '''
                    }
                }
            }
        }
    }
}