pipeline {
    environment {
        registry = "chebbi4m/backend"
        registryCredential = 'dockerhub_id'
        dockerImage = ''
    }

    agent any

    stages {
        stage('git') {
            steps {
                echo 'pulling from github'
                git branch: 'MohamedChebbi5Sim1',
                    url: 'https://github.com/ihebdebbech/DevopsSki_station.git'
            }
        }

        stage('maven build') {
            steps {
                echo 'maven build'
                sh """mvn clean install"""
            }
        }

        stage('testing with mockito') {
            steps {
                echo 'maven testing'
                sh "mvn test"
            }
        }

        stage('Sonarqube') {
            steps {
                echo 'sonar test'
                sh 'mvn sonar:sonar -Dsonar.login=admin -Dsonar.password=SonarSonar123@'
            }
        }

        stage('Deploy to Nexus ArtifactArk') {
            steps {
                echo 'Deploy to nexus'
                sh 'mvn deploy -DskipTests'
            }
        }

        stage('Building our image') {
            steps {
                script {
                    dockerImage = docker.build(registry + ":$BUILD_NUMBER")
                }
            }
        }

        stage('Deploy our image') {
            steps {
                script {
                    docker.withRegistry('', registryCredential) {
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
                sh 'docker start prometheusfix'
                sh 'docker start grafana'
            }
        }
    }

    post {
        always {
            publishHTML(target: [
                allowMissing: false,
                alwaysLinkToLastBuild: false,
                keepAll: true,
                reportDir: './target/site/jacoco',
                reportFiles: 'index.html',
                reportName: 'Jacoco Code Coverage Report'
            ])
            echo "Job Name: ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}, Build URL: ${env.BUILD_URL}"
            emailext(
                to: "chebbim4@gmail.com",
                from: "chebbim4@gmail.com",
                replyTo: "chebbim4@gmail.com",
                mimeType: 'text/html',
                subject: "STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                body: """<p>STARTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
                         <p>Build Status: ${currentBuild.result}</p>
                         <p>Check console output at <a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>;</p>
                         <img src="https://www.phpro.be/uploads/media/sulu-400x400/09/469-jenkins%404x.png?v=1-0?62b3251db82aa489a7ee194a74cc6fb1" alt="jenkins">""",
                attachmentsPattern: 'target/site/jacoco/*.html'
            )
        }
    }
}
