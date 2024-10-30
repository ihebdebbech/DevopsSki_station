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
                      // Execute 'mvn clean' command
                      sh 'mvn clean'
                  }
              }

              stage('Compile') {
                  steps {
                      // Execute 'mvn compile' command
                      sh 'mvn compile'
                  }
              }
              stage('Test') {
                  steps {
                      sh "mvn test install" // Run unit tests using mvn test
                  }
              }/*

              stage('Sonarqube') {
                  environment {
                      // Define SonarQube credentials
                      SONAR_USERNAME = credentials('admin_sonar')

                  }
                  steps {
                      sh 'mvn test jacoco:report'
                      sh "mvn sonar:sonar -Dsonar.login=${SONAR_USERNAME} -Dsonar.projectKey=devops_groupe4 -Dsonar.projectName='devops_groupe4'"
                  }
              }*/
             // stage('Nexus') {
               //   steps {
                 //     sh 'mvn deploy -Dmaven.test.skip'
                  //}
              //}

              stage('Building image') {
                  steps {
                      script {
                              sh 'docker build -t ihebdebbech/devopsskistation:1.0.1 .'
                      }
                  }
              }

            /*  stage('push docker hub') {
                  steps {
                      script {
                          try {
                              echo 'Building Docker image...'
                              sh 'docker build -t ihebdebbech/devopsskistation:1.0.1 .'

                              echo 'Logging in to Docker Hub...'
                              sh 'docker login -u ihebdebbech -p devops123'
                              echo 'Pushing Docker image to Docker Hub...'
                              sh 'docker push ihebdebbech/devopsskistation:1.0.1'
                          } catch (Exception e) {
                              echo "Error occurred while building and pushing Docker image: ${e.message}"
                              currentBuild.result = 'FAILURE'
                              error("Docker build and push failed")
                          }
                      }
                  }
              }
              stage('Docker Compose') {
                  steps {
                      sh 'docker compose up -d'
                  }
              }*/
          }
      }
