pipeline {
    agent any

    tools {
        maven 'Maven'    
        jdk 'jdk17'      
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/YassineBourich/EmployeeManagement-CI.git'
            }
        }

        stage('Build') {
            steps {
                script {
                    if (isUnix()) {
                        echo "Building project on Linux"
                        sh "${env.MAVEN_HOME}/bin/mvn -B -U clean install"
                    } else {
                        echo "Building project on Windows"
                        bat "\"${env.MAVEN_HOME}\\bin\\mvn\" -B -U clean install"
                    }
                }
            }
        }

        stage('Test') {
            steps {
                script {
                    if (isUnix()) {
                        echo "Running tests on Linux"
                        sh "${env.MAVEN_HOME}/bin/mvn -B test"
                    } else {
                        echo "Running tests on Windows"
                        bat "\"${env.MAVEN_HOME}\\bin\\mvn\" -B test"
                    }
                }
            }
        }

        stage('Archive Artifacts') {
            steps {
                script {
                    if (isUnix()) {
                        archiveArtifacts artifacts: 'target/*.war', allowEmptyArchive: true
                    } else {
                        archiveArtifacts artifacts: 'target\\*.war', allowEmptyArchive: true
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully!"
        }
        failure {
            echo "Pipeline failed."
        }
    }
}
