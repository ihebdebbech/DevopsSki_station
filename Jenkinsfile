pipeline {
    agent any 
    
    stages {
        stage('Checkout GIT') {
            steps {
                echo 'Pulling...'
                git(
                    branch: 'main',
                    url: 'https://github.com/ihebdebbech/DevopsSki_station.git'
                )
            }
        }

        stage('Clean') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('Compile') {
            steps {
                sh 'mvn compile'
                echo 'compile done......'
            }
        }/*
          stage('Test') {
            steps {
                echo 'Running tests...'
                sh 'mvn test'
            }
        }
*/
        // Uncomment the Nexus stage if needed
        /*
        stage('Nexus') {
            steps {
                sh 'mvn deploy -Dmaven.test.skip'
            }
        }
        */
    }
}
