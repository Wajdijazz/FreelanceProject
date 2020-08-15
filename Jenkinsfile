pipeline {
    agent any
    
environment {
    registry = "https://hub.docker.com/u/wajdijazz"
    registryCredential = 'dockerhub'
  }
    
    tools {
        // Install the Maven version configured as "M3" and add it to the path.
        maven "M3"
    }

    stages {
        stage('Build') {
            steps {
                // Get some code from a GitHub repository
                git 'https://github.com/Wajdijazz/FreelanceProject-Back.git'

                // Run Maven on a Unix agent.
                sh "mvn -Dmaven.test.failure.ignore=true clean package"

                // To run Maven on a Windows agent, use
                // bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }

  
        }
        stage('Building image') {
             steps{
                 script {
                   docker.build registry + ":$BUILD_NUMBER"
                  }
    }
  }
    }
}
