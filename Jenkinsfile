pipeline {
    agent {
        label 'your-agent-label'  // Specifies a Jenkins agent label to define the node where the job should run.
    }

    options {
        // Allow Jenkins to run on multiple nodes or specify a specific executor allocation
        // You can adjust the number of executors for your Jenkins environment if you're using Kubernetes or other orchestrators.
        disableConcurrentBuilds()  // Avoids concurrent builds for the same pipeline.
        timeout(time: 1, unit: 'HOURS')  // Defines a timeout to prevent jobs from running indefinitely.
    }

    stages {
        stage('Clone Repository') {
            steps {
                echo 'Cloning the GitHub repository...'
                git branch: 'main', url: 'https://github.com/KavinduNuwandika/CSC_554.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building the application...'
                dir('STUDENT_MANAGMENT') {
                    sh './mvnw clean package'
                }
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                dir('STUDENT_MANAGMENT') {
                    sh './mvnw test'
                }
            }
        }

        stage('Docker Build and Deploy') {
            steps {
                echo 'Building and deploying using Docker Compose...'
                dir('STUDENT_MANAGMENT') {
                    sh 'docker-compose down'
                    sh 'docker-compose up -d --build'
                }
            }
        }
    }

    post {
        success {
            echo 'Pipeline executed successfully!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
