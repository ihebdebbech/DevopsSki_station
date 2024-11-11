pipeline {
    environment {

        registry = "ihebdebbech/backend"

        registryCredential = 'dockerhub_id'

        dockerImage = ''

    }

    agent any

    stages {
        stage('git') {
            steps {
                echo 'pulling from github';
                git branch : 'ihebdebbech',
                url : 'https://github.com/ihebdebbech/DevopsSki_station.git'
            }
        }
         stage('maven build ') {
            steps {
                echo 'maven build';
                sh """mvn clean install """
            }
        }
          stage('testing with mockito') {
            steps {
                echo 'maven testing';
                sh "mvn test"

        }
          }
         stage('Sonarqube') {
            steps {
                echo 'sonar test';
               sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=Admin123456789*'
            }
       }

       stage('ArtifactArk') {
           steps {
               echo 'Deploy to nexus';
                sh 'mvn deploy -DskipTests'

           }
        }
         stage('Building our image') {

            steps {

                script {

                    dockerImage = docker.build registry + ":$BUILD_NUMBER"

                }

            }

       }
         stage('Deploy our image') {

           steps {

                script {

                    docker.withRegistry( '', registryCredential ) {

                        dockerImage.push()

                  }

               }

            }

       }

        stage('Building and deploying using docker-compose') {
            steps {
               sh 'docker-compose up -d'
            }
        }


       stage('Grafana Prometheus') {
            steps {
                sh 'docker start prometheus'
                sh 'docker start grafana'
            }
        }



    }

}